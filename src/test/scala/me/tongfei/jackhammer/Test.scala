package me.tongfei.jackhammer

import me.tongfei.granite._
import me.tongfei.granite.io._
import poly.io.Local._

/**
 * @author Tongfei Chen
 */
object Test extends App {

  val names = for {
    tarGz <- Directory("/Users/tongfei/my/data/zh-ldc2015e17/zh").files
    comm <- ConcreteIO.loadFromTarGz(tarGz.fullName)
    ms <- comm.entityMentionSetList
    m <- ms.mentionList if m.entityType == "PERSON"
  } yield m.text

  val pw = new java.io.PrintWriter(File("/Users/tongfei/my/data/zh-ldc2015e17/zh-person.txt").writer)
  names foreach pw.println
  pw.close()

  val bp = 0

}
