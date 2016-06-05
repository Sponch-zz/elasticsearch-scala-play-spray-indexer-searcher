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

import com.sksamuel.elastic4s.ElasticClient
import com.typesafe.config.ConfigFactory
import com.sksamuel.elastic4s.ElasticsearchClientUri
import org.elasticsearch.common.settings.Settings
import com.typesafe.config.Config

case class MovieProvider(config: Config) {
  private val elasticEndpoint = config.getString("elasticsearch.endpoint");
  private val elasticCluster = config.getString("elasticsearch.cluster");
  private val index = config.getString("elasticsearch.index");
  private val types = config.getString("elasticsearch.type");

  private val uri = ElasticsearchClientUri(elasticEndpoint)
  private val settings = Settings.settingsBuilder().put("cluster.name", elasticCluster).build()
  private val elasticClient = ElasticClient.transport(settings, uri)
  def getElasticClient(): ElasticClient = elasticClient

}
