# rest-api-search-movies Project

This projects provides a REST API to search movies by actor names. It does a phrase search using
the name of the actor and return list of movies, with information of the actors that acted
in the movie and the year of the movie.

##Dependencies

Before using the API, it's necessary to index information about movies and actors. So, you need 
to go to the project [**indexer-movie-script**](https://bitbucket.org/csponchiado/holidaycheck/src/HEAD/indexer-movie-script/?at=master) and follow the **README**.

## Technologies

To develop this project, it was used the following technologies:

1. [Scala](http://www.scala-lang.org/) (Language)
2. [Elastic4s](https://github.com/sksamuel/elastic4s) (DSL for applications in Scala that use Elasticsearch)
2. [Spray](http://spray.io/) (REST/HTTP-based integration layers)
2. [ScalaStyle](http://www.scalastyle.org/) (check the style of the code)

## Procediments

Follow these steps to get started:

1. Git-clone this repository.

        $ git clone git@bitbucket.org:csponchiado/holidaycheck.git

2. Change directory into your clone:

        $ cd rest-api-search-movies

3. Update the application.conf to seeting the configuration of your elasticsearch

		$ vim src/main/resource/application.conf

		 elasticsearch{
 		   	endpoint = "elasticsearch://127.0.0.1:9300",
           	cluster = "movies"
           	index = "movies"
        	type = "movie"
         }

4. Launch SBT:

        $ sbt

5. Compile everything and run all tests:

        > test

6. Start the application:

        > re-start

7. Browse to [http://localhost:8080/search/?query=<actor>&rows=<rows>&start=<start>](http://localhost:8080/search/?query=Astorga,%20Pedro&rows=3&start=0)
	
		http://localhost:8080/search/?query=<actor>&rows=<rows>&start=<start>
		<query> = actor name
		<rows>  = number of results per request
		<start> = start index of results, used for pagination

8. Exemplo of output

```javascript
          [{
            "id": "92656ddc20d18752d3fd1953fa68c9a961843b81",
            "name": "Amor a prueba",
            "year": 2014,
            "actors": ["Acuña, Toto", "Américo", "Arce, Lester", "Artiaga, Thomas", "Astorga, Pedro"]
          }, {
            "id": "8d9214e1edd4882167b52c8653108307f59a18e7",
            "name": "La Odisea",
            "year": 2013,
            "actors": ["Andrade, Pangal", "Astorga, Pedro"]
          }, {
            "id": "f1f09051fce2665be412eadecfa6ca23d1e1a675",
            "name": "Salta si puedes",
            "year": 2013,
            "actors": ["Andrade, Pangal", "Araneda, Rafael", "Astorga, Alvin", "Astorga, Pedro"]
          }]  
```

7. Stop the application:

        > re-stop

