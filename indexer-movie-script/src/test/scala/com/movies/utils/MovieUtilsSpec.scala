package com.movies.utils

import org.scalatest.FlatSpec

class MovieUtilsSpec  extends FlatSpec {

  val utils = MovieUtils
  
  it should "normalize one string with escape and trim" in {
    utils.reset()
    assert(utils.normalize(Some("""    "teste\/\r\n\b\f\t      """)) == Some("""teste\\\/\\r\\n\\b\\f\\t"""))
  }
  
  it should "not normalize when its a empty string" in {
    utils.reset()
    assert(utils.normalize(Some("  ")) == None)
  }
  
  it should "not normalize when its a null string" in {
    utils.reset()
    assert(utils.normalize(None) == None)
  }

  it should "validate as true if string is empty with null" in {
    utils.reset()
    assert(utils.isEmpty(None) == false)
  }
  
  it should "validate as true if string is empty with empty string" in {
    utils.reset()
    assert(utils.isEmpty(Some("          ")) == true)
  }
  
  it should "validate as false if string is not empty " in {
    utils.reset()
    assert(utils.isEmpty(Some("Test")) == false)
  }
  
  it should "not create a movie when line doenst match the regexp" in {
    utils.reset()
    assert(utils.parseLine("Copyright 1990-2007 The Internet Movie Database, Inc.  All rights reserved.") == None)
  }
  
  it should "create a movie when match the regexp" in {
   utils.reset()
    val movie = utils.parseLine("""$, Claw                 "OnCreativity" (2012)  [Himself]""") 
    assert(movie != null)
    assert(movie.get.name == "OnCreativity")
    assert(movie.get.year == 2012)
    assert(movie.get.actors.size == 1 && movie.get.actors(0) == "$, Claw")
  }
  
  it should "add the same actor to a movie when didnt get the group of actor" in {
    utils.reset()
    var movie = utils.parseLine("""$, Claw                 "OnCreativity" (2012)  [Himself]""")
    movie = utils.parseLine("""                        "OnCreativity 2" (2012)  [Himself]""")
    assert(movie != None)
    assert(movie.get.name == "OnCreativity 2")
    assert(movie.get.year == 2012)
    assert(movie.get.actors.size == 1 && movie.get.actors(0) == "$, Claw")
  }
  
  it should "not return movie when information is the same" in {
    utils.reset()
    var movie = utils.parseLine("""$, Claw                 "OnCreativity" (2012)  [Himself]""")
    movie = utils.parseLine("""$, Claw                 "OnCreativity" (2012)  [Himself]""")
    assert(movie == None)
  }
  
  it should "add others actor to the same movie" in {
    utils.reset()
    var movie = utils.parseLine("""$, Claw                 "OnCreativity" (2012)  [Himself]""")
    movie = utils.parseLine("""John                 "OnCreativity" (2012)  [Himself]""")
    movie = utils.parseLine("""Carlos                 "OnCreativity" (2012)  [Himself]""")
    assert(movie != None)
    assert(movie.get.name == "OnCreativity")
    assert(movie.get.year == 2012)
    assert(movie.get.actors.size == 3 && movie.get.actors(0) == "$, Claw" && movie.get.actors(1) == "John" && movie.get.actors(2) == "Carlos")
  }
  
  it should "add others actor to the same movie even with differences in case" in {
   utils.reset()
    var movie = utils.parseLine("""$, Claw                 "ONCREATIVITY" (2012)  [Himself]""")
    movie = utils.parseLine("""John                 "OnCreativity" (2012)  [Himself]""")
    assert(movie != None)
    assert(movie.get.actors.size == 2)
  }
}