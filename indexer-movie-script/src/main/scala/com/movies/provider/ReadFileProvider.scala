package com.movies.provider

import scala.io.Source
import com.movies.model.Movie
import com.movies.utils.MovieUtils
import org.slf4j.LoggerFactory
import com.typesafe.scalalogging.Logger

trait FileProvider{
  def read(file: String, indexer: String => Unit)
}

case object ReadFileProvider extends FileProvider {
  val logger = Logger(LoggerFactory.getLogger("ReadFileProvider"))

  var source = Source

  def read(file: String, indexer: String => Unit): Unit = {
    logger.info("Starting reading file: " + file)
    source.fromFile(file, "ISO-8859-1")
      .getLines
      .foreach { line =>indexer(line)}
  }
}
