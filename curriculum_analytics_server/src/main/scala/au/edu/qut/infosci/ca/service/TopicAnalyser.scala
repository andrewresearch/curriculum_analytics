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
//import au.edu.qut.ltu.data.TopicResults
//import org.slf4j.LoggerFactory
//import org.springframework.stereotype.Component
//
//import scala.collection.JavaConverters._
//
///**
// * Created by andrew on 27/01/15.
// */
//
////@Component
//class TopicAnalyser {
//
//  var lda:LdaAnalysis = _
//
//  def logger = LoggerFactory.getLogger(this.getClass)
//
//  def iterationReport(i:Int) = logger.debug("Iteration: "+i)
//
//  def processLDA(records: List[(String,String,String,String)],numTopics:Int,iterations:Int = 1000) = {
//    logger.info("numTopics: " + numTopics)
//    lda = new LdaAnalysis(numTopics)
//    records.foreach { record => lda.addDocument(record._1,record._2,record._3,record._4)}
//
//    logger.info("Read " + lda.numDocuments + " documents with " + lda.numTokens + " tokens")
//
//    lda.processCollection(iterations,iterationReport)
//    new TopicResults(null, lda.terms.asJava, lda.topicTermDistributions.map(_.asJava).asJava, lda.documentTopicDistributions.asJava, lda.dbSortedTopics(100),null)
//  }
//
//  def numDocuments = lda.numDocuments
//  def numTerms = lda.numTokens
//
//}
