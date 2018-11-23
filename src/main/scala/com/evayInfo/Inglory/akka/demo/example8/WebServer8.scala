package com.evayInfo.Inglory.akka.demo.example8

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

import scala.io.StdIn

/**
 * Created by sunlu on 18/10/30.
 *
 * https://www.cnblogs.com/Netsharp/p/9755057.html
 *
 * http://localhost:8080/v1/user/create
 *
 */
object WebServer8 {
  def main(args:Array[String]):Unit={

    implicit val system = ActorSystem("netsharp")
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    /*
    val xroute=path(Segments){xs=>
      get{
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, xs.toString()))
      }
    }
    */

    val xroute=path(Segments){xs=>
      get{
        val mkstring = xs(0) + xs(1)  + xs(2)
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"mkstring:$mkstring"))
      }
    }

    val bindingFuture = Http().bindAndHandle(xroute,"localhost",8080)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")

    StdIn.readLine()

    bindingFuture.flatMap(_.unbind())
      .onComplete(_=>system.terminate())
  }
}
