package com.najkhan.lightlunch
package model.persistance


import slick.jdbc.PostgresProfile.api._

trait DBSchema {


  class Price(tag: Tag) extends Table[PPrice](tag, "price") {
    def id = column[Long]("id", O.PrimaryKey)

    def now = column[Long]("now")

    def flashText = column[String]("flashtext")

    def mealDeal = column[Boolean]("mealdeal")

    override def * = (id, now, flashText, mealDeal) <> (PPrice.tupled, PPrice.unapply)
  }

  var prodPrices = TableQuery[Price]

  class Attribute(tag: Tag) extends Table[pAttributes](tag, "attributes") {
    def id = column[Long]("id",O.PrimaryKey)

    def description = column[String]("discription")

    def name = column[String]("name")

    def partNumber = column[String]("part_number")

    def brand = column[String]("brand")

    def priceId = column[Long]("price")

    def pPrice = foreignKey("FK_PRICE",priceId,prodPrices)(_.id)

    override def *  = (id, description, name, partNumber, brand, priceId) <> (pAttributes.tupled,pAttributes.unapply)

  }

  var prodAttributes = TableQuery[Attribute]

  class proProducts(tag :Tag) extends Table[pProduct](tag, "products"){
    def id = column[Long]("id", O.PrimaryKey)
    def productType = column[String]("product_type")
    def pAttrib = column[Long]("attributes")
    override def * = (id,productType,pAttrib) <> (pProduct.tupled,pProduct.unapply)

    def attrib = foreignKey("attrib_FK",pAttrib,prodAttributes)(_.id)
  }

  var pProducts = TableQuery[proProducts]

  class dbBasket(tag :Tag) extends Table[bBasket](tag, "baskets"){
    def id = column[String]("id",O.PrimaryKey)
    def prodId = column[Long]("prod_id", O.PrimaryKey)
    def quantity = column[Int]("quantity")
    override def * = (id,prodId,quantity) <> (bBasket.tupled,bBasket.unapply)

    def basketProdFK = foreignKey("prod_basket_FK",prodId,pProducts)(_.id)
  }

  var pBaskets = TableQuery[dbBasket]

  def allSchemas = prodPrices.schema ++ prodAttributes.schema ++ pProducts.schema ++ pBaskets.schema

}


