package address.measures

import org.scalatest.{Matchers, WordSpec}

class QuadMeasureSpec extends WordSpec with Matchers {
  "QuadMeasureSpec" should {
    "combine 4 different measures into one" in {
      case class Increment(inc: Int, override val result: Int = 0) extends Measure[Unit, Int] {
        override def withNext(elem: Unit): Measure[Unit, Int] = this.copy(result = this.result + inc)
      }

      val measure: QuadMeasure[Unit, Int, Int, Int, Int] = QuadMeasure(
        Increment(1),
        Increment(2),
        Increment(3),
        Increment(4)
      )

      val count = 5
      val list = List.fill(5)(())
      Measure.calculate(list)(measure) shouldBe (1*count, 2*count, 3*count, 4*count)
    }
  }
}
