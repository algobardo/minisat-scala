package my.sat

/** Essential solver types */

object Var {
  type t = Int
  def toInt(v:t) = v
  def apply(v:t) = v
}
/** Literal
 * A literal is either a positive or negavie apparence of a variable.
 */
final class Lit(v:Var.t, sgn:Boolean) {
  val x = if (sgn) v + v + 1 else v + v 
  
  def sign = (x & 1) == 1
  def variable = x >> 1 
  def toInt = x
  override def equals (other: Any) = {
    other match {
      case that:Lit => (x == that.x)
      case _ => false
    }
  }
}

object Lit {
  def apply(v:Var.t, sign:Boolean) = new Lit(v,sign)
  def not(l:Lit) = new Lit(l.variable, !l.sign)
}

/** Clause
 * A clause contains a vector of literals
 * 
 */
class Clause(lits: Array[Lit], 
	     val learnt:Boolean) {
  // Header info

  var lit = lits.clone // Local copy, mutable 

  // The following was implemented as union in MiniSAT.
  var activity = if (learnt) 0.0 else -1.0 // Only useful for learnt
  var abst = calcAbstraction

  def calcAbstraction = {
    // Abstraction computes a uint32 representation of 
    // the bit vector corresponding to the variables in
    // the clause. 
    var abstraction = 0
    for (i <- 0 until lit.length ) {
      abstraction |= 1 << (lit(i).variable & 31)
    }
    abstraction
  }

  def size = lit.length

  // Get the i-th literal 
  def apply(i:Int) = lit(i)
  def indexOf(l: Lit) = lit.indexOf(l)
  def remove(l: Lit) {
    val i = indexOf(l)
    lit(i) = lit.last
    lit = lit.dropRight(1)
  }

  def strengthen(p:Lit) {
    remove(p)
    abst = calcAbstraction
  }
  
  
}