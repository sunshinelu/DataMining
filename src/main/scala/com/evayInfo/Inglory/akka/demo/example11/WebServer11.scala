package com.evayInfo.Inglory.akka.demo.example11

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server._
import akka.stream.ActorMaterializer

import scala.io.StdIn


/**
 * Created by sunlu on 18/11/21.
 *

在postman中使用post方法，http://localhost:5000/test1
Body > raw > json
{
  "names":"jhon",
  "id":10
}


在postman中使用post方法，http://localhost:5000/test2
Body > raw > json

{
	"FuncMode":{
		"method": "classification",
		"option": "randforclssifier",
		"mode": "m_all_flow"
	},
	"TableName":{
		"trainTable": "test1",
		"testTable": "randomforestclassifier_train",
		"optTable": "randomforestclassifier_test"
	},
	"ModelPath":{
		"filePth": "C:/Users/lufeng/PycharmProjects/IngloryBDMinning/model/",
		"modelName": "rfc.pkl"
	},
	"ModelParam":{
		"param0": "100",
		"param1": "",
		"param2": "True"
	}
}
 */
object WebServer11 extends App with Directives with ParamJsonSupport{
  implicit val system = ActorSystem("actor-system")
  implicit val materializer: ActorMaterializer = ActorMaterializer()


  def routes:Route = route1 ~ route2
  def route1 :Route = path("test1") {
    post {
      entity(as[TestAPIParams]) { params =>
        val name = params.names
        val id = params.id
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"name is:$name, id is:$id")) // We can use params.names and params.id
      }
    }
  }
  def route2 :Route= path("test2") {
    post {
      entity(as[WholeParam]) { params =>
        val funcMode = params.FuncMode
        val tableName = params.TableName
        val modelPath = params.ModelPath
        val modelParam = params.ModelParam
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"funcMode is:$funcMode, tableName is:$tableName, " +
          s"modelPath is:$modelPath, modelParam is:$modelParam")) // We can use params.names and params.id
      }
    }
  }

  val bindingFuture = Http().bindAndHandle(routes, "0.0.0.0", 5000)
  println(s"Server online at http://localhost:5000/\nPress RETURN to stop...")
  StdIn.readLine()

}
