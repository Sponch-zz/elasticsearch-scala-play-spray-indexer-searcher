package com.movies

import com.movies.provider.ReadFileProvider
import com.movies.provider.ElasticSearchProvider
import com.movies.utils.MovieUtils
import com.lexicalscope.jewel.cli.CliFactory
import com.lexicalscope.jewel.cli.ArgumentValidationException
import com.movies.provider.ElasticSearchClientBuilder
import org.slf4j.LoggerFactory
import com.typesafe.scalalogging.Logger

object IndexerMovies {
  def main(args: Array[String]): Unit = {
    val logger = Logger(LoggerFactory.getLogger("IndexerMovies"))
    val usage = """Usage: IndexerMovies --elasticendpoint <endpoint> --elasticcluster <cluster> --path </path/to/file/movie>\n
      Example: IndexerMovies --elasticendpoint "elasticsearch://127.0.0.1:9300" --elasticcluster "holiday" --path "/Users/user/Downloads/actors.list" """

    try {
      val config: IndexerConfiguration = CliFactory.parseArguments(classOf[IndexerConfiguration], args: _*)

      val client = ElasticSearchClientBuilder(config.elasticendpoint, config.elasticcluster).client
      val elasticIndexer = new ElasticSearchProvider(client)
      elasticIndexer.createIndex

      ReadFileProvider.read(config.path, elasticIndexer.indexMovie)

    } catch {
      case e: ArgumentValidationException => { logger.info(usage, e.getMessage) }
    }
  }
}
