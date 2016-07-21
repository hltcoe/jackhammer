package me.tongfei.jackhammer

import me.tongfei.probe._
import me.tongfei.granite._
import edu.jhu.hlt.{concrete => jhu}

/**
 * @author Tongfei Chen
 */
object CommunicationFeatures {

  def Words(lr: LanguageResources, k: Int) = Featurizer.count("cw") { c: jhu.Communication =>
    (for {
      section <- c.sectionList
      sentence <- section.sentenceList
      token <- sentence.tokenization.tokenList
    } yield token.text)
      .map(_.toLowerCase)
      .filter(w => !(lr.stopwords contains w))
  }.assignWeights(w => lr.idf.getOrElse(w, 1.5)).topK(k).uniformWeight

}
