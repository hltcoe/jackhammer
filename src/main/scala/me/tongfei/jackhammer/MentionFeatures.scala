package me.tongfei.jackhammer

import edu.jhu.hlt.{concrete => jhu}
import me.tongfei.granite._
import me.tongfei.probe._

/**
 * @author Tongfei Chen
 */
object MentionFeatures {

  type Mention = (jhu.EntityMention, jhu.Communication)

  val Text = Featurizer.singleCategorical("t") { s: Mention =>
    s._1.text
  }

  val Words = Featurizer.count("w") { s: Mention =>
    val comm = s._2
    val tkzs = comm.resolveTokenization(s._1.tokens.tokenizationId)
    s._1.tokens.tokenIndexList.map(i => tkzs.tokenList(i).text)
  }

  val Type = Featurizer.singleCategorical("type") { s: Mention =>
    val tkzs = s._2.resolveTokenization(s._1.tokens.tokenizationId)
    tkzs.tokenTaggingList.find(_.taggingType == "NER").get.taggedTokenList(s._1.tokens.tokenIndexList.head).tag
  }

  val Trigrams = Featurizer.binary("3g") { s: String => s.sliding(3).toSeq }

  val WordTrigrams = Words >>> Trigrams changeName "3g"

  def ContextWords(lr: LanguageResources, k: Int) = CommunicationFeatures.Words(lr, k) contramap { s: Mention => s._2 }

}
