package com.najkhan.lightlunch
package service

import model.persistance.ProductRepository.{findProd, getProduct}
import model.{Basket, BasketRequest, Product}
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.{complete, onSuccess}
import scala.concurrent.ExecutionContext.Implicits.global
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import JsonConversion.JsonConversion
import spray.json.DefaultJsonProtocol.{StringJsonFormat, tuple2Format}

object BasketService extends JsonConversion{

  var baskets  =  collection.mutable.Map[String,Basket]()

  def createBasket(basketRequest: BasketRequest) = {
      val aa = Basket().load(basketRequest)
      onSuccess(aa) {
        value => {
          baskets += (value.id -> value)
          complete(StatusCodes.OK, value)
        }
      }
    }
    def addToBasket(id :String,basketRequest: BasketRequest) = {
      baskets.filter(_._1 == id).toList match {
        case Nil => complete(StatusCodes.OK,"No such basket")
        case head :: Nil =>  baskets -= head._1
          val prod =findProd(basketRequest.productId).map(x => getProduct(x.head))
          onSuccess(prod){
            case p@Product(_,_,_) => val bas = (head._1,head._2.addToBasket(p,basketRequest.quantity))
              baskets += bas
              complete(StatusCodes.OK,bas)
          }

      }
    }

}
