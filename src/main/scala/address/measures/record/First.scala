package address.measures.record

import address.measures.Measure

case class First[A](pred: A => Boolean,
                    override val result: Option[A] = None)
    extends Measure[A, Option[A]] {
  override def withNext(elem: A): First[A] =
    if (pred(elem) && result.isEmpty) {
      this.copy(result = Some(elem))
    } else {
      this
    }
}
