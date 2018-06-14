package au.edu.qut.infosci.ca.service

import au.edu.qut.infosci.ca.data.{Taxonomy, TaxonomyCounts}
import org.slf4j.LoggerFactory

import scala.collection.mutable.ArrayBuffer

/**
 * Created by andrew on 12/01/15.
 */
//@Component
class BloomsVerbCounter {

  def logger = LoggerFactory.getLogger(this.getClass)

  def count(taxonomy:Taxonomy,text:String):TaxonomyCounts = {
    if (taxonomy==null) logger.error("TAXONOMY IS NULL")
    val wordmap = text.split("[^a-zA-Z0-9']+").groupBy(identity).mapValues(_.length)
    //logger.debug("wordmap: "+wordmap)
    var results = ArrayBuffer[(String,Array[(String,Int)])]()
    taxonomy.taxonomyArray.foreach{taxonomyElement =>
      val verbLabel = taxonomyElement._1
      val verbArray = taxonomyElement._2
      logger.debug("verbLabel: "+verbLabel)
      //logger.debug("verbArray: "+verbArray.toString)
      //results.+=((verbLabel,verbArray.flatMap(k => wordmap.get(k.toString).map((k,_)))))
      val verbs = verbArray.flatMap(verb => wordmap.get(verb.toString).map((verb,_)))
      results += ((verbLabel,verbs))
      //wordCounts.verbLists.+()
      //logger.debug("results: "+results.toString)
    }
    //new TaxonomyVerbCounts(null,results.toArray)
    new TaxonomyCounts(null,results.toArray)
  }
}
