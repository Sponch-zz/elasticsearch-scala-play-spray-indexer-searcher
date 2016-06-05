package com.movies.utils

import org.json.simple.JSONObject

import com.movies.model.Movie

case object MovieUtils {

  val actorPattern = "^(.+?)\\s{2,2}(.+?)\\s\\((\\d{4})\\).+\\[.+?\\].*".r
  var lastActor: Option[String] = None;
  var setMovies = Map[String, Movie]()

  def createIDFromString(string: String): String = {
    val stringAux = string.toLowerCase().replaceAll("[^0-9a-z]+", "").trim()
    val md = java.security.MessageDigest.getInstance("SHA-1")
    md.digest(stringAux.getBytes("UTF-8")).map("%02x".format(_)).mkString
  }

  def reset() : Unit = {
    lastActor = None
    setMovies = Map[String, Movie]()
  }

  def parseLine(line: String): Option[Movie] = {
    var movie: Option[Movie] = None
    if (actorPattern.findFirstMatchIn(line).size > 0) {
      val actorPattern(actor, movieName, year) = line
      var vActor = normalize(Some(actor))
      var vMovieName = normalize(Some(movieName))
      if (!isEmpty(Some(vMovieName.get))) {
        if (!isEmpty(Some(actor))) {
          lastActor = Some(actor.trim());
        }
        val cachedMovie = setMovies.get(vMovieName.get.toLowerCase())
        if (cachedMovie != None) {
          movie = Some(cachedMovie.get)
          if (movie.exists { m => !m.containsActor(lastActor.get) }) {
              movie.map { movie => movie.addActor(lastActor.get) }
          }else{
            movie = None
          }
        } else {
          movie = Some(Movie(createIDFromString(vMovieName.get), vMovieName.get, year.toInt))
          movie.map { movie => movie.addActor(lastActor.get) }
          setMovies += (normalize(Some(movieName.toLowerCase())).get -> movie.get)
        }
      }
    }
    movie
  }

  def isEmpty(text: Option[String]): Boolean = {
    text match{
      case Some(text) => text.trim() == ""
      case None => false
    }
  }

  def normalize(text: Option[String]): Option[String] = text match {
      case Some(text) if !isEmpty(Some(text)) =>
        Some(JSONObject.escape(text.replaceAll("\"", "").trim()))
      case _ => None
  }
}
