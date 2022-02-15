package com.najkhan.lightlunch
package model

import com.najkhan.lightlunch.model.persistance.{PPrice, ProductRepository, pAttributes, pProduct}
import com.najkhan.lightlunch.model.persistance.ProductRepository.{findProd, getProduct}

import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}


case class BasketItem(product :Product,numberOfItems :Int) {}

case class Basket(id  :String= UUID.randomUUID().toString,basketItems: List[BasketItem] = Nil){
//  def this(id :String,basketItems :List[BasketItem]) = {
//    this(id,basketItems)
//  }
  def addToBasket(product :Product,quantity :Int): Basket ={
    this.copy(basketItems = basketItems :+ BasketItem(product, quantity))
  }
  def load(basketRequest :BasketRequest) = {
    val product = findProd(basketRequest.productId)

    product.map(x => Basket(this.id,List(BasketItem(getProduct(x.head), basketRequest.quantity))))

  }
}

