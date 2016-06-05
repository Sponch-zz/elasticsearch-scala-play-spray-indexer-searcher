// Copyright (C) 2011-2016 the original author or authors.
// See the LICENCE.txt file distributed with this work for additional
// information regarding copyright ownership.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.search.movies.provider

import java.util.ArrayList
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import org.elasticsearch.search.sort.SortOrder
import com.sksamuel.elastic4s.ElasticClient
import com.sksamuel.elastic4s.ElasticDsl._
import com.sksamuel.elastic4s.HitAs
import com.sksamuel.elastic4s.IndexesAndTypes.apply
import com.sksamuel.elastic4s.RichSearchHit
import com.sksamuel.elastic4s.RichSearchResponse
import com.typesafe.config.Config
import spray.json._
import spray.json.DefaultJsonProtocol
import org.slf4j.LoggerFactory
import com.typesafe.scalalogging.Logger


case class Movie(
  _id: String,
  name: String,
  year: Int,
  actors: List[String])

object MovieJsonProtocol extends DefaultJsonProtocol {
  implicit object MovieJsonFormat extends RootJsonFormat[Movie] {
    def write(m: Movie) : JsObject = JsObject(
      "id" -> JsString(m._id),
      "name" -> JsString(m.name),
      "year" -> JsNumber(m.year),
      "actors" -> JsArray(m.actors.toVector.map { actor => JsString(actor) }))
    def read(value: JsValue) : Movie = {
      value.asJsObject.getFields("id", "name", "year", "actors") match {
        case Seq(JsString(id), JsString(name), JsNumber(year), JsArray(actors)) =>
          new Movie(id, name, year.toInt, actors.toList.map { value => value.toString() })
        case _ => throw new DeserializationException("Color expected")
      }
    }
  }
}

case class MovieDAO(elasticClient: ElasticClient, config: Config) {
  val logger = Logger(LoggerFactory.getLogger("MovieDAO"))
  val index = config.getString("elasticsearch.index")
  val types = config.getString("elasticsearch.type")

  def searchMovie(queryValue: String, rows: Int, startValue: Int): Future[List[Movie]] = {
    val query = search in index / types query {
                    matchPhraseQuery("actors", queryValue).slop(3)
                  } start startValue limit rows sort {
                    (field sort "year" order SortOrder.DESC)
                  }
    logger.info("Searching for query: " + queryValue)

    elasticClient.execute { query }.map { result => parseMovie(result) }
  }

  implicit object MovieHitAs extends HitAs[Movie] {
    override def as(hit: RichSearchHit): Movie = {
      val id = hit.getId
      val name = hit.sourceAsMap("name").toString
      val year = hit.sourceAsMap("year").toString.toInt
      val actors: List[String] = hit.sourceAsMap("actors").asInstanceOf[ArrayList[String]].toArray().toList.asInstanceOf[List[String]]
      Movie(id, name, year, actors)
    }
  }

  private def parseMovie(resp: RichSearchResponse): List[Movie] = {
    resp.as[Movie].toList
  }
}
