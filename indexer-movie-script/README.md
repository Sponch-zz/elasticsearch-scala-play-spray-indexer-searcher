# indexer-movie-script Project

**indexer-movie-script**  is a script that reads the file **actors.list** from **Internet Movie Database (IMDb)**, 
parses informations about movies and indexes this information in Elasticsearch. Each time that this script run, 
it clean the index of the Elasticsearch and start indexing again.

In this file, one actor can participate in one or more movies and one movie has one or more actors. 

One example of the document indexed:

```json
{
	_index: "movies",
	_type: "movie",
	_id: "b6ac9179d2541725dfe1e14bacd43fd8f46554ca",
	_score: 2.947674,
	_source: {
		name: "Overlord",
		actors: [
			"Babij, Karl",
			"Ball, Nate"
		],
		year: 2007
	}
}
```
## Technologies

To develop this project, it was used the following technologies:

1. [Scala](http://www.scala-lang.org/) (Language)
2. [Elastic4s](https://github.com/sksamuel/elastic4s) (DSL for applications in Scala that use Elasticsearch)
2. [ScalaStyle](http://www.scalastyle.org/) (check the style of the code)

***
## Requirements

To run the script, it's necessary to have installed in your machine this softwares:
 
1. [Elasticsearch](https://www.elastic.co/downloads/elasticsearch)
2. [sbt](http://www.scala-sbt.org/download.html)

## Procediments

Follow these steps to get started:

1. Git-clone this repository.

        $ git clone git@github.com:Sponch/elasticsearch-scala-play-spray-indexer-searcher.git
		
2. Start the Elasticsearch
	
		cd /path-so-elasticsearch/
		./bin/elasticsearch --cluster.name movies
			
2. Download the **actors.list**
	
		cd /tmp	
		wget ftp://ftp.fu-berlin.de/pub/misc/movies/database/actors.list.gz
		gunzip actors.list.gz
		
4. Change directory into your clone:

        $ cd indexer-movie-script
		
5. Run the script

		sbt "run --elasticendpoint elasticsearch://127.0.0.1:9300 --elasticcluster movies --path /tmp/actors.list"
	
6. Check the index in Elasticsearch:

    	http://localhost:9200/movies/_search?q=*
	
	


