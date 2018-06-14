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
//package controllers
//
//import org.slf4j.LoggerFactory
//
//import scala.collection.mutable.ArrayBuffer
//
////import scala.collection.JavaConverters._
//
///**
// * Created by andrew on 7/01/15.
// */
//@RestController
//class TaxonomyVerbController {
//
//  def logger = LoggerFactory.getLogger(this.getClass)
//
//  @Autowired val fileParser: TikaFileParser = null
//  @Autowired var verbCounter: BloomsVerbCounter = null
//  @Autowired val taxonomyRepo: TaxonomyRepo = null
//  @Autowired val taxonomyVerbCountsRepo: TaxonomyVerbCountsRepo = null
//  @Autowired val taxonomyCountsRepo: TaxonomyCountsRepo = null
//
//  var documentText: String = "No document available"
//  var documentAnalysis: TaxonomyCounts = null
//
//  @RequestMapping(value = Array("/createTaxonomy"), method = Array(RequestMethod.GET))
//  // Some of these words provided by the site: http://www.nwlink.com/~donclark/hrd/bloom.html#revised
//  // and http://www.colorado.edu/sei/documents/Workshops/Handouts/Blooms_Taxonomy.pdf
//  def saveTaxonomy = {
//    val taxonomy = new Taxonomy(null,"Bloom's Taxonomy","",Array(
//      ("remembering", Array("remember","define", "describe", "identify", "know", "label", "list","match", "name", "outline", "recall", "recognise","reproduce", "select", "state","choose","describe","locate","memorize","memorise","omit","recite","recognize")),
//
//      ("understanding", Array("understand","comprehend", "convert", "defend", "distinguish", "estimate", "explain", "extend", "generalise", "infer", "interpret", "paraphrase", "predict", "rewrite", "summarise", "translate","classify","demonstrate","distinguish","express","extend","give example","illustrate","indicate","interrelate","judge","match","paraphrase","represent","restate","select","show","summarize","tell")),
//
//      ("applying", Array("apply", "change", "compute", "construct", "demonstrate", "discover", "manipulate", "modify", "operate", "predict", "prepare", "produce", "relate", "show", "solve", "use","choose","dramatize","explain","generalize","generalise","judge","organize","organise","paint","prepare","produce","select","sketch","use")),
//
//      ("analysing", Array("analyse", "compare", "contrast", "diagram", "deconstruct", "differentiate", "discriminate", "distinguish", "identify", "illustrate", "infer", "outline", "relate", "select", "separate","analyze","categorize","categorise","classify","point out","subdivide","survey")),
//
//      ("evaluating", Array("evaluate","appraise", "compare", "conclude", "contrast", "criticise", "critique", "defend", "describe", "discriminate", "interpret", "justify", "relate", "summarise", "support","judge","criticize")),
//
//      ("creating", Array("create","invent","compose","predict","plan","construct","design","imagine","propose","devise","formulate","combine","hypothesize","originate","forecast","categorise", "compile", "devise", "generate", "modify", "organise", "rearrange", "reconstruct", "relate", "reorganize", "revise", "rewrite", "tell", "write","develop","formulate","hypothesise","invent","make","make up","produce","role play"))
//    ))
//    taxonomyRepo.save(taxonomy)
//    taxonomy
//  }
//
//  //More verbs to add
//  /*Remember:
//    Understand:
//    Apply:
//    Analyse:
//    Evaluate: appraise
//    Create:
//  */
//
//  @RequestMapping(value = Array("/upload"), method = Array(RequestMethod.POST))
//  def uploadFile(@RequestParam("file") file: MultipartFile) = {
//    //@RequestParam("formData") formData:ArrayBuffer[String],
//    //val taxonomy = new Taxonomy("",Map("short" -> Array("a","is","the"),"names" -> Array("Telstra","ANDREW")))
//    val fileText = fileParser.parse(file)
//    documentText = fileText
//    val result = verbCounter.count(taxonomyRepo.findOneByName("Bloom's Taxonomy"),fileText)
//    //taxonomyVerbCountsRepo.save(result)
//    //taxonomyCountsRepo.save(result)
//    documentAnalysis = result
//    logger.info("RESULT: "+result)
//    //result
//    "Done"
//  }
//
//  @RequestMapping(value = Array("/fileText"), method = Array(RequestMethod.GET))
//  def getFileText = {
//    documentText
//  }
//
//  @RequestMapping(value = Array("/fileAnalysis"), method = Array(RequestMethod.POST),consumes = Array("application/json"))
//  def getFileAnalysis(@RequestBody name:String) = {
//    logger.debug("Requesting Taxonomy: "+name)
//    logger.info("ANALYSIS: "+documentAnalysis)
//    documentAnalysis.counts.map { level =>
//      val tl = new TaxonomyLevel(level._1,level._2.map(wc => new WordCount(wc._1,wc._2)))
//      //logger.debug("tl: "+tl)
//      tl
//    }
//  }
//
//  case class TaxonomyLevel(level:String,words:Array[WordCount])
//
//
//  @RequestMapping(value = Array("/bloomsTotals"), method = Array(RequestMethod.GET))
//  def getBloomsTotals = {
//
//    //var jsonMap:Map[String,Int] = Map()
//    var totals = ArrayBuffer[WordCount]()
//    for((key: String, value: Array[(String,Int)]) <- documentAnalysis.counts) {
//      var total = 0
//      for((verb: String, count: Int) <- value) {
//        total += count
//      }
//      totals += (new WordCount(key,total))
//    }
//    //val json = JSONObject (jsonMap)//JSONArray (1 :: 2 :: 3 :: Nil)))
//    logger.info("totals: "+totals)
//    totals
//  }
//
//  case class WordCount (word:String, count:Int)
//
//}