package com.evayInfo.Inglory.data.mining.platform

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.stream.ActorMaterializer
import com.evayInfo.Inglory.data.mining.platform.conf.ConfigurationManager
import com.evayInfo.Inglory.data.mining.platform.routes.SparkServices

import scala.concurrent.Future
import scala.io.StdIn

/**
 * Created by sunlu on 18/11/20.
 */
object boot extends App{

  val host = ConfigurationManager.serverUrl
  val port = ConfigurationManager.port


  implicit val system = ActorSystem()  // ActorMaterializer requires an implicit ActorSystem
  implicit val ec = system.dispatcher  // bindingFuture.map requires an implicit ExecutionContext
  val routes = new SparkServices().sparkRoutes
  implicit val materializer = ActorMaterializer()  // bindAndHandle requires an implicit materializer

  val bindingFuture: Future[ServerBinding] =
    Http().bindAndHandle(routes, host, port) //Starts the HTTP server
  println(s"Server online at http://$host:$port/\nPress RETURN to stop...")
  StdIn.readLine()

  val log =  Logging(system.eventStream, "go-ticks")
  bindingFuture.map { serverBinding =>
    log.info(s"RestApi bound to ${serverBinding.localAddress} ")
  }.onFailure {
    case ex: Exception =>
      log.error(ex, "Failed to bind to {}:{}!", host, port)
      system.terminate()
  }



}
