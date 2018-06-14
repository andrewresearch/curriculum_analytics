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
//import au.edu.qut.ltu.data.DocTopic
//import cc.factorie.app.strings.{Stopwords, alphaSegmenter}
//import cc.factorie.directed._
//import cc.factorie.variable._
//
//import scala.collection.mutable.ListBuffer
//import scala.util.{Random, Sorting}
//
//import scala.collection.JavaConverters._
//
///**
// * Store all created LDA data
// * Created by andrew on 15/07/2014.
// */
//class LdaAnalysis(nt:Int) {
//
//  implicit val model:MutableDirectedModel = DirectedModel()
//  implicit val random = new Random(0)
//
//  var numTopics:Int = nt
//  var documents:ListBuffer[LdaDocument] = ListBuffer()
//  var texts:ListBuffer[String] = ListBuffer()
//  val alpha = MassesVariable.dense(nt, 0.1) //Parameter for Dirichlet prior on per-doc distribution
//  object WordSeqDomain extends CategoricalSeqDomain[String]
//  def domain = WordSeqDomain
//  val WordDomain = WordSeqDomain.elementDomain
//  val beta = MassesVariable.growableUniform(WordDomain, 0.1) //Parameter for Dirichlet prior on per-topic distribution
//  var phis = Mixture(this.nt)(ProportionsVariable.growableDense(this.WordDomain) ~ Dirichlet(this.beta)) //Word distribution per topic
//
//  def numDocuments = documents.size
//  def docIds = documents.map(_.id)
//  def numTokens = documents.foldLeft(0)(_ + _.size)
//  def topicNumber(topic:ProportionsVariable) = phis.indexOf(topic)+1
//
//
//  def topicTermDistributions = phis.map(_.value.toSeq).toSeq
//  def terms = WordDomain.categories
//  def termTopicDistributions = (terms zip topicTermDistributions).toMap
//
//  def documentWordTopics = documents.map(_.zs.discreteValues.map(_.intValue)).toSeq
//
//  def documentTopicDistributions = {
//    var dtd:ListBuffer[DocTopic] = ListBuffer()
//    documents.foreach { doc =>
//    dtd += new DocTopic(doc.id,doc.assessType,doc.year,doc.theta.value.toSeq.asJava)
//    }
//    dtd.toSeq
//  }
//
//  def sortedTopics = {
//    var topicWords: Map[Int,Seq[(String,Double)]] = Map()
//    for((topic, i) <- topicTermDistributions.zipWithIndex) {
//      val sortedTopics = Sorting.stableSort(terms.zip(topic))(manifest[(String, Double)], Ordering.by(_._2))
//      topicWords += i -> sortedTopics.reverse
//    }
//    topicWords
//  }
//
//  def dbSortedTopics(num:Int = 100) = {
//    var javaSortedTopics: java.util.HashMap[String,java.util.List[String]] = new java.util.HashMap()
//
//    sortedTopics.foreach { kv =>
//      javaSortedTopics.put(kv._1.toString, kv._2.map( sd => sd._1+"|"+sd._2.toString).toSeq.take(num).asJava)
//    }
//    javaSortedTopics
//  }
//
//
//  def addDocument(id:String,assessType:String,year:String,text:String) = {
//    val tokens = alphaSegmenter(text).map(_.toLowerCase).filter(!Stopwords.contains(_)).toSeq
//    if (tokens.size > 0) {
//      val theta = ProportionsVariable.dense(numTopics) ~ Dirichlet(alpha) //Topic distribution for this doc
//      val zs = new Zs(tokens.length, numTopics) :~ PlatedDiscrete(theta) //Topics per word for this doc
//      var doc = new LdaDocument(text, theta, tokens, WordSeqDomain) ~ PlatedCategoricalMixture(phis, zs)
//      doc.id = id
//      doc.assessType = assessType
//      doc.year = year
//      documents += doc
//      texts += doc.file
//    }
//  }
//
//  def processCollection(iterations: Int = 500, currentIt: Int => Unit) = {
//    val collapse = new ListBuffer[Var]
//    collapse += phis
//    collapse ++= documents.map(_.theta)
//    val sampler = new CollapsedGibbsSampler(collapse, model)
//    (1 to iterations).foreach { i =>
//      currentIt(i)
//      documents.foreach( doc => sampler.process(doc.zs))
//    }
//  }
//
//  class LdaDocument(val file:String, val theta:ProportionsVar, strings:Seq[String], wordSeqDomain:CategoricalSeqDomain[String])(implicit model:DirectedModel) extends CategoricalSeqVariable(strings) {
//
//    def domain = wordSeqDomain
//    def zs = model.parentFactor(this).asInstanceOf[PlatedCategoricalMixture.Factor]._3
//    var id:String = ""
//    var assessType:String = ""
//    var year:String=""
//  }
//
//    class Zs(len:Int,numTopics:Int) extends DiscreteSeqVariable(len) {
//      object ZDomain extends DiscreteDomain(numTopics)
//      object ZSeqDomain extends DiscreteSeqDomain { def elementDomain = ZDomain }
//      def domain = ZSeqDomain
//    }
//}
