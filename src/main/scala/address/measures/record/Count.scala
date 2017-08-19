package address.measures.record

import address.measures.Measure

case class Count[A](predicate: A => Boolean, override val result: Long = 0)
    extends Measure[A, Long] {
  override def withNext(elem: A): Count[A] =
    if (predicate(elem)) {
      this.copy(result = this.result + 1)
    } else {
      this
    }
}
