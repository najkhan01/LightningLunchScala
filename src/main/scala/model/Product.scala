package com.najkhan.lightlunch
package model
//import org.http4s.Entity





case class Product( val id:String, productType:String,  attributes:Attributes)

case class Attributes(  description:String,   name:String,   partNumber:String,
                        brand:String, price:ProductPrice)

case class ProductPrice( now:Double, flashText:String, mealDeal:Boolean)

/*
    ToDo:
    - Get product using product id - Done
    - Post create basket - Done
    - Put add items to basket - Done
    - Get basket using basket id - Done
 */



