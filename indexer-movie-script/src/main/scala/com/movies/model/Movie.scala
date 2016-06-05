package com.movies.model

import scala.collection.mutable.ListBuffer

case class Movie(id: String, name: String, year : Int ){

  private var actors_ = new ListBuffer[String]()

  def addActor(actor: String): Unit = {
    actors_ += actor
  }

  def containsActor(actor: String): Boolean = {
    actors_.contains(actor);
  }

  def actors : ListBuffer[String] = {
    actors_
  }
}
