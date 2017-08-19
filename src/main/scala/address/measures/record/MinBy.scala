package address.measures.record

import address.measures.Measure

case class MinBy[A, +B](f: A => B, override val result: Option[A] = None)(
    implicit cmp: Ordering[B]
) extends Measure[A, Option[A]] {

  override def withNext(elem: A): MinBy[A, B] = {
    val candidates = result.toList :+ elem
    val minCandidate = candidates.minBy(f)
    this.copy(result = Some(minCandidate))
  }
}
