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

package controllers

import scala.collection.mutable.ListBuffer
import model.Movie
import model.SearchData
import play.api.data._
import play.api.data.Forms._
import play.api.i18n.I18nSupport
import play.api.mvc.Action
import play.api.mvc.Controller
import play.api.i18n.MessagesApi
import javax.inject.Inject
import play.api.Logger
import com.typesafe.config.ConfigFactory
import provider.ElasticClientBuilder
import provider.MovieDAO

/**
 * Class responsible to handle search and show the home
 */
case class Application @Inject()(val messagesApi: MessagesApi) extends Controller with I18nSupport  {
        
  private val indexMovie = ConfigFactory.load().getString("elasticsearch.index");
  private val typesMovie= ConfigFactory.load().getString("elasticsearch.type");
  private val client = ElasticClientBuilder.getElasticClient();
  private val movieDAO = new MovieDAO(client, indexMovie, typesMovie)
  
  
  /**
   * Index of the application
   */
  def index() = Action { implicit request =>
    Ok(views.html.index(SearchData.searchForm))
  }
  
  /**
   * Action responsible to validade the request, do a search and return the list of movies from one actor
   */
  def search() = Action { implicit request =>
      SearchData.searchForm.bindFromRequest.fold(
      formWithErrors => Ok(views.html.index(formWithErrors)),
      searchData => {
        SearchData.searchForm.fill(searchData)
        val movies : List[Movie] = movieDAO.searchMovie(searchData.query, movieDAO.ACTOR);
        Ok(views.html.search(SearchData.searchForm, movies))
        }
    )
  }
  
  def show(id: String) = Action { implicit request => 
     val movies : List[Movie] = movieDAO.searchMovie(id, movieDAO.ID_MOVIE);
     Ok(views.html.movie_detail(SearchData.searchForm, movies(0)))   
  }
}