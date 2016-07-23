package me.tongfei.jackhammer

import edu.jhu.hlt.{concrete => jhu}
import me.tongfei.granite._
import me.tongfei.jackhammer.util._
import me.tongfei.probe._

/**
 * @author Tongfei Chen
 */
object SentenceFeatures {

  val Words = Featurizer.count("w") { s: jhu.Sentence =>
    s.tokenization.tokenList.map(t => t.text.toLowerCase).filter(t => t.head.isLetter)
  }

  val BagOfWords = Words uniformWeight

  val StemmedBagOfWords = Words map PorterStemmer uniformWeight

  def TfIdfWeightedWords(lr: LanguageResources) = Words assignWeights { w => lr.idf.getOrElse(w, 1.5) }

  def StemmedTfIdfWeightedWords(lr: LanguageResources) = Words map PorterStemmer assignWeights { w => lr.idf.getOrElse(w, 1.5) }

  val NamedEntityTypes = Featurizer.binary("netype") { s: jhu.Sentence =>
    for {
      n <- s.tokenization.nerTagging.toList
      tok <- n.taggedTokenList if tok.tag != "O"
    } yield tok.tag
  }

}
