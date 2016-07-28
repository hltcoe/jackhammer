package me.tongfei.jackhammer.util

import scala.collection._

/**
 * @author Tongfei Chen (ctongfei@gmail.com).
 */

class Adjacency[E] {
  var prev = mutable.ListMap[Int, E]()
  var next = mutable.ListMap[Int, E]()
}

class Graph[V, E] { self =>

  var vertexData = mutable.HashMap[Int, V]()

  var r = mutable.HashMap[Int, Adjacency[E]]()

  def outgoingIdsOf(v: Int) = r.get(v) match {
    case Some(a) => a.next.keys
    case None => Iterable()
  }

  def incomingIdsOf(v: Int) = r(v).prev.keys

  def apply(u: Int, v: Int): E = r(u).next(v)

  def apply(v: Int): V = vertexData(v)

  def update(u: Int, v: Int, w: E) = r(u).next(v) = w

  def update(v: Int, data: V) = vertexData(v) = data

  def addEdge(u: Int, v: Int, w: E) = {
    r(u).next(v) = w
    r(v).prev(u) = w
  }

  def removeEdge(u: Int, v: Int) = {
    r(u).next -= v
    r(v).prev -= u
  }

  def breadthFirstIterator(s: Int): Iterator[Int] = new Iterator[Int] {
    val queue = mutable.Queue[Int](s)
    val visited = mutable.HashSet[Int](s)

    def hasNext = queue.nonEmpty

    def next() = {
      val curr = queue.dequeue()
      self.outgoingIdsOf(curr).filter(v => !visited.contains(v)).foreach { v =>
        visited += v
        queue.enqueue(v)
      }
      curr
    }
  }

}

object Graph {
  def apply[V, E](n: Int)(vertices: V*)(edges: (Int, Int, E)*): Graph[V, E] = {
    val g = new Graph[V, E]
    for (edge ← edges) {
      val u = edge._1
      val v = edge._2
      val w = edge._3
      if (!g.r.contains(u)) g.r(u) = new Adjacency[E]
      if (!g.r.contains(v)) g.r(v) = new Adjacency[E]
      g.r(u).next += v → w
      g.r(v).prev += u → w
    }
    for ((vertex, v) ← vertices.zipWithIndex) {
      g.vertexData(v) = vertex
    }
    g
  }
}
