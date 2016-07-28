package me.tongfei.jackhammer

import me.tongfei.config._
import me.tongfei.granite._
import me.tongfei.granite.io._
import poly.io.Local._

/**
 * @author Tongfei Chen
 */
object FeatureTest extends App {

  val comm = ConcreteIO.load("/Users/tongfei/my/data/en-mit99-qa-concrete/mit99turked-train/mit99turked.1.concrete")

  //val m = comm.entityMentionSetList(1).mentionList()

  val en = LanguageResources("en", "/Users/tongfei/my/data/en.stopwords.txt", "/Users/tongfei/my/data/en.idf.txt")

  val lr = LanguageResources("zh", "/Users/tongfei/my/data/zh-ldc2015e17/zh.stopwords.txt", "/Users/tongfei/my/data/zh-ldc2015e17/zh.idf.txt")

  val fx = MentionFeatures.Text ++ MentionFeatures.Words ++ MentionFeatures.WordTrigrams ++ MentionFeatures.Type ++ MentionFeatures.ContextWords(lr, 10)

  //val f = fx(m, comm)

  val s = comm.sectionList.head.sentenceList.head

  val fss = SentenceFeatures.BagOfWords(s)

  val fs = SentenceFeatures.StemmedBagOfWords(s)

  val fnt = SentenceFeatures.NamedEntityTypes(s)

  val fff = SentenceFeatures.NamedEntities(s)

  val fq = QuestionFeatures.QuestionWordWithLexicalAnswerTypeFeature(s)

  val bp = 0

}
