package address.measures

object Measure {
  def calculate[A, B](elements: TraversableOnce[A])(
      initialMeasure: Measure[A, B]): B =
    elements.foldLeft(initialMeasure)(_.withNext(_)).result
}
trait Measure[A, +B] {
  def result: B
  def withNext(elem: A): Measure[A, B]
}
