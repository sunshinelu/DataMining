package com.evayInfo.Inglory.akka.demo.example6

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

import scala.io.StdIn

/**
 * Created by sunlu on 18/10/30.
 * https://www.cnblogs.com/Netsharp/p/9755057.html
 */
object WebServer6 {

  def main(args:Array[String]):Unit={

    implicit val system = ActorSystem("netsharp")
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    val userRoute = path("user"){
      get{
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say user to akka-http</h1>"))
      }
    }

    val orderRoute = path("order"){
      get{
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say order to akka-http</h1>"))
      }
    }

    val customerRoute = path("customer"){
      get{
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say customer to akka-http</h1>"))
      }
    }

    val routes =
      pathPrefix("v1") {
        userRoute ~ orderRoute ~ customerRoute
      } ~ path("")(getFromResource("public/index.html"))

    val bindingFuture = Http().bindAndHandle(routes,"localhost",8080)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")

    StdIn.readLine()

    bindingFuture.flatMap(_.unbind())
      .onComplete(_=>system.terminate())
  }

}
