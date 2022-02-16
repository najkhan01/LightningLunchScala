package com.najkhan.lightlunch
package Actors

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import com.najkhan.lightlunch.Controller.AkkaProductController.{prodReq, prodReqAc}
import com.najkhan.lightlunch.model.guard
import com.najkhan.lightlunch.model.persistance.ProductRepository.{findProd, getProduct}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object ProdActor {

  case class prodAc(p :Future[Product]) extends guard
  def apply() :Behaviors.Receive[prodReqAc] = Behaviors.receive {
    case (_,prodReqAc(msg, to))
    =>
    val rep = findProd(msg).map {
      x => {
        getProduct(x.head)
      }
    }
      to ! prodAc(rep)
      Behaviors.same
  }

}
