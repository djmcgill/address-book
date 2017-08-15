package address.measures

case class QuadMeasure[A, B, C, D, E](
    metric1: Measure[A, B],
    metric2: Measure[A, C],
    metric3: Measure[A, D],
    metric4: Measure[A, E]
) extends Measure[A, (B, C, D, E)] {

  def result = (metric1.result, metric2.result, metric3.result, metric4.result)

  def withNext(elem: A): Measure[A, (B, C, D, E)] =
    QuadMeasure(
      metric1.withNext(elem),
      metric2.withNext(elem),
      metric3.withNext(elem),
      metric4.withNext(elem)
    )
}
