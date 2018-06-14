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
///**
// * Created by andrew on 10/01/15.
// */
//@RestController
//@RequestMapping(value=Array("/assessment"))
//class AssessmentController {
//
//  def logger = LoggerFactory.getLogger(this.getClass)
//
//  @Autowired val data:DataService = null
//
//  @RequestMapping(value=Array("/"),method=Array(RequestMethod.GET))
//  def assessHome = {
//    logger.info("Assessment Controller")
//    "Assessment Home"
//  }
//
//  @RequestMapping(value=Array("/types"),method=Array(RequestMethod.GET))
//  def assessTypes = {
//    //Return an array for the types graph
//    data.getAssessmentTypes
//  }
//
//  @RequestMapping(value=Array("/typeTopics/{year}"),method=Array(RequestMethod.GET))
//  def assessTypeTopics(@PathVariable year:String) = {
//    data.getAssessmentTypeTopics(year)
//  }
//
//}
