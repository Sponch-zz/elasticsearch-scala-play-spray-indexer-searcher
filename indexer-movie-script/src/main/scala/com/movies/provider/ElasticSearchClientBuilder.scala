package com.movies.provider

import com.sksamuel.elastic4s.ElasticClient
import com.sksamuel.elastic4s.ElasticsearchClientUri
import org.elasticsearch.common.settings.Settings

trait ElasticSearchBuilder{
   def client: ElasticClient
}

case class ElasticSearchClientBuilder(endpoint: String, cluster: String) extends ElasticSearchBuilder{

  def client: ElasticClient = {
    val uri = ElasticsearchClientUri(endpoint)
    val settings = Settings.settingsBuilder().put("cluster.name", cluster).build()
    ElasticClient.transport(settings, uri)
  }
}
