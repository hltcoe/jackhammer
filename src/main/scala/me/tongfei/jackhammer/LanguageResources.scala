package me.tongfei.jackhammer

import poly.io.Local._
import me.tongfei.probe.util._

/**
 * @author Tongfei Chen
 */
class LanguageResources private(val id: String, val stopwords: Set[String], val idf: Map[String, Double]) {

}

object LanguageResources {

  def apply(id: String, stopwordsFile: String, idfFilename: String) = {
    val stopwordsSet = File(stopwordsFile).lines.toSet
    val idfMap = File(idfFilename).lines.map { s =>
      val sa = s.split('\t')
      sa(0) -> sa(1).toDouble }.toMap
    new LanguageResources(id, stopwordsSet, idfMap)
  }

}
