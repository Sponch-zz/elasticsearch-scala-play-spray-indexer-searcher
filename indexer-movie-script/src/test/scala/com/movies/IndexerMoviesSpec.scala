package com.movies

import org.scalatest._
import org.mockito.Mockito.mock
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.when
import com.movies.provider.ElasticSearchProvider
import com.movies.provider.FileProvider
import org.mockito.internal.matchers.Any

class IndexerMoviesSpec extends FlatSpec {

  var service = mock(classOf[FileProvider])
  
  
//  it should "process files and send to elasticsearch" in {
//      when(service.read("any/file",Any())).doNothing() 
//  }

  
 
}