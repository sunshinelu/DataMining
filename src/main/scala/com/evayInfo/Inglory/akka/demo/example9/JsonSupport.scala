package com.evayInfo.Inglory.akka.demo.example9

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

/**
 * Created by sunlu on 18/11/21.
 */

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val testAPIJsonFormat = jsonFormat2(TestAPIParams)
}