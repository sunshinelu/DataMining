package com.evayInfo.Inglory.akka.demo.example10

import akka.actor._
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import akka.stream.ActorMaterializer
import scala.util.Random._

/**
 * Created by sunlu on 18/11/21.
 */
object MinimalHttpServer extends App with OrderJsonSupport{

  implicit val system = ActorSystem("actor-system")
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  def routes = route1 ~ route2
  def route1:Route =
    pathPrefix("v1") {
      path("id" / Segment) { id =>
        get {
          println("server get " + id)
          complete(s"got get request")
        } ~
          post {
            entity(as[String]) { entity =>
              println("server get " + entity)
              complete(s"got post request")
            }
          }
      }
    }

  private def calcGrandTotal(o: Order) = {
    val amount = o.items.map(
      i => i.percentageDiscount.getOrElse(1.0d)
        * i.unitPrice * i.quantity).sum + o.deliveryPrice
    GrandTotal(o.id, amount)
  }

  private def genRandomOrder(): Order = {
    val items = (0 to nextInt(5)).map(i => {
      Item(i, nextInt(100), 100 * nextDouble(),
        if (nextBoolean()) Some(nextDouble()) else None)
    }).toList
    Order(nextString(4), System.currentTimeMillis(),
      items, 100 * nextDouble(), Map("notes" -> "random"))
  }


  def route2:Route =
    pathPrefix("v2") {
      path("id" / Segment) { id =>
        get {
          println("server get " + id)
          complete(s"got get request")
        } ~
          post {
            entity(as[String]) { entity =>
              println("server get " + entity)
              complete(s"got post request")
            }
          }
      } ~
        path("json") {
          get {
            complete {
              genRandomOrder()
            }
          } ~
            post {
              entity(as[Order]) {order =>
                complete {
                  calcGrandTotal(order)
                }
              }
            }
        }
    }

  Http().bindAndHandle(routes, "0.0.0.0", 8099)
}
//object MinimalHttpServerApplication extends App {
//  MinimalHttpServer.startServer("0.0.0.0", 8099)
//}