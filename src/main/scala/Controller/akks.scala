package com.najkhan.lightlunch
package Controller

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import org.checkerframework.checker.units.qual.s

object akks extends App{
  case class greet(name :String)

  val ac = Behaviors.receiveMessage[greet]{
    msg => {
      println(s"Hello ${msg.name}")
      Behaviors.stopped
      //Behaviors.empty
    }
  }

  val acSys = ActorSystem(ac,"greeter")

  acSys ! greet("Naj")
}

