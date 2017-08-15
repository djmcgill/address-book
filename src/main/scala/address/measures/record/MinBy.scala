package address.measures.record

import address.measures.Measure
import address.model.Record

case class MinBy[B](f: Record => B,
                    override val result: Option[Record] = None)(
    implicit cmp: Ordering[B]
) extends Measure[Record, Option[Record]] {

  override def withNext(elem: Record): MinBy[B] = {
    val candidates = result.toList :+ elem
    val minCandidate = candidates.minBy(f)
    this.copy(result = Some(minCandidate))
  }
}
