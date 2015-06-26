package org.casualmiracles.finance.contracts

trait PRs extends Zip {

  def max[T](pra: PR[T], prb: PR[T])(implicit ev: T ⇒ Double): PR[Double] = lift2Pr((a: T, b: T) ⇒ math.max(a, b), pra, prb)

  def condPr[T](aPr: PR[Boolean], bPr: PR[T], cPr: PR[T]): PR[T] = lift3Pr((b: Boolean, tru: T, fal: T) ⇒ if (b) tru else fal, aPr, bPr, cPr)

  def liftPr[A, B](f: A ⇒ B, pr: PR[A]): PR[B] = PR(pr.unPr.map(_.map(f(_))))

  def lift2Pr[A, B, C](f: (A, B) ⇒ C, aPr: PR[A], bPr: PR[B]): PR[C] = {
    val rvF = (rvA: RV[A], rvB: RV[B]) ⇒ zipWith(rvA, rvB)(f(_, _))
    PR(zipWith(aPr.unPr, bPr.unPr)(rvF))
  }

  def lift2PrAll[A](f: (A, A) ⇒ A, aPr: PR[A], bPr: PR[A]): PR[A] = {
    val rvF = (rvA: RV[A], rvB: RV[A]) ⇒ zipWith(rvA, rvB)(f(_, _))
    PR(zipWithAll(rvF, aPr.unPr, bPr.unPr))
  }

  def lift3Pr[A, B, C, D](f: (A, B, C) ⇒ D, aPr: PR[A], bPr: PR[B], cPr: PR[C]): PR[D] = {
    val rvF = (rvA: RV[A], rvB: RV[B], rvC: RV[C]) ⇒ zipWith3(rvA, rvB, rvC)(f(_, _, _))
    PR(zipWith3(aPr.unPr, bPr.unPr, cPr.unPr)(rvF))
  }

  def formatPr(pr: PR[_], n: Int, margin:String = ""):String = pr.unPr.take(n).zipWithIndex.map( is ⇒ { margin + is._2 + ": " + formatRV(is._1) } ).mkString("")

  def formatRV(rv: RV[_]):String = {
    rv.map( x => x match{
      case d:Double => "%7.3f".format(d)
      case d:Any => d
    } ).mkString(" ") + "\n"
  }
}

object PR extends PRs {

  implicit class PrOps(prA: PR[Double]) {
    def %+(prB: PR[Double]): PR[Double] = lift2PrAll((_: Double) + (_: Double), prA, prB)
    def %-(prB: PR[Double]): PR[Double] = lift2PrAll((_: Double) - (_: Double), prA, prB)
    def %*(prB: PR[Double]): PR[Double] = lift2PrAll((_: Double) * (_: Double), prA, prB)
    def abs: PR[Double] = liftPr((d: Double) ⇒ math.abs(d), prA)
    def signum: PR[Double] = liftPr((d: Double) ⇒ math.signum(d), prA)
  }
}

case class PR[A](unPr: Stream[RV[A]])