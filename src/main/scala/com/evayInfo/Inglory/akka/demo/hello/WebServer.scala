package com.evayInfo.Inglory.akka.demo.hello


import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

import scala.io.StdIn


/**
 * Created by sunlu on 18/10/30.
 *
 * https://blog.csdn.net/javajxz008/article/details/81478764?utm_source=blogxgwz7
 *
 * 浏览器中输入：

http://localhost:8080/hello
 */
object WebServer {

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("api-server")
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    val route=path("hello"){
      get{
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
      }
    }
    val bingdingFuture = Http().bindAndHandle(route,"localhost",8080)
    StdIn.readLine()
    bingdingFuture.flatMap(_.unbind()).onComplete(_=>system.terminate())
  }


}
