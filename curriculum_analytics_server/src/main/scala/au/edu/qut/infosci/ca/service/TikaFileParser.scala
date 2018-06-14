//package au.edu.qut.infosci.ca.service
//
//import java.io.StringWriter
//
//import org.apache.tika.Tika
//import org.apache.tika.detect.Detector
//import org.apache.tika.metadata.{TikaMetadataKeys, TikaCoreProperties, Metadata}
//import org.apache.tika.parser.AutoDetectParser
//import org.apache.tika.sax.BodyContentHandler
//import org.slf4j.LoggerFactory
//import org.springframework.stereotype.{Component, Service}
//import org.springframework.web.multipart.MultipartFile
//
///**
// * Created by andrew on 12/01/15.
// */
////@Component
//class TikaFileParser {
//
//  def logger = LoggerFactory.getLogger(this.getClass)
//
//  def parse(file:MultipartFile): String = {
//    val parser = new AutoDetectParser()
//
//    //var bodyContentHandler = new BodyContentHandler(writer)
//    var metadata = new Metadata
//    metadata.set(TikaMetadataKeys.RESOURCE_NAME_KEY,file.getOriginalFilename)
//
//    if(!file.isEmpty) {
//      try {
//        var writer = new StringWriter()
//        var tika = new Tika()
//        var content = tika.parseToString(file.getInputStream)
//        logger.info("Content: "+content)
//        //parser.parse(file.getInputStream,new BodyContentHandler(writer),metadata)
//        //logger.info("Metadata is: "+metadata.toString)
//        //logger.info("Writer data is:")+writer.toString
//        //"Name: "+metadata.toString +"\n"+
//          //"File: "+"\n"+bodyContentHandler.toString
//          //writer.toString //Send back for now - to be saved to DB
//        content
//      } catch {
//        case e: Throwable => logger.error("An error occurred: "+e)
//          e.toString
//      }
//    } else {
//      logger.error("File is empty")
//      "Empty File"
//    }
//
//
//    //"Finished loading: "+metadata.toString+" - content is: "+writer.toString
//  }
//
//}
