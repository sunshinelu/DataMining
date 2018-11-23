package com.evayInfo.Inglory.akka.demo.example11

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

/**
 * Created by sunlu on 18/11/21.
 */


trait ParamJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val testAPIJsonFormat = jsonFormat2(TestAPIParams)
  implicit val FuncModeFormat = jsonFormat3(FuncMode)
  implicit val TableNameFormat = jsonFormat3(TableName)
  implicit val ModelPathFormat = jsonFormat2(ModelPath)
  implicit val ModelParamFormat = jsonFormat3(ModelParam)
  implicit val WholeParamFormat = jsonFormat4(WholeParam)

}
