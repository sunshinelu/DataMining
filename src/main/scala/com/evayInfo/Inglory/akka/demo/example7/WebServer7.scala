package com.evayInfo.Inglory.akka.demo.example7

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
object WebServer7 {

  def main(args:Array[String]):Unit={

    implicit val system = ActorSystem("netsharp")
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    val xroute=path(Segment/Segment/Segment){(v,domain,operation)=>
      get{
        val mkstring = v + domain + operation
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, s"version:$v;domain:$domain;operation:$operation;mkstring:$mkstring"))
      }
    }

    val bindingFuture = Http().bindAndHandle(xroute,"localhost",8080)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")

    StdIn.readLine()

    bindingFuture.flatMap(_.unbind())
      .onComplete(_=>system.terminate())
  }
}
