package org.casualmiracles.utilities

import org.scalatest.{FunSuite, Matchers}


/**
  * @author yl
  */
class TracerSuite extends FunSuite with Matchers {

  test("Tracer accumulator") {

    val tracer = new Tracer()

    // Default: tracing, console mode
    tracer.trace(5, "String")

    tracer.output() should be("")


    // tracing, accumulator mode
    tracer.tracing = true
    tracer.mode = "acc"
    tracer.trace(0, "String")

    tracer.output() should be("((0,String),\n)")


    // no tracing 
    tracer.clear()
    tracer.tracing = false

    tracer.trace(0, "String")

    tracer.output() should be("")
  }
}