package com.najkhan.lightlunch
package Controller

import slick.jdbc.PostgresProfile.api._
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes
import spray.json.{RootJsonFormat, enrichAny}
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import model.persistance.ProductRepository

import spray.json.DefaultJsonProtocol._
import model.persistance.ProductRepository.{ findProd, getAllOrders, getProduct, presistProduct, saveOrder}
import model.{Basket, BasketRequest, Product}
import service.BasketService.{addToBasket, baskets, createBasket}

import com.najkhan.lightlunch.JsonConversion.JsonConversion

import scala.collection.mutable



object AkkaProductController extends App with JsonConversion{

  implicit val system = ActorSystem(Behaviors.empty, "my-system")

  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.executionContext


  case class prodReq(id :Long)



  val prodRoute = {
    concat(
      path("products") {
        get {
          val res = findProd
          onSuccess(res){
            case p@Seq((_,_,_),_*) => {
              var result = List[Product]()
                for(prod <- p){
                  println(prod)
                  result = result :+ getProduct(prod)
                }
              println(result.length)
              complete(StatusCodes.OK,result)
            }
            case _ => println(res);complete(StatusCodes.OK,"Nothing here")
          }
        }
      },
    pathPrefix("product") {
      path(Segment){ req =>
       get {
              val res = findProd(req.value.toLong)
              onSuccess(res) {
                case Seq(p@(_,_,_)) => complete(StatusCodes.OK, ProductRepository.getProduct(p))
                case _ => complete(StatusCodes.NotFound, "No such product")
              }
            }
          }
      },
      pathPrefix("product"){
        put{
          entity(as[Product]){
            req => {
              onSuccess(presistProduct(req))(_ => complete(StatusCodes.OK, s"Successfull peristed the product with id ${req.id}"))
            }
          }
        }
      }
    )
    }
  val basketRoute = {
    concat(
      path("baskets") {
        post {
          entity(as[BasketRequest]) {
            baskReq => {
              createBasket(baskReq)
            }
          }
        }
      },
      path("baskets"){
         get {
             complete(StatusCodes.OK,baskets.values)
           }
       },
      pathPrefix("baskets"){
                              path(Segment){ req =>
                                put {
                                  entity(as[BasketRequest]){ br =>
                                        addToBasket(req,br)
                                  }
                                }
                              }
      }
    )
  }
  val orderRoute = {

      pathPrefix("order") {
        path(Segment) { basId =>
          post {
            saveOrder(basId,baskets.get(basId))
            complete(StatusCodes.OK,"Order Persisted")
          }
        }
      }
  }
  val allOrders = {
    path("orders") {
      get {
        val orders = getAllOrders()
        orders.onComplete{println(_)}
        onSuccess(orders) {
          case b :mutable.Map[String,Basket] =>
            //closeDBConn()
            complete(StatusCodes.OK, b.values)
          case _ => complete(StatusCodes.OK,"Nothing to show")
        }
      }
    }
  }
  val allRoutes = concat(prodRoute,basketRoute,orderRoute,allOrders)

  Http().newServerAt("localhost", 8084).bind(allRoutes)

  }





