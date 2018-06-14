package au.edu.qut.infosci.ca.data

/**
 * Created by andrew on 12/01/15.
 */
case class Taxonomy (
                      id: String,
                      name: String,
                      description: String,
                      taxonomyArray: Array[(String,Array[String])]
                      )
//taxonomyMap: HashMap[String,Array[String]]