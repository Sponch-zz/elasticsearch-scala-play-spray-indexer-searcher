package com.movies.provider

import org.mockito.Mockito._
import org.scalatest.BeforeAndAfter
import org.scalatest.FunSuite
import org.scalatest.mock.MockitoSugar
import com.movies.model.Movie
import com.sksamuel.elastic4s.ElasticClient
import org.mockito.Matchers._

class ElasticSearchProviderSpec extends FunSuite with BeforeAndAfter with MockitoSugar {

  test("generate Exception when host is invalid") {
    intercept[IllegalArgumentException] {
     val client = ElasticSearchClientBuilder("", "").client
    }
  }
  
  test( "create json from a movie") {
    val movie = Movie("123", "name", 2000)
    movie.addActor("actor1")
    movie.addActor("actor2")
    val builder = mock[ElasticSearchBuilder]
    val client = mock[ElasticClient]
    //when(client.execute(anyObject(),anyObject(),anyObject(),anyObject()).thenReturn(None)
   // val json = ElasticSearchProvider(client).indexMovie(line)
    
   // assert(json == 0)
  }

}