package com.evayInfo.Inglory.akka.demo.helloWord

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContext

object HelloWorldApp extends App
{
  implicit val as                   = ActorSystem("hello-world-system")
  implicit val ac                   = ActorMaterializer()
  implicit val ec: ExecutionContext = as.dispatcher

  //创建一个HelloWorldActor监管者
  as actorOf (HelloWorldActorSupervisor props, HelloWorldActorSupervisor.NAME)
}
