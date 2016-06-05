package com.movies

import com.lexicalscope.jewel.cli.Option

/**
 * Trait responsible to get information from command line related to path where the file to be parsed are,
 * the endpoint of elasticsearch and the name of the cluster
 */
trait IndexerConfiguration {
  @Option def path: String
  @Option def elasticendpoint: String
  @Option def elasticcluster: String
}
