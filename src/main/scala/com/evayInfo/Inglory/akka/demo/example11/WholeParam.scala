package com.evayInfo.Inglory.akka.demo.example11

/**
 * Created by sunlu on 18/11/21.
 */

case class TestAPIParams(names: String, id: Int)

case class FuncMode(
                     method: String,
                     option: String,
                     mode: String
                     )

case class TableName(
                      trainTable: String,
                      testTable: String,
                      optTable: String
                      )

case class ModelPath(
                      filePth: String,
                     modelName: String)

case class ModelParam(
                       param0:String,
                       param1:String,
                       param2:String
                       )

case class WholeParam(
                       FuncMode:FuncMode,
                       TableName:TableName,
                       ModelPath:ModelPath,
                       ModelParam:ModelParam
                       )