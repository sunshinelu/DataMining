package com.evayInfo.Inglory.akka.demo.example9

import org.apache.avro.data.Json

/**
 * Created by sunlu on 18/11/21.
 */
case class TestAPIParams(names: String, id: Int)

case class TestAPIParams2(func: Json, id: Int)