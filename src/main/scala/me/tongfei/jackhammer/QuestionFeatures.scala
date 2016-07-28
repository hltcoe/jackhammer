package me.tongfei.jackhammer

import edu.jhu.hlt.{concrete => jhu}
import me.tongfei.granite._
import me.tongfei.probe._
import me.tongfei.jackhammer.util._

/**
 * @author Tongfei Chen
 */
object QuestionFeatures {

  val rootDependencyLabel = "root"
  val qwords = Set("who", "where", "why", "when", "how", "what", "which", "whose", "whom")

  val QuestionWordWithLexicalAnswerType = Featurizer.binary("qword") { s: jhu.Sentence =>

    val pos = s.tokenization.posTagging.get
    val g = DependencyGraph.ofConcreteSentence(s, 0)
    val root = g.outgoingIdsOf(-1).find(v => g(-1, v) == rootDependencyLabel).get
    val qwordIndex = g.breadthFirstIterator(root).find(v => qwords.contains(g(v).toLowerCase)).getOrElse(-1)
    if (qwordIndex == -1) Iterable()
    else {
      val qword = g(qwordIndex).toLowerCase

      qword match {
        case wh@"how" =>
          if (pos.taggedTokenList.find(tt => tt.tokenIndex == qwordIndex + 1).get.tag.startsWith("V"))
            Iterable(wh)
          else Iterable(wh + " " + g(qwordIndex + 1), wh) // concat with word after "how" if it is not a verb
        case wh@("what" | "which") =>
          val lat = LexicalAnswerType(s)
          if (lat != "") Iterable(wh + " " + lat, wh)
          else Iterable(wh) // deal with questions like "What kind"
        case wh => Iterable(wh)
      }
    }

  }
}
