package com.evayInfo.Inglory.data.mining.platform.conf

import com.typesafe.config.ConfigFactory

/**
 * Created by sunlu on 18/11/22.
 */
class ConfigurationManager {

  val config = ConfigFactory.load("application.conf")

  val serverUrl = config.getString("http.host")
  val port = config.getInt("http.port")

  val mysql_jdbc_url = config.getString("mysql.url")
  val mysql_jdbc_user = config.getString("mysql.user")
  val mysql_jdbc_password = config.getString("mysql.password")

  val spark_master = config.getString("spark.master")
  val spark_executor_memory = config.getString("spark.spark.executor.memory")

}

//object ConfigurationManager extends ConfigurationManager{
//  def main(args: Array[String]) {
//    println(ConfigurationManager.mysql_jdbc_url)
//    println(spark_executor_memory)
//  }
//}
