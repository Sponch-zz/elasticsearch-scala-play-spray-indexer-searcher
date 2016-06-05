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

package com.search.movies

import scala.concurrent.ExecutionContextExecutor
import com.search.movies.provider.MovieDAO
import com.typesafe.config.ConfigFactory
import akka.actor.Actor
import akka.actor.ActorContext
import spray.httpx.SprayJsonSupport
import spray.httpx.marshalling.ToResponseMarshallable.isMarshallable
import spray.routing.Directive.pimpApply
import spray.routing.HttpService
import spray.routing.directives.ParamDefMagnet.apply
import com.search.movies.provider.MovieJsonProtocol
import com.search.movies.provider.MovieProvider

class SearchMovieActor extends Actor with HttpService {

  val actorRefFactory = context: ActorContext
  implicit val executionContext = actorRefFactory.dispatcher: ExecutionContextExecutor

  import MovieJsonProtocol._
  import spray.httpx.SprayJsonSupport._

  val config = ConfigFactory.load()
  val movieProvider = MovieProvider(config)
  val movieDAO = MovieDAO(movieProvider.getElasticClient(), config)

  override def receive: Receive = runRoute(
      parameters('query, 'rows ?10, 'start?0) { (query, rows,start) =>
      get {
        complete(movieDAO.searchMovie(query, rows, start))
      }
    })
}
