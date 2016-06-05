package com.movies.provider

import scala.collection.mutable.ListBuffer
import org.elasticsearch.common.settings.Settings
import com.movies.model.Movie
import com.sksamuel.elastic4s.ElasticClient
import com.sksamuel.elastic4s.ElasticDsl._
import com.sksamuel.elastic4s.ElasticsearchClientUri
import com.sksamuel.elastic4s.IndexAndTypes.apply
import com.sksamuel.elastic4s.mappings.FieldType._
import com.sksamuel.elastic4s.source.Indexable
import play.api.libs.json.Json
import com.sksamuel.elastic4s.analyzers._
import com.movies.utils.MovieUtils
import org.slf4j.LoggerFactory
import com.typesafe.scalalogging.Logger

case class ElasticSearchProvider(client: ElasticClient) {
  val logger = Logger(LoggerFactory.getLogger("ElasticSearchProvider"))
  
  def createIndex: Unit = {
    client.execute { deleteIndex("movies") }
    client.execute {
      create index "movies" shards 1 replicas 1 refreshInterval "5s" mappings (
        "movie" as (
          "actor" typed StringType analyzer "actor_analyzer" omitNorms true,
          "year" typed IntegerType includeInAll false,
          "name" typed StringType index "not_analyzed")) analysis (
            CustomAnalyzerDefinition(
              "actor_analyzer",
              PatternTokenizer("remove_not_alpha_numeric", pattern = "[^0-9a-zA-Z]+"),
              LowercaseTokenFilter,
              TrimTokenFilter,
              ApostropheTokenFilter,
              AsciiFoldingTokenFilter,
              HtmlStripCharFilter),
              CustomAnalyzerDefinition(
                "name_analyzer",
                PatternTokenizer("remove_not_alpha_numeric", pattern = "[^0-9a-zA-Z]+"),
                LowercaseTokenFilter,
                TrimTokenFilter,
                ApostropheTokenFilter,
                AsciiFoldingTokenFilter,
                HtmlStripCharFilter,
                MappingCharFilter("mapping_charfilter", "ph" -> "f", "qu" -> "q"),
                KStemTokenFilter))
    }
  }

  def createListActors(actors: ListBuffer[String]): String = {
    Json.stringify(Json.toJson(actors.result()))
  }

  implicit object MovieIndexable extends Indexable[Movie] {
    override def json(t: Movie): String = {
      val json = s""" { "name" : "${t.name}", "actors" : ${createListActors(t.actors)}, "year" : ${t.year} } """
      logger.info(json)
      json
    }
  }

  def indexMovie(line: String): Unit = {
    val movie = MovieUtils.parseLine(line);
    movie match {
      case Some(movie) => {
        val res = client.execute {
          index into "movies" -> "movie" id movie.id source movie
        }
      }
      case None => Unit
    }
  }
}
