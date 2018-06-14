package au.edu.qut.infosci.ca.service


/*
import java.io.{IOException, FileNotFoundException}
import java.nio.charset.MalformedInputException

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.{Component, Service}

import scala.io.Source
import scala.xml.Properties

/**
 * Created by andrew on 5/01/15.
 */
@Component
class Scraper {

  def logger = LoggerFactory.getLogger(this.getClass)

  def getUrlAsString(url:String):String = {

    var captureLine = false
    var result:String = ""

    try {
      val sb = new StringBuilder
      val page = Source.fromURL(url,"ISO-8859-1")//("utf-8")
      for (line:String <- page.getLines()) {
        if (line.contains("<!--sino index-->")) captureLine = true //start capturing lines
        if (captureLine) sb.append(line).append(Properties.lineSeparator)
        if (line.contains("<!--sino noindex-->")) captureLine = false //stop capturing lines
      }
      result = sb.toString()
    } catch {
      case ex: FileNotFoundException => {
        result = "ERROR: Page does not exist"
        logger.error(result)
      }
      case ex: MalformedInputException => {
        result = "ERROR: Malformed Input Exception for URL: "+url
        logger.error(result)
      }
    }
    result
  }


}
*/