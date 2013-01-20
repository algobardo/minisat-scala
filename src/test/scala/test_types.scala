package test.sat

import my.sat._

import org.scalatest.FunSuite 

class TypeTestSuite extends FunSuite {
  test("Var") {
    val a = Var(1)
    assert( a == 1)
    assert( Var.toInt(a) == 1)
  }

  test("Lit") {
    val a = Var(1)
    val al = Lit(a, true)
    val af = Lit(a, false)
    assert(al.toInt == Var.toInt(a)*2 +1)
    assert(af.toInt == Var.toInt(a)*2)
  }

  test("Clause") {
    val a = Var(0)
    val b = Var(2)
    val lits = Array(Lit(a,true), Lit(b,false))
    val cl = new Clause(lits, false)
    assert(cl.abst == 5)  // Abstraction

    cl.strengthen(Lit(a,true))
    assert(cl.size == 1)
    assert(cl.abst == 4)
  }
}