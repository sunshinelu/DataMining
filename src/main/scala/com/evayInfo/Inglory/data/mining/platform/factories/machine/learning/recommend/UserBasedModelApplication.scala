package com.evayInfo.Inglory.data.mining.platform.factories.machine.learning.recommend

import java.util.Properties

import com.evayInfo.Inglory.data.mining.platform.conf.ConfigurationManager
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{Row, SaveMode, SparkSession}

/**
 * Created by sunlu on 18/10/26.
 */
class UserBasedModelApplication {

  // 是否输出日志
  def SetLogger = {
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("com").setLevel(Level.OFF)
    Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
    System.setProperty("spark.ui.showConsoleProgress", "false")
    Logger.getRootLogger().setLevel(Level.OFF)
  }

  // 链接mysql配置信息
  //  val url = "jdbc:mysql://localhost:3306/data_mining_DB?useUnicode=true&characterEncoding=UTF-8&" +
  //    "useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC"
  //  val user = "root"
  //  val password = "123456"
  val url: String = ConfigurationManager.mysql_jdbc_url
  val user: String = ConfigurationManager.mysql_jdbc_user
  val password: String = ConfigurationManager.mysql_jdbc_password

  val prop = new Properties()
  prop.setProperty("user", user)
  prop.setProperty("password", password)

  val spark_master =  ConfigurationManager.spark_master
  val spark_executor_memory = ConfigurationManager.spark_executor_memory


  /*

   */
  def RatingPrediction(test_table: String, model_name: String,
                       user_col: String, item_col: String, rating_col: String,
                       opt_table: String)={
    val SparkConf = new SparkConf().setAppName(s"UserBasedModelApplication:RatingPrediction").setMaster(spark_master).set("spark.executor.memory", spark_executor_memory)
    val spark = SparkSession.builder().config(SparkConf).getOrCreate()
    val sc = spark.sparkContext
    import spark.implicits._

    // 读取mysql数据
    val col_name = Seq("user", "item", "rating")
    val test_df = spark.read.jdbc(url, test_table, prop).
      select(user_col, item_col, rating_col).
      toDF(col_name: _*).
      withColumn("rating", $"rating".cast("double"))

    val userNumber = test_df.select("user").dropDuplicates().count()

    // 读取用户相似性结果
    val user_simi_df = spark.read.jdbc(url, model_name, prop)

    // user_1, user_2, simi, user, item, rating
    val userR_1 = user_simi_df.join(test_df, user_simi_df("user_1") === test_df("user"), "left").
      withColumn("recomValue", col("simi") * col("rating")).
      groupBy("user_2", "item").agg(sum($"recomValue")).drop("recomValue").
      withColumnRenamed("sum(recomValue)", "recomValue")


    val temp_df = test_df.withColumnRenamed("user", "user_2").withColumn("whether", lit(1))
    val userR_2 = userR_1.join(temp_df, Seq("user_2", "item"), "fullouter").filter(col("whether").isNotNull).drop("whether")

    /*
     根据 输入数据的rating的最大值最小值对 预测的结果进行标准化
      */

    val (rating_Min, rating_Max) = test_df.agg(min($"rating"), max($"rating")).first match {
      case Row(x: Double, y: Double) => (x, y)
    }

    val scaled_Range = lit(rating_Max-rating_Min) // Range of the scaled variable
    val scaled_Min = lit(rating_Min) // Min value of the scaled variable

    val (recom_Min, recom_Max) = userR_2.agg(min($"recomValue"), max($"recomValue")).first match {
      case Row(x: Double, y: Double) => (x, y)
    }

    val recom_Normalized = ($"recomValue" - recom_Min) / (recom_Max - recom_Min) // v normalized to (0, 1) range
    val scaled = scaled_Range * recom_Normalized + scaled_Min
    val predict_df = userR_2.withColumn("vScaled", scaled).
      drop("recomValue").withColumnRenamed("vScaled", "prediction").
      withColumnRenamed("user_2","user").na.fill(Map("prediction" -> 0.0)).
      withColumn("prediction", bround($"prediction", 3)) // 保留3位有效数字

    // 将预测结果保存到mysql数据库中
    predict_df.write.mode(SaveMode.Overwrite).jdbc(url,opt_table,prop)


    sc.stop()
    spark.stop()

  }

  def TopNProductsForUsers(test_table: String, model_name: String,
                           user_col: String, item_col: String, rating_col: String,
                           topN:Int,opt_table: String)={
    val SparkConf = new SparkConf().setAppName(s"UserBasedModelApplication:TopNProductsForUsers").setMaster(spark_master).set("spark.executor.memory", spark_executor_memory)
    val spark = SparkSession.builder().config(SparkConf).getOrCreate()
    val sc = spark.sparkContext
    import spark.implicits._

    // 读取mysql数据
    val col_name = Seq("user", "item", "rating")
    val test_df = spark.read.jdbc(url, test_table, prop).
      select(user_col, item_col, rating_col).
      toDF(col_name: _*).
      withColumn("rating", $"rating".cast("double"))

    // 读取用户相似性结果
    val user_simi_df = spark.read.jdbc(url, model_name, prop)

    // user1, user2, userSimi, userString, itemString, rating
    val userR_1 = user_simi_df.join(test_df, user_simi_df("user_1") === test_df("user"), "left").
      withColumn("recomValue", col("simi") * col("rating")).
      groupBy("user_2", "item").agg(sum($"recomValue")).drop("recomValue").
      withColumnRenamed("sum(recomValue)", "recomValue")

//    userR_1.printSchema()
//    userR_1.show()

    /*
        根据 输入数据的rating的最大值最小值对 预测的结果进行标准化
         */
    val (rating_Min, rating_Max) = test_df.agg(min($"rating"), max($"rating")).first match {
      case Row(x: Double, y: Double) => (x, y)
    }
    val scaled_Range = lit(rating_Max-rating_Min) // Range of the scaled variable
    val scaled_Min = lit(rating_Min) // Min value of the scaled variable

    val (recom_Min, recom_Max) = userR_1.agg(min($"recomValue"), max($"recomValue")).first match {
      case Row(x: Double, y: Double) => (x, y)
    }

    val recom_Normalized = ($"recomValue" - recom_Min) / (recom_Max - recom_Min) // v normalized to (0, 1) range
    val scaled = scaled_Range * recom_Normalized + scaled_Min

    val userR_1_Normal = userR_1.withColumn("vScaled", scaled).
      drop("recomValue").withColumnRenamed("vScaled", "prediction")


    val temp_df = test_df.select("user", "item").withColumnRenamed("user", "user_2").withColumn("whether", lit(1))
    val userR_2 = userR_1_Normal.join(temp_df, Seq("user_2", "item"), "fullouter").filter(col("whether").isNull)


    val predict_df = userR_2.withColumnRenamed("user_2","user")

    val w = Window.partitionBy("user").orderBy(col("prediction").desc)
    val result_df = predict_df.withColumn("rn", row_number.over(w)).where(col("rn") <= topN).
      drop("rn").drop("whether").na.fill(Map("prediction" -> 0.0)).
      withColumn("prediction", bround($"prediction", 3)) // 保留3位有效数字

    // 将结果保存到mysql数据库中
    result_df.write.mode(SaveMode.Overwrite).jdbc(url,opt_table,prop)


    sc.stop()
    spark.stop()

  }

  def TopNUsersForProducts(test_table: String, model_name: String,
                           user_col: String, item_col: String, rating_col: String,
                           topN:Int,opt_table: String)={
    val SparkConf = new SparkConf().setAppName(s"UserBasedModelApplication:TopNUsersForProducts").setMaster(spark_master).set("spark.executor.memory", spark_executor_memory)
    val spark = SparkSession.builder().config(SparkConf).getOrCreate()
    val sc = spark.sparkContext
    import spark.implicits._

    // 读取mysql数据
    val col_name = Seq("user", "item", "rating")
    val test_df = spark.read.jdbc(url, test_table, prop).
      select(user_col, item_col, rating_col).
      toDF(col_name: _*).
      withColumn("rating", $"rating".cast("double"))

    // 读取用户相似性结果
    val user_simi_df = spark.read.jdbc(url, model_name, prop)

    // user1, user2, userSimi, userString, itemString, rating
    val userR_1 = user_simi_df.join(test_df, user_simi_df("user_1") === test_df("user"), "left").
      withColumn("recomValue", col("simi") * col("rating")).
      groupBy("user_2", "item").agg(sum($"recomValue")).drop("recomValue").
      withColumnRenamed("sum(recomValue)", "recomValue")

    /*
        根据 输入数据的rating的最大值最小值对 预测的结果进行标准化
         */
    val (rating_Min, rating_Max) = test_df.agg(min($"rating"), max($"rating")).first match {
      case Row(x: Double, y: Double) => (x, y)
    }
    val scaled_Range = lit(rating_Max-rating_Min) // Range of the scaled variable
    val scaled_Min = lit(rating_Min) // Min value of the scaled variable

    val (recom_Min, recom_Max) = userR_1.agg(min($"recomValue"), max($"recomValue")).first match {
      case Row(x: Double, y: Double) => (x, y)
    }

    val recom_Normalized = ($"recomValue" - recom_Min) / (recom_Max - recom_Min) // v normalized to (0, 1) range
    val scaled = scaled_Range * recom_Normalized + scaled_Min

    val userR_1_Normal = userR_1.withColumn("vScaled", scaled).
      drop("recomValue").withColumnRenamed("vScaled", "prediction")


    val temp_df = test_df.select("user", "item").withColumnRenamed("user", "user_2").withColumn("whether", lit(1))
    val userR_2 = userR_1_Normal.join(temp_df, Seq("user_2", "item"), "fullouter").filter(col("whether").isNull)


    val predict_df = userR_2.withColumnRenamed("user_2","user")

    val w = Window.partitionBy("item").orderBy(col("prediction").desc)
    val result_df = predict_df.withColumn("rn", row_number.over(w)).where(col("rn") <= topN).
      drop("rn").drop("whether").na.fill(Map("prediction" -> 0.0)).
      withColumn("prediction", bround($"prediction", 3)) // 保留3位有效数字

    // 将结果保存到mysql数据库中
    result_df.write.mode(SaveMode.Overwrite).jdbc(url,opt_table,prop)

    sc.stop()
    spark.stop()
  }

}
