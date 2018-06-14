///*
// * Copyright 2015 Andrew Gibson
// *
// *    Licensed under the Apache License, Version 2.0 (the "License");
// *    you may not use this file except in compliance with the License.
// *    You may obtain a copy of the License at
// *
// *        http://www.apache.org/licenses/LICENSE-2.0
// *
// *    Unless required by applicable law or agreed to in writing, software
// *    distributed under the License is distributed on an "AS IS" BASIS,
// *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// *    See the License for the specific language governing permissions and
// *    limitations under the License.
// */
//
//package au.edu.qut.infosci.ca.service
//
//import au.edu.qut.ltu.data._
//import com.fasterxml.jackson.annotation.JsonCreator
//import com.mongodb.{DBObject, BasicDBObject}
//import org.slf4j.LoggerFactory
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.data.domain.PageRequest
//import org.springframework.data.domain.Sort.Direction
//import org.springframework.data.mongodb.core.MongoTemplate
//import org.springframework.stereotype.Component
//import org.springframework.web.bind.annotation.{RequestMethod, RequestMapping}
//
//import scala.collection.JavaConverters._
//import scala.collection.mutable
//import scala.collection.mutable.ArrayBuffer
//
///**
// * Created by andrew on 27/01/15.
// */
//@Component
//class DataService {
//
//  @Autowired val assessmentRepo: AssessmentRepo = null
//  @Autowired val topicResultsRepo: TopicResultsRepo = null
//  @Autowired val typeTopicRepo: TypeTopicRepo = null
//  @Autowired val mongo: MongoTemplate = null
//
//  def logger = LoggerFactory.getLogger(this.getClass)
//
//  def getAssessmentTypes = {
//    /*
//    // Construct our _id to group on
//    var fields = new BasicDBObject( "Type","$type")
//    fields.put( "Academic Year", "$academicYear" )
//    // Contruct group element
//    var groupFields = new BasicDBObject( "_id", fields )
//    groupFields.put( "count", new BasicDBObject( "$sum", 1 ) )
//    val group = new BasicDBObject( "$group", groupFields )
//    // Run aggregation
//    val results:Iterable[DBObject] = mongo.getCollection("assessment").aggregate( group ).results().asScala
//
//    //var typeMap = new HashMap[String, HashMap[String,Long]]
//    for(result <- results) {
//      logger.debug("DBObject: "+result.toMap.toString)
//    } */
//    val assessments = assessmentRepo.findAll().asScala
//    var results = mutable.HashMap[String,mutable.HashMap[String,Int]]()
//    assessments.foreach { assessment =>
//      val current = results.getOrElse(assessment.assessmentType,mutable.HashMap[String,Int]())
//      current.put(assessment.academicYear,current.getOrElse(assessment.academicYear,0)+1)
//      results.put(assessment.assessmentType,current)
//    }
//    //logger.debug("results: "+results)
//    var resultAsArray = ArrayBuffer[AssessType]()
//    results.foreach { result =>
//      var year1, year2, year3 = 0
//      result._2.foreach { yearCount =>
//        if(yearCount._1.contains("2013")) year1 = yearCount._2
//        if(yearCount._1.contains("2014")) year2 = yearCount._2
//        if(yearCount._1.contains("2015")) year3 = yearCount._2
//
//        //resultAsArray += AssessType(result._1, yearCount._1, yearCount._2)
//      }
//      resultAsArray += AssessType(result._1,Array(year1,year2,year3))
//    }
//    resultAsArray.toList.sortBy(_.y(1)).reverse
//  }
//
//  case class AssessType (t: String, y: Array[Int])
//
//  def getAssessmentTypeTopics(year:String) = {
//    val topics = topicResultsRepo.findAll().asScala.head.sortedTopics.asScala.toList
//
//    val topicList = topics.map { topic =>
//      "Topic-"+"%02d".format(topic._1.toInt)+" >> "+topic._2.asScala.take(10).map(_.replaceAll("\\|.*","")).mkString(" ")
//    }
//    val tts = typeTopicRepo.findAll(new PageRequest(0, 100, Direction.ASC, "count"))
//    val ttMap = tts.asScala.map { tt =>
//      var docType = "NoType"
//      if(tt.year.contains(year) || year.contains("ALL")) {
//       docType = tt.docType
//      }
//      Map(("type" -> docType), ("data" -> (tt.topic.asScala.zipWithIndex.map(_.swap)).toMap))
//    }
//
//    var d3data = ArrayBuffer[Cell]()
//    ttMap.foreach { cell =>
//      val dt = cell.apply("type").asInstanceOf[String]
//      if(!dt.contains("NoType")) {
//        val cd = cell.apply("data").asInstanceOf[Map[Int, Double]]
//        cd.foreach { case (k, v) =>
//
//          d3data += Cell(dt, "Topic-" + k.toString, v)
//
//        }
//      }
//    }
//    (d3data.toList.sortBy(_.v).reverse, topicList.sorted)
//  }
//
//  case class Cell (d: String, t: String, v: Double)
//
//
//  def getAllAssessmentNamesAndTitles:List[String] = {
//    val assessments: java.lang.Iterable[Assessment] = assessmentRepo.findAll
//    assessments.asScala.map( assess => assess.name+" | "+assess.longTitle).toList
//  }
//
//  def getAllAssessmentDescriptors:List[String] = {
//    val assessments: java.lang.Iterable[Assessment] = assessmentRepo.findAll
//    assessments.asScala.map( assess => assess.description).toList
//  }
//
//  def getAllAssessments:List[Assessment] = {
//    assessmentRepo.findAll().asScala.toList
//  }
//
//  def saveTopicResults(topicResults:TopicResults) = {
//    topicResultsRepo.deleteAll()
//    topicResultsRepo.save(topicResults)
//  }
//
//  def getTopicResults:TopicResults = {
//    topicResultsRepo.findAll().asScala.toList.head
//  }
//
//}
