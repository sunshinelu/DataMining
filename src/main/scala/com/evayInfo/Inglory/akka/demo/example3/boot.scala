package com.evayInfo.Inglory.akka.demo.example3

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.evayInfo.Inglory.akka.demo.example3.routes.SparkService
import com.evayInfo.Inglory.akka.demo.example3.factories.Context


class StartSparkServer(implicit val system: ActorSystem,
                       implicit val materializer: ActorMaterializer) extends SparkService {
  def startServer(address: String, port: Int) = {
    Http().bindAndHandle(sparkRoutes, address, port)
  }
}

object StartApplication extends App {
  StartApp
}

object StartApp {
  implicit val system: ActorSystem = ActorSystem("Spark-Couchbase-Service")
  implicit val executor = system.dispatcher
  implicit val materializer = ActorMaterializer()
  val server = new StartSparkServer()
  val config = Context.config
  val serverUrl = config.getString("http.interface")
  val port = config.getInt("http.port")
  server.startServer(serverUrl, port)
}