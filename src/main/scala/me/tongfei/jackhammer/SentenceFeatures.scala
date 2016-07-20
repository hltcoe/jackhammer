package me.tongfei.jackhammer

import edu.jhu.hlt.{concrete => jhu}
import me.tongfei.granite._
import me.tongfei.jackhammer.util._
import me.tongfei.probe._

/**
 * @author Tongfei Chen
 */
object SentenceFeatures {

  val BagOfWords = Featurizer.count("w") { s: jhu.Sentence =>
    s.tokenization.tokenList.map(t => t.text.toLowerCase)
  }

  val StemmedBagOfWords = BagOfWords map PorterStemmer.apply


}
