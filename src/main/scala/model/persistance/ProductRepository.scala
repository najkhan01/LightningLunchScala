package com.najkhan.lightlunch
package model.persistance

import model.{Attributes, Basket, BasketItem, Product, ProductPrice}

import akka.stream.alpakka.slick.scaladsl.SlickSession
import org.checkerframework.checker.units.qual.{m, s}
import slick.ast.Library.Max
import slick.jdbc.PostgresProfile.api._

import scala.collection.mutable
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util.Try
import scala.util.control.Exception

object ProductRepository extends DBSchema {

  implicit val session: SlickSession = SlickSession.forConfig("postgres")

  def getProduct(prod : (pProduct,Option[pAttributes],Option[PPrice])): Product ={
    val pPri = prod._3.get
    val pAtt = prod._2.get
    val pPro = prod._1
    val pri = ProductPrice(pPri.now,pPri.flashText,pPri.mealDeal)
    val att = Attributes(pAtt.description,pAtt.name,pAtt.partNumber,pAtt.brand,pri)
    Product(pPro.id.toString,pPro.productType,att)
  }

  def findProd(id: Long) = {
    //val qer = pProducts.filter(_.id === id)
    val quer = for {
      ((prod, att), pri) <- ((pProducts joinLeft prodAttributes on (_.pAttrib === _.id)) joinLeft prodPrices on (_._2.map(_.priceId) === _.id)).filter(_._1._1.id === id)
    } yield (prod, att, pri)
    session.db.run(quer.result)
  }

  def findProd = {
    //val qer = pProducts.filter(_.id === id)
    val quer = for {
      ((prod, att), pri) <- ((pProducts joinLeft prodAttributes on (_.pAttrib === _.id)) joinLeft prodPrices on (_._2.map(_.priceId) === _.id))
    } yield (prod, att, pri)
    session.db.run(quer.result)
  }

  def saveOrder(id :String,basket :Option[Basket]) = {
    basket.foreach(_.basketItems.foreach(x =>{
                session.db.run(pBaskets += bBasket(id,x.product.id.toLong,x.numberOfItems))
    }))
    //session.db.close()
  }

  def getAllOrders() = {
    val quer = session.db.run(pBaskets.result)
    val baskets = quer.map(_.map(x => {
      findProd(x.product)}
      .map(y => {
        Basket(x.id, List(BasketItem(getProduct(y.head), x.quantity)))
      }
    ))).map(x => x.foldRight(Future(mutable.Map[String,Basket]()))((next,acc) => {
      acc.map { s =>
      next.map(z => {
          if (s.isDefinedAt(z.id)) {
            val bas = s.get(z.id)
            s -= bas.get.id.toString
            val newBas = bas.get.addToBasket(z.basketItems.head.product, z.basketItems.head.numberOfItems)
            s += (z.id -> newBas)
          } else {
            s += (z.id -> z)
          }
          acc
        })
      }.flatten
    }.flatten)).flatten
    baskets
  }
  def presistProduct(prod :Product) ={
    val atIds = prodAttributes.map(_.id).max.result
    val prIds = prodPrices.map(_.id).max.result
    val maxAt =  session.db.run(atIds)
    val maxPr = session.db.run(prIds)
    maxAt.map(aId => maxPr.map(pId => {
      session.db.run(DBIO.seq(
        prodPrices += PPrice(pId.get +1L, prod.attributes.price.now.toLong, prod.attributes.price.flashText,
          prod.attributes.price.mealDeal),
       prodAttributes += pAttributes(
        aId.get + 1L,prod.attributes.description,prod.attributes.name,
       prod.attributes.partNumber,prod.attributes.brand,pId.get + 1L),
     pProducts += pProduct(prod.id.toLong,prod.productType,aId.get + 1L))
     ).andThen(x => x)
    })).flatten
  }


}
