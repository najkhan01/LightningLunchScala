package com.najkhan.lightlunch
package Controller

import com.najkhan.lightlunch.model.persistance.{PPrice, ProductRepository}
import com.najkhan.lightlunch.model.persistance.ProductRepository.{pBaskets, prodPrices}
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration
//import service.ProductService._

import slick.backend.StaticDatabaseConfig
import com.najkhan.lightlunch.model.persistance.DBSchema
//import com.najkhan.lightlunch.service.ProductService
import akka.stream.alpakka.slick.scaladsl._
import slick.collection.heterogeneous.Zero.{+, value}
import slick.collection.heterogeneous.syntax.::
import slick.dbio.Effect.Write
import slick.jdbc.{ActionBasedSQLInterpolation, PostgresProfile}

import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

//@StaticDatabaseConfig("file:/src/main/resources/application.conf#tsql")
object Loader extends App {

  //implicit executionContext = ExecutionContext.global
  //ProductService.loadData()
  //products.foreach(x => println(x.attributes))
  //val db= Database.forConfig("mydatabase")
  val x = "xxxx"
  implicit val session: SlickSession = SlickSession.forConfig("postgres")

//  var pricId = 1L
//  var arribId = 1L
//  var prices :List[String] = List()
//  var attribs  :Seq[DBIO[Int]] = Seq[DBIO[Int]]()
//  var prodProducts:Seq[DBIO[Int]] = Seq[DBIO[Int]]()
  //for(prod <- products) {


  //  val attr = prod.attributes

    session.db.run( prodPrices += PPrice(111,111,x,true))
    //session.db.run( prodAttributes += pAttributes(arribId,attr.description,attr.name,attr.partNumber,attr.brand,pricId))
    //session.db.run(pProducts += pProduct(prod.id.toLong,prod.productType,arribId))
//    pricId += 1
//    arribId += 1
 // }

  //println(pricId +" "+ arribId)
//  prices.foreach(x => println(x.toString + "val"))
//  for(i <- prices) println(i + " val")

//  def findProd(id: Long) = {
//    //val qer = pProducts.filter(_.id === id)
//    val quer = for {
//      ((prod, att), pri) <- ((pProducts joinLeft prodAttributes on (_.pAttrib === _.id)) joinLeft prodPrices on (_._2.map(_.priceId) === _.id)).filter(_._1._1.id === id)
//    } yield (prod, att, pri)
//    session.db.run(quer.result)
//  }
  //val qer = sqlu"""select * from products where id = $id"""


//    kk.onComplete{
//      case Success(value) => println(value)
//    }
//
//    println(quer)


  //findProd(1320121)

//  val kk =
//    sqlu"CREATE TABLE IF NOT EXISTS baskets(id varchar(255),prod_id BIGINT,quantity INTEGER,PRIMARY KEY(id,prod_id),FOREIGN KEY(prod_id) REFERENCES products(id));"
//  session.db.run(pBaskets.schema.create)

  //session.close()


}
