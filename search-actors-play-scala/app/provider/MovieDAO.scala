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

package provider

import scala.collection.JavaConversions._
import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Failure
import scala.util.Success
import org.elasticsearch.action.search.SearchResponse
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.search.sort.SortOrder
import com.sksamuel.elastic4s.ElasticClient
import com.sksamuel.elastic4s.ElasticDsl._
import com.sksamuel.elastic4s.ElasticsearchClientUri
import com.sksamuel.elastic4s.HitAs
import com.sksamuel.elastic4s.RichSearchHit
import com.sksamuel.elastic4s.RichSearchResponse
import com.typesafe.config.ConfigFactory
import model.Movie
import play.api.Logger
import java.util.ArrayList

case class MovieDAO(elasticClient: ElasticClient, index: String, types: String) {
  val ACTOR = "actor"
  val ID_MOVIE = "idMovie"

  def searchMovie(queryValue: String, typeSearch: String): List[Movie] = {
    val res: Future[RichSearchResponse] = {
      val queryJson = typeSearch match{
        case "actor" => queryByActor(queryValue)
        case "idMovie"=> queryIdMovie(queryValue)
      }
      Logger.info("QueryString: " + queryJson)
      elasticClient execute {
        queryJson
      }
    }
    var movies: List[Movie] = null

    res onComplete {
      case Success(s) => { movies = parseMovie(s) }
      case Failure(t) => println("An error has occured: " + t)
    }

    println("Request sent for term " + queryValue)
    Thread.sleep(1000)
    movies
  }

  private def queryByActor(queryValue: String) = {
      search in index / types  query  matchPhraseQuery("actors", queryValue).slop(3) start 0 limit 20 sort (field sort "year" order SortOrder.DESC)
  }

  private def queryIdMovie(id: String) = {
      search in index / types  query termQuery("_id", id)
  }

  implicit object MovieHitAs extends HitAs[Movie] {
    override def as(hit: RichSearchHit): Movie = {
      val id = hit.getId
      val name = hit.sourceAsMap("name").toString
      val year = hit.sourceAsMap("year").toString.toInt
       val actors : List[String] =  hit.sourceAsMap("actors").asInstanceOf[ArrayList[String]].toArray().toList.asInstanceOf[List[String]]
      Movie(id, name, year, actors)
    }
  }

  private def parseMovie(resp: RichSearchResponse): List[Movie] = {
    resp.as[Movie].toList
  }
}
