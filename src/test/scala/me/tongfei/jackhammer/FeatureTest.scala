package me.tongfei.jackhammer

import me.tongfei.config._
import me.tongfei.granite._
import me.tongfei.granite.io._
import poly.io.Local._

/**
 * @author Tongfei Chen
 */
object FeatureTest extends App {

  val comm = ConcreteIO.loadFromTarGz(Directory("/Users/tongfei/my/data/zh-ldc2015e17/zh").files.head.fullName).head

  val m = comm.entityMentionSetList(1).mentionList(3)

  val lr = LanguageResources("zh", "/Users/tongfei/my/data/zh-ldc2015e17/zh.stopwords.txt", "/Users/tongfei/my/data/zh-ldc2015e17/zh.idf.txt")

  val fx = MentionFeatures.Text ++ MentionFeatures.Words ++ MentionFeatures.WordTrigrams ++ MentionFeatures.Type ++ MentionFeatures.ContextWords(lr, 10)

  val f = fx(m, comm)

  val bp = 0

}
