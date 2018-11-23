package com.evayInfo.Inglory.akka.demo.example9

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.{Route, Directives}
import akka.stream.ActorMaterializer
import spray.json.DefaultJsonProtocol

import scala.io.StdIn

/**
 * Created by sunlu on 18/11/21.
 *
 * 参考连接
 * https://blog.scalents.com/2017/08/21/how-to-create-an-akka-http-server/
 *

在postman中使用post方法，输入：http://localhost:8002/test
 Body > raw > json
{
  "names":"jhon",
  "id":10
}
 *
 */
object WebServer9 extends App with Directives with JsonSupport{


  implicit val system = ActorSystem("actor-system")
  implicit val materializer: ActorMaterializer = ActorMaterializer()


  val routes :Route= path("test") {
    post {
      entity(as[TestAPIParams]) { params =>
        val name = params.names
        val id = params.id
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"name is:$name, id is:$id")) // We can use params.names and params.id
      }
    }
  }

  val bindingFuture = Http().bindAndHandle(routes, "0.0.0.0", 8002)
  println(s"Server online at http://localhost:8002/\nPress RETURN to stop...")
  StdIn.readLine()


}


