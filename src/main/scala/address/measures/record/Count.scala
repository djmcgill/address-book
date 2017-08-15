package address.measures.record

import address.measures.Measure
import address.model.Record

case class Count(predicate: Record => Boolean, override val result: Long = 0)
    extends Measure[Record, Long] {
  override def withNext(elem: Record): Count =
    if (predicate(elem)) {
      this.copy(result = this.result + 1)
    } else {
      this
    }
}
