package com.evayInfo.Inglory.data.mining.platform.routes

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import com.evayInfo.Inglory.data.mining.platform.conf.{ParamJsonSupport, WholeParam}
import com.evayInfo.Inglory.data.mining.platform.factories.machine.learning.MachineLearningApplication


/**
 * Created by sunlu on 18/11/20.
 */
class SparkServices extends ParamJsonSupport{


  def sparkRoutes: Route = preprocessingRoute ~ statisticallyRoute ~ deeplearningRoute ~ machineLearningRoute ~ textAnalysisRoute

  def preprocessingRoute =
    pathPrefix("preprocessing" / Segment) { event =>
      pathEndOrSingleSlash {
        post {
          // POST /events/:event
//          entity(as[EventDescription]) { ed =>
//            onSuccess(createEvent(event, ed.tickets)) {
//              case BoxOffice.EventCreated(event) => complete(Created, event)
//              case BoxOffice.EventExists =>
//                val err = Error(s"$event event exists already.")
//                complete(BadRequest, err)
//            }
//          }
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"<h1>data preprocessing success！</h1>"))
        }
      }
    }

  def statisticallyRoute =
    pathPrefix("statistically" / Segment) { event =>
      pathEndOrSingleSlash {
        post {
          // POST /events/:event
          //          entity(as[EventDescription]) { ed =>
          //            onSuccess(createEvent(event, ed.tickets)) {
          //              case BoxOffice.EventCreated(event) => complete(Created, event)
          //              case BoxOffice.EventExists =>
          //                val err = Error(s"$event event exists already.")
          //                complete(BadRequest, err)
          //            }
          //          }
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"<h1>data preprocessing success！</h1>"))
        }
      }
    }

  def deeplearningRoute =
    pathPrefix("deeplearning" / Segment) { event =>
      pathEndOrSingleSlash {
        post {
          // POST /events/:event
          //          entity(as[EventDescription]) { ed =>
          //            onSuccess(createEvent(event, ed.tickets)) {
          //              case BoxOffice.EventCreated(event) => complete(Created, event)
          //              case BoxOffice.EventExists =>
          //                val err = Error(s"$event event exists already.")
          //                complete(BadRequest, err)
          //            }
          //          }
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"<h1>data preprocessing success！</h1>"))
        }
      }
    }

  def machineLearningRoute:Route =
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
            val col_names =params.TableName.col_names
            val testTable =params.TableName.testTable
            val optTable =params.TableName.optTable

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
              algorithm,algorithm_option,algorithm_mode,
              trainTable,col_names,testTable,optTable,
              filePth,modelName,
              param0,param1,param2,param3,param4 )

            complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"run success!")) // We can use params.names and params.id
          }
        }
      }
    }

  def textAnalysisRoute:Route =
    pathPrefix("v1") {
      path("textAnalysis") {
        post{

          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"run success!"))
        }
      }
    }


}
