package com.evayInfo.Inglory.data.mining.platform.routes

import java.util.Properties

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import com.evayInfo.Inglory.data.mining.platform.conf.{ConfigurationManager, ParamJsonSupport, WholeParam}
import com.evayInfo.Inglory.data.mining.platform.factories.data.preprocessing.PreprocessingApplication
import com.evayInfo.Inglory.data.mining.platform.factories.data.statistically.StatisticallyApplication
import com.evayInfo.Inglory.data.mining.platform.factories.deep.learning.DeepLearningApplication
import com.evayInfo.Inglory.data.mining.platform.factories.machine.learning.MachineLearningApplication
import com.evayInfo.Inglory.data.mining.platform.factories.text.analysis.TextAnalysisApplication
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkContext, SparkConf}


/**
 * Created by sunlu on 18/11/20.
 */
class SparkServices extends ParamJsonSupport {


  def sparkRoutes: Route = versionRoute ~ preprocessingRoute ~ statisticallyRoute ~ deeplearningRoute ~ machineLearningRoute ~ textAnalysisRoute


  def versionRoute =
    pathPrefix("v1") {
      path("version") {
        post {
          //          val sparkConf = new SparkConf().setAppName("spark-version").setMaster("local")
          //          val sc = new SparkContext(sparkConf)


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
          /*
        val SparkConf = new SparkConf().setAppName(s"BuildFeatureExtractionModel:WordCount").
          setMaster(spark_master).
          set("spark.executor.memory", spark_executor_memory).
          set("spark.sql.warehouse.dir", "target/spark-warehouse")
        val spark = SparkSession.builder().config(SparkConf).getOrCreate()
        val sc = spark.sparkContext
        */

          val spark: SparkSession = SparkSession.builder
            .master(spark_master)
            .appName("spark-version")
            .getOrCreate

          val sc = spark.sparkContext
          val sparkConf = sc.getConf

          val version = sc.version

          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"spark version is $version."))
        }
      }
    }


  def preprocessingRoute =
    pathPrefix("v1") {
      path("preprocessing") {
        post {
          entity(as[WholeParam]) { params =>
            //FuncMode
            val algorithm = params.FuncMode.algorithm
            val algorithm_option = params.FuncMode.option
            val algorithm_mode = params.FuncMode.mode

            // TableName
            val trainTable = params.TableName.trainTable
            val col_names = params.TableName.col_names
            val testTable = params.TableName.testTable
            val optTable = params.TableName.optTable

            // ModelPath
            val filePth = params.ModelPath.filePth
            val modelName = params.ModelPath.modelName

            // ModelParam
            val param0 = params.ModelParam.param0
            val param1 = params.ModelParam.param1
            val param2 = params.ModelParam.param2
            val param3 = params.ModelParam.param3
            val param4 = params.ModelParam.param4

            // preprocessing
            val preprocess = new PreprocessingApplication().preprocessing(
              algorithm, algorithm_option, algorithm_mode,
              trainTable, col_names, testTable, optTable,
              filePth, modelName,
              param0, param1, param2, param3, param4)

            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"run success!")) // We can use params.names and params.id
          }
        }
      }
    }

  def statisticallyRoute =
    pathPrefix("v1") {
      path("statistically") {
        post {
          entity(as[WholeParam]) { params =>
            //FuncMode
            val algorithm = params.FuncMode.algorithm
            val algorithm_option = params.FuncMode.option
            val algorithm_mode = params.FuncMode.mode

            // TableName
            val trainTable = params.TableName.trainTable
            val col_names = params.TableName.col_names
            val testTable = params.TableName.testTable
            val optTable = params.TableName.optTable

            // ModelPath
            val filePth = params.ModelPath.filePth
            val modelName = params.ModelPath.modelName

            // ModelParam
            val param0 = params.ModelParam.param0
            val param1 = params.ModelParam.param1
            val param2 = params.ModelParam.param2
            val param3 = params.ModelParam.param3
            val param4 = params.ModelParam.param4

            // statistically
            val statis = new StatisticallyApplication().statistically(
              algorithm, algorithm_option, algorithm_mode,
              trainTable, col_names, testTable, optTable,
              filePth, modelName,
              param0, param1, param2, param3, param4)

            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"run success!")) // We can use params.names and params.id
          }
        }
      }
    }

  def deeplearningRoute =
    pathPrefix("v1") {
      path("deepLearning") {
        post {
          entity(as[WholeParam]) { params =>
            //FuncMode
            val algorithm = params.FuncMode.algorithm
            val algorithm_option = params.FuncMode.option
            val algorithm_mode = params.FuncMode.mode

            // TableName
            val trainTable = params.TableName.trainTable
            val col_names = params.TableName.col_names
            val testTable = params.TableName.testTable
            val optTable = params.TableName.optTable

            // ModelPath
            val filePth = params.ModelPath.filePth
            val modelName = params.ModelPath.modelName

            // ModelParam
            val param0 = params.ModelParam.param0
            val param1 = params.ModelParam.param1
            val param2 = params.ModelParam.param2
            val param3 = params.ModelParam.param3
            val param4 = params.ModelParam.param4

            // deep learning
            val deeplearning = new DeepLearningApplication().deepLearning(
              algorithm, algorithm_option, algorithm_mode,
              trainTable, col_names, testTable, optTable,
              filePth, modelName,
              param0, param1, param2, param3, param4)

            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"run success!")) // We can use params.names and params.id
          }
        }
      }
    }

  def machineLearningRoute: Route =
    pathPrefix("v1") {
      path("machineLearning") {
        post {
          entity(as[WholeParam]) { params =>
            //FuncMode
            val algorithm = params.FuncMode.algorithm
            val algorithm_option = params.FuncMode.option
            val algorithm_mode = params.FuncMode.mode

            // TableName
            val trainTable = params.TableName.trainTable
            val col_names = params.TableName.col_names
            val testTable = params.TableName.testTable
            val optTable = params.TableName.optTable

            // ModelPath
            val filePth = params.ModelPath.filePth
            val modelName = params.ModelPath.modelName

            // ModelParam
            val param0 = params.ModelParam.param0
            val param1 = params.ModelParam.param1
            val param2 = params.ModelParam.param2
            val param3 = params.ModelParam.param3
            val param4 = params.ModelParam.param4

            // machine learning
            val ml = new MachineLearningApplication().machineLearning(
              algorithm, algorithm_option, algorithm_mode,
              trainTable, col_names, testTable, optTable,
              filePth, modelName,
              param0, param1, param2, param3, param4)

            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"run success!")) // We can use params.names and params.id
          }
        }
      }
    }

  def textAnalysisRoute: Route =
    pathPrefix("v1") {
      path("textAnalysis") {
        post {
          entity(as[WholeParam]) { params =>

            //FuncMode
            val algorithm = params.FuncMode.algorithm
            val algorithm_option = params.FuncMode.option
            val algorithm_mode = params.FuncMode.mode

            // TableName
            val trainTable = params.TableName.trainTable
            val col_names = params.TableName.col_names
            val testTable = params.TableName.testTable
            val optTable = params.TableName.optTable

            // ModelPath
            val filePth = params.ModelPath.filePth
            val modelName = params.ModelPath.modelName

            // ModelParam
            val param0 = params.ModelParam.param0
            val param1 = params.ModelParam.param1
            val param2 = params.ModelParam.param2
            val param3 = params.ModelParam.param3
            val param4 = params.ModelParam.param4

            // machine learning
            val textAnalysis = new TextAnalysisApplication().textAnalysis(
              algorithm, algorithm_option, algorithm_mode,
              trainTable, col_names, testTable, optTable,
              filePth, modelName,
              param0, param1, param2, param3, param4)

            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"run success!")) // We can use params.names and params.id
          }
        }
      }
    }


}
