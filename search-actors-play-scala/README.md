# search-actors-play-scala Project

This projects provides a WEB INTERFACE search of movies by actor names. It does a phrase search using
the name of the actor and return list of movies, with information of the actors that acted
in the movie and the year of the movie.

##Dependencies

Before using the API, it's necessary to index information about movies and actors. So, you need 
to go to the project [**indexer-movie-script**](https://bitbucket.org/csponchiado/holidaycheck/src/HEAD/indexer-movie-script/?at=master) and follow the **README**.

## Technologies

To develop this project, it was used the following technologies:

1. [Scala](http://www.scala-lang.org/) (Language)
2. [Elastic4s](https://github.com/sksamuel/elastic4s) (DSL for applications in Scala that use Elasticsearch)
2. [Play](https://www.playframework.com/) (High Velocity Web Framework For Java and Scala)
2. [ScalaStyle](http://www.scalastyle.org/) (check the style of the code)

## Procediments

Follow these steps to get started:

1. Git-clone this repository.

        $ git clone git@bitbucket.org:csponchiado/holidaycheck.git

2. Change directory into your clone:

        $ cd search-actors-play-scala
        
3. Launch SBT:

        $ sbt

4. Start the application:

        > run
        
5. Access the UI

        http://localhost:9000/
        
6. Do a search

        http://localhost:9000/search?query=john
        
7. Check details of one Movie

        http://localhost:9000/movie/6c4699de37526542863a67e187f27f9e3f4aa799
