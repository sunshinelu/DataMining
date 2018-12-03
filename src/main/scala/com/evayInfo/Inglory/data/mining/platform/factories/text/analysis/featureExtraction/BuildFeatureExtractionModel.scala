package com.evayInfo.Inglory.data.mining.platform.factories.text.analysis.featureExtraction

import java.util.Properties

import com.evayInfo.Inglory.data.mining.platform.conf.ConfigurationManager
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.feature._
import org.apache.spark.ml.linalg.{Vector => MLVector}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.sql._
import org.apache.spark.sql.functions._

/**
 * Created by sunlu on 18/10/17.
 *
 * 功能：构建特征抽取模型，并对输入数据进行特征抽取。
 * 输入：表名、列名（该列已经过分词）、特征抽取方法（词频、TF-ID、Word2Vec）
 * 输出：模型路径、特征抽取后的表名
 *
 * tableName:待分析表名，string类型
 * colName:分词后所在列列名，类型
 * feature_size:特征长度，Int类型
 * min_count:最小词频数，Int类型
 * model_path:模型所在路径，String类型
 * opt_table:输出表名，String类型
 */
class BuildFeatureExtractionModel {

  System.setProperty("spark.sql.warehouse.dir","./spark-warehouse")

  // 是否输出日志
  def SetLogger = {
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("com").setLevel(Level.OFF)
    Logger.getLogger("org.apache.spark").setLevel(Level.ERROR)
    System.setProperty("spark.ui.showConsoleProgress", "false")
    Logger.getRootLogger().setLevel(Level.OFF)
  }

  // 链接mysql配置信息
  val conf = new ConfigurationManager()
  val url: String = conf.mysql_jdbc_url
  val user: String = conf.mysql_jdbc_user
  val password: String = conf.mysql_jdbc_password

  val prop = new Properties()
  prop.setProperty("user", user)
  prop.setProperty("password", password)

  val spark_master =  conf.spark_master
  val spark_executor_memory = conf.spark_executor_memory


  def WordCount(tableName:String,colName:String,feature_size:Int,min_count:Int,model_path:String,opt_table:String)={

    val SparkConf = new SparkConf().setAppName(s"BuildFeatureExtractionModel:WordCount").
      setMaster(spark_master).
      set("spark.executor.memory", spark_executor_memory).
      set("spark.sql.warehouse.dir","target/spark-warehouse")
    val spark = SparkSession.builder().config(SparkConf).getOrCreate()
    val sc = spark.sparkContext
    import spark.implicits._

    val h_conf = sc.hadoopConfiguration
    h_conf.set("fs.hdfs.impl", classOf[org.apache.hadoop.hdfs.DistributedFileSystem].getName)
    h_conf.set("fs.file.impl", classOf[org.apache.hadoop.fs.LocalFileSystem].getName)


    // 读取mysql数据
    val ipt_df = spark.read.jdbc(url, tableName, prop)

    // Split the text into Array
    val tokenizer = new Tokenizer().
      setInputCol(colName).
      setOutputCol("words")

    // CountVectorizer
    val countVectorizer = new CountVectorizer()
      .setInputCol("words")
      .setOutputCol("features")
      .setVocabSize(feature_size) // 若不设置该参数则默认使用全部词的数量，在此例中是词的数量是16
      .setMinDF(min_count)

    // Create our pipeline
    val cv_pipeline = new Pipeline().setStages(Array(tokenizer, countVectorizer))
    // Train the model
    val cv_model = cv_pipeline.fit(ipt_df)

    // save cv_model model
    cv_model.write.overwrite().save(model_path)

    // Predict on the sentenceData dataset
    val df_CV = cv_model.transform(ipt_df)

    //将结果保存到数据框中
    // 列features的array类型转成string类型，因为mysql中没有array类型
    val MLVectorToString = udf((features:MLVector) => Vectors.fromML(features).toDense.toString())

    val results_df = df_CV.withColumn("features", MLVectorToString($"features")).drop("words")

    results_df.write.mode("overwrite").jdbc(url, opt_table, prop) //overwrite ; append

    sc.stop()
    spark.stop()

  }


  def TF_IDF(tableName:String,colName:String,feature_size:Int,min_count:Int,model_path:String,opt_table:String)={
    val SparkConf = new SparkConf().setAppName(s"BuildFeatureExtractionModel:WordCount").
      setMaster(spark_master).
      set("spark.executor.memory", spark_executor_memory).
      set("spark.sql.warehouse.dir","target/spark-warehouse")
    val spark = SparkSession.builder().config(SparkConf).getOrCreate()
    val sc = spark.sparkContext
    import spark.implicits._


    val h_conf = sc.hadoopConfiguration
    h_conf.set("fs.hdfs.impl", classOf[org.apache.hadoop.hdfs.DistributedFileSystem].getName)
    h_conf.set("fs.file.impl", classOf[org.apache.hadoop.fs.LocalFileSystem].getName)

    // 读取mysql数据
    val ipt_df = spark.read.jdbc(url, tableName, prop)

    // Split the text into Array
    val tokenizer = new Tokenizer().
      setInputCol(colName).
      setOutputCol("words")


    // TF-IDF
    val hashingTF = new HashingTF().
      setInputCol(tokenizer.getOutputCol).
      setOutputCol("rawFeatures").
      setNumFeatures(feature_size)

    val idf = new IDF().
      setInputCol(hashingTF.getOutputCol).
      setOutputCol("features").
      setMinDocFreq(min_count)

    // Create our pipeline
    val ifidf_pipeline = new Pipeline().setStages(Array(tokenizer, hashingTF, idf))
    // Train the model
    val ifidf_model = ifidf_pipeline.fit(ipt_df)

    // save ifidf_model model
    ifidf_model.write.overwrite().save(model_path)

    // Predict on the sentenceData dataset
    val df_TFIDF = ifidf_model.transform(ipt_df)

    //将结果保存到数据框中
    // 列features的array类型转成string类型，因为mysql中没有array类型
    val MLVectorToString = udf((features:MLVector) => Vectors.fromML(features).toDense.toString())

    val results_df = df_TFIDF.withColumn("features", MLVectorToString($"features")).drop("words").drop("rawFeatures")

    results_df.write.mode("overwrite").jdbc(url, opt_table, prop) //overwrite ; append

    sc.stop()
    spark.stop()

  }

  def Word2Vec(tableName:String,colName:String,feature_size:Int,min_count:Int,model_path:String,opt_table:String)={
    val SparkConf = new SparkConf().setAppName(s"BuildFeatureExtractionModel:WordCount").
      setMaster(spark_master).
      set("spark.executor.memory", spark_executor_memory).
      set("spark.sql.warehouse.dir","target/spark-warehouse")
    val spark = SparkSession.builder().config(SparkConf).getOrCreate()
    val sc = spark.sparkContext
    import spark.implicits._

    val h_conf = sc.hadoopConfiguration
    h_conf.set("fs.hdfs.impl", classOf[org.apache.hadoop.hdfs.DistributedFileSystem].getName)
    h_conf.set("fs.file.impl", classOf[org.apache.hadoop.fs.LocalFileSystem].getName)


    // 读取mysql数据
    val ipt_df = spark.read.jdbc(url, tableName, prop)

    // Split the text into Array
    val tokenizer = new Tokenizer().
      setInputCol(colName).
      setOutputCol("words")

    // Word2Vec
    val word2Vec = new Word2Vec()
      .setInputCol(tokenizer.getOutputCol)
      .setOutputCol("features")
      .setVectorSize(feature_size)
      .setMinCount(min_count)

    // Create our pipeline
    val word2vec_pipeline = new Pipeline().setStages(Array(tokenizer, word2Vec))
    // Train the model
    val word2vec_model = word2vec_pipeline.fit(ipt_df)

    // save word2vec_model model
    word2vec_model.write.overwrite().save(model_path)

    // Predict on the sentenceData dataset
    val df_Word2Vec = word2vec_model.transform(ipt_df)

    //将结果保存到数据框中
    // 列features的array类型转成string类型，因为mysql中没有array类型
    val MLVectorToString = udf((features:MLVector) => Vectors.fromML(features).toDense.toString())

    val results_df = df_Word2Vec.withColumn("features", MLVectorToString($"features")).drop("words")

    results_df.write.mode("overwrite").jdbc(url, opt_table, prop) //overwrite ; append

    sc.stop()
    spark.stop()

  }

}
