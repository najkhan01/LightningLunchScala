package com.najkhan.lightlunch
package model.persistance

case class pProduct(val id:Long, val productType:String, val attributes:Long)

case class pAttributes(id :Long, description:String, name:String, partNumber:String,
                       brand:String, priceId:Long)




case class PPrice(id :Long, now:Long, flashText:String, mealDeal:Boolean)

case class bBasket(id: String,product :Long,quantity :Int)
