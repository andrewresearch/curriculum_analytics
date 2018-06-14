//package controllers
//
//import org.slf4j.LoggerFactory
//
//import scala.collection.JavaConverters._
//import scala.collection.mutable.ListBuffer
//
///**
// * Created by andrew on 27/01/15.
// */
//@RestController
//class LdaController {
//
//  @Autowired val dm: DataService = null
//  @Autowired val topicAnalyser: TopicAnalyser = null
//  @Autowired val mongo: MongoTemplate = null
//  @Autowired val typeTopicRepo: TypeTopicRepo = null
//  @Autowired val topicResultsRepo: TopicResultsRepo = null
//
//
//  def logger = LoggerFactory.getLogger(this.getClass)
//
//  @RequestMapping(value=Array("/assessment/namesTitles"),method=Array(RequestMethod.GET))
//  def namesTitles = {
//    logger.info("Names Titles")
//    val nt = dm.getAllAssessmentNamesAndTitles
//    logger.debug("Names and Titles: "+nt)
//    nt
//  }
//
//  @RequestMapping(value=Array("/assessment/descriptors"),method=Array(RequestMethod.GET))
//  def descriptors = {
//    logger.info("LDA Controller")
//    val ad = dm.getAllAssessmentDescriptors
//    logger.debug("Assessment Descriptors: "+ad)
//    ad
//  }
//
//
//
//  @RequestMapping(value=Array("/assessment/lda/{topics}/{iterations}"),method=Array(RequestMethod.GET))
//  def lda(@PathVariable topics: Int, @PathVariable iterations: Int) = {
//    logger.info("Processing descriptors for LDA")
//    val assessments = dm.getAllAssessments //.take(50)
//    var ldaList: ListBuffer[(String, String, String,String)] = ListBuffer()
//    assessments.foreach(assess => ldaList += ((assess.id, assess.assessmentType,assess.academicYear, assess.name + " | " + assess.longTitle + " | " + assess.description)))
//    val topicResults = topicAnalyser.processLDA(ldaList.toList, topics, iterations)
//
//    logger.debug("Num documents: " + topicAnalyser.numDocuments)
//    logger.info("Num terms: "+topicAnalyser.numTerms)
//    dm.saveTopicResults(topicResults)
//    topicResults
//  }
//
//  @RequestMapping(value=Array("/assessment/topics"),method=Array(RequestMethod.GET))
//  def topics = {
//    dm.getTopicResults.sortedTopics
//  }
//
//  @RequestMapping(value=Array("/assessment/create/typeTopics"),method=Array(RequestMethod.GET))
//  def createTypeTopics = {
//
//    typeTopicRepo.deleteAll()
//    val topicResults = topicResultsRepo.findAll().asScala.head
//    val docTopics = topicResults.docTopics.asScala
//
//    //Create TypeTopics for each year
//    Array("2013","2014","2015").foreach { year =>
//      var typeTopics: Map[String,List[Double]] = Map()
//      var typeCounts: Map[String,Int] = Map()
//      logger.info("adding topics for each type...")
//      docTopics.foreach { dt =>
//        if(dt.docYear.contains(year)) {
//          val emptyList: List[Double] = List.fill(dt.topicDistribution.size)(0.0)
//          val count: Int = typeCounts.getOrElse(dt.docType, 0)
//          val zippedList = dt.topicDistribution.asScala zip typeTopics.getOrElse(dt.docType, emptyList)
//          //logger.debug("zipped list:"+ zippedList)
//          val summedList = zippedList.toList.map(t => t._1 + t._2)
//          //logger.debug("summed list: "+summedList)
//          typeTopics += dt.docType -> summedList
//          typeCounts += dt.docType -> (count + 1)
//        }
//      }
//      logger.info("creating topic mixtures for each type...")
//      var finalTypeTopics:Map[String,List[Double]] = Map()
//      typeTopics.foreach { typeTopic =>
//        val topicList = typeTopic._2
//        val count = typeCounts.getOrElse(typeTopic._1,0)
//        logger.info("type " + typeTopic._1 + " has a count of " + count)
//        val finalTopic = topicList.map(_ / count)
//        //logger.debug("finalTopic should sum to 1: "+(finalTopic.foldLeft(0.0)(_ + _)))
//        finalTypeTopics += typeTopic._1 -> finalTopic
//        typeTopicRepo.save(new TypeTopic(null,typeTopic._1,year,count,finalTopic.asJava))
//      }
//    }
//    "Done"
//  }
//
//
//
//
//
//}
