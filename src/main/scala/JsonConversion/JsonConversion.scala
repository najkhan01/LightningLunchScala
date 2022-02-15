package com.najkhan.lightlunch
package JsonConversion

import Controller.AkkaProductController.prodReq
import model.{Attributes, Basket, BasketItem, BasketRequest, Product, ProductPrice}

import spray.json.DefaultJsonProtocol.{BooleanJsonFormat, DoubleJsonFormat, IntJsonFormat, LongJsonFormat, StringJsonFormat, jsonFormat, jsonFormat1, jsonFormat2, jsonFormat3, jsonFormat5, listFormat}

import java.util.UUID

trait JsonConversion {

  implicit val prodReqFormat = jsonFormat1(prodReq)
  implicit val repPrice = jsonFormat3(ProductPrice)
  implicit val repAttribute = jsonFormat5(Attributes)
  implicit val prodRetFormat = jsonFormat3(Product)
  implicit val bskItemFormat = jsonFormat2(BasketItem)
  implicit val basketFormat = jsonFormat2(Basket)
  implicit val baketReqFormat = jsonFormat2(BasketRequest)

}
