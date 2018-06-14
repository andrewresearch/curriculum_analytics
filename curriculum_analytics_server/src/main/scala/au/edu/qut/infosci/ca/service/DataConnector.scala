/*
 * Copyright 2015 Andrew Gibson
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package au.edu.qut.infosci.ca.service


/*
import au.edu.qut.ltu.data.{WebDoc, WebDocRepo}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.{PageRequest, Pageable}
import org.springframework.stereotype.Component
//import scala.collection.JavaConversions
import scala.collection.JavaConverters

/**
 * Created by andrew on 5/01/15.
 */
@Component
class DataConnector {

  @Autowired val webDocRepo: WebDocRepo = null
  @Autowired val cleaner: Cleaner = null
  def logger = LoggerFactory.getLogger(this.getClass)

  def saveToDatabase(url: String, text: String) = {
    webDocRepo.save(new WebDoc(null,url,text))
  }

  def getYear(year: String) = {
    //var yearString:String = ""
    val docs:java.lang.Iterable[WebDoc] = webDocRepo.findAll(new PageRequest(1,10)) //findByUrlRegex(year)
    val first = cleaner.removeTags(docs.iterator().next().content)
    logger.info("Found "+first) //.toList.length+" documents for year "+year)
    /*for(doc <-docs) {
      yearString += cleaner.removeTags(doc.content)
    } */
    first
  }

}
*/