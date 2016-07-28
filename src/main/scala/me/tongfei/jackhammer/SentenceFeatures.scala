package me.tongfei.jackhammer

import edu.jhu.hlt.{concrete => jhu}
import me.tongfei.granite._
import me.tongfei.jackhammer.util._
import me.tongfei.probe._

import scala.collection.mutable._

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

  val NamedEntityTypes = Featurizer.binary("NEType") { s: jhu.Sentence =>
    for {
      n <- s.tokenization.nerTagging.toList
      tok <- n.taggedTokenList if tok.tag != "O"
    } yield tok.tag
  }

  val NamedEntities = FeaturizerFamily.binary("NE") { s: jhu.Sentence =>
    val tkz = s.tokenization
    val ems = ArrayBuffer[(String, String)]()
    for (ner <- tkz.nerTagging) {
      val tags = Array.fill(tkz.tokenList.size)("")
      for (tt <- ner.taggedTokenList)
        tags(tt.tokenIndex) = tt.tag

      var prevTag = ""
      var prevIdx = 0
      for (currIdx <- 0 until tkz.tokenList.size) {
        val currTag = tags(currIdx)
        if (currTag != prevTag) {
          if (prevTag != "" && prevTag != "O")
            ems += prevTag -> (prevIdx until currIdx).map(i => tkz.tokenList(i).text).mkString(" ")
          prevTag = currTag
          prevIdx = currIdx
        }
      }
    }
    ems
  }

}
