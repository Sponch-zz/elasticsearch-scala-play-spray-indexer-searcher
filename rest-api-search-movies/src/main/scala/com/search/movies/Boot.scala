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

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import spray.can.Http
import akka.pattern.ask
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import scala.concurrent.duration._

object Boot extends App {

  val config = ConfigFactory.load

  // we need an ActorSystem to host our application in
  implicit val system = ActorSystem("movie-search")

  // create and start our service actor
  val service = system.actorOf(Props[SearchMovieActor], "movie-search")

  implicit val timeout = Timeout(50.seconds)
  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http) ? Http.Bind(service, interface = config.getString("movie-search.bind"), port = config.getInt("movie-search.port"))
}
