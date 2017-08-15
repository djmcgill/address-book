package address.measures.record

import address.measures.Measure
import address.model.Record

case class Filter[A](pred: Record => Boolean,
                     override val result: List[Record] = Nil)
    extends Measure[Record, List[Record]] {
  override def withNext(elem: Record): Measure[Record, List[Record]] =
    if (pred(elem)) {
      this.copy(result = this.result :+ elem)
    } else {
      this
    }
}
