package com.movies.model

import org.scalatest.FlatSpec

class MovieSpec extends FlatSpec {

  "When create a movie" should "be empty the actor list" in {
    val movie = Movie("123", "name", 1234)
    assert(movie.actors.size == 0)
  }

  "When add an actor to a movie" should "be not empty the actor list" in {
    val movie = Movie("123", "name", 1234)
    movie.addActor("actor1")
    movie.addActor("actor2")
    assert(movie.actors.size == 2)
  }
  
  "When add an actor to a movie" should "return true when check list" in {
    val movie = Movie("123", "name", 1234)
    movie.addActor("actor1")
    assert(movie.containsActor("actor1") == true)
  }
  
  "When search for an actor that doesnt exist in a movie" should "return false" in {
    val movie = Movie("123", "name", 1234)
    movie.addActor("actor1")
    assert(movie.containsActor("actor2") == false)
  }
}