package com.manyangled.util

trait Constructable[+T] {
  def construct(): T
}

object Constructable {
  def apply[T](blk: => T): Constructable[T] = new Constructable[T] {
    def construct(): T = blk
  }
}

trait ConstructableFromDouble[+T] {
  def construct(v: Double): T
}

object ConstructableFromDouble {
  def apply[T](f: (Double) => T) = new ConstructableFromDouble[T] {
    def construct(v: Double): T = f(v)
  }
}

