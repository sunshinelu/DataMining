package com.evayInfo.Inglory.data.mining.platform.conf

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

/**
 * Created by sunlu on 18/11/21.
 */


trait ParamJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {

  implicit val FuncModeFormat = jsonFormat3(FuncMode)
  implicit val TableNameFormat = jsonFormat4(TableName)
  implicit val ModelPathFormat = jsonFormat2(ModelPath)
  implicit val ModelParamFormat = jsonFormat5(ModelParam)
  implicit val WholeParamFormat = jsonFormat4(WholeParam)

}
