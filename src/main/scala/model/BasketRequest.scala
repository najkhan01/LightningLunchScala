package com.najkhan.lightlunch
package model

import java.util.UUID

case class BasketRequest(val productId :Long, var quantity :Int){
  def setQuantity(quantity :Int): Unit ={
    this.quantity += quantity
  }
}
