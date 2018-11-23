package com.evayInfo.Inglory.data.mining.platform.conf

/**
 * Created by sunlu on 18/11/21.
 */


case class FuncMode(
                     algorithm: String,
                     option: String,
                     mode: String
                     )

case class TableName(
                      trainTable: String,
                      col_names: String,
                      testTable: String,
                      optTable: String
                      )

case class ModelPath(
                      filePth: String,
                     modelName: String)

case class ModelParam(
                       param0: String,
                       param1: String,
                       param2: String,
                       param3: String,
                       param4: String
                       )

case class WholeParam(
                       FuncMode:FuncMode,
                       TableName:TableName,
                       ModelPath:ModelPath,
                       ModelParam:ModelParam
                       )