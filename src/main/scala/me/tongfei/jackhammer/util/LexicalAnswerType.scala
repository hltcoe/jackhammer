package me.tongfei.jackhammer.util

import edu.stanford.nlp.parser.lexparser._
import edu.stanford.nlp.trees._
import me.tongfei.granite._
import me.tongfei.granite.io._

import scala.collection.JavaConversions._

/**
  * @author Tongfei Chen
  */
object LexicalAnswerType {

  val parser = LexicalizedParser.loadModel()
  val headFinder = new CollinsHeadFinder

  def apply(q: Sentence): String = {

    val tokens = q.tokenization.tokenList.map(_.text).mkString(" ")
    val t = parser.parse(tokens)

    val qword = q.tokenization.tokenList(0).text.toLowerCase

    if (qword != "what" && qword != "which") return ""

    val firstWhNp = t.preOrderNodeList().find(x => x.label.value == "WHNP")
    val firstNp = t.preOrderNodeList().find(x => x.label.value == "NP")

    val unstemmed =
      if (firstWhNp.isDefined && firstWhNp.get.children().length > 1) firstWhNp.get.children().last.headTerminal(headFinder).label.value
      else if (firstNp.isDefined) firstNp.get.headTerminal(headFinder).label.value
      else ""

    PorterStemmer(unstemmed)
  }


}
