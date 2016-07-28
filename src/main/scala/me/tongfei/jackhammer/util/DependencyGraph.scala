package me.tongfei.jackhammer.util

import edu.jhu.hlt.concrete._
import scala.collection.JavaConversions._

/**
 * @author Tongfei Chen (ctongfei@gmail.com).
 */

object DependencyGraph {

  type DependencyGraph = Graph[String, String]

  /**
   * Constructs a dependency parse graph using the specific dependency parse in a Concrete sentence.
   * @param s Concrete sentence.
   * @param dpId Id of the dependency parse to be used in the Concrete sentence.
   * @return
   */
  def ofConcreteSentence(s: Sentence, dpId: Int): DependencyGraph = {
    val n = s.getTokenization.getTokenList.getTokenListSize
    val vertices = s.getTokenization.getTokenList.getTokenList.map(_.getText)
    val edges = s.getTokenization.getDependencyParseList.get(dpId).getDependencyList
      .map(d => (d.getGov, d.getDep, d.getEdgeType))
    Graph[String, String](n)(vertices:_*)(edges:_*)
  }
}
