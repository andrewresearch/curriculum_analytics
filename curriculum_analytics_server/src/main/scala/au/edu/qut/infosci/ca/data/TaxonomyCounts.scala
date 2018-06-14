package au.edu.qut.infosci.ca.data


/**
 * Created by andrew on 13/01/15.
 */
case class TaxonomyCounts (
  id: String,
  counts: Array[(String,Array[(String,Int)])]
                            )
