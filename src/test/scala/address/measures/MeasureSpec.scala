package address.measures

import org.scalatest.{Matchers, WordSpec}

class MeasureSpec extends WordSpec with Matchers {
  "Measure.calculate" should {
    "run the measure on the traversable" in {
      case class Sum(override val result: Int = 0) extends Measure[Int, Int] {
        override def withNext(elem: Int): Measure[Int, Int] = this.copy(result = this.result + elem)
      }

      val list = List(1, 2, 3)
      Measure.calculate(list)(Sum()) shouldBe list.sum
    }

    "do nothing on an empty traversable" in {
      case class Err(override val result: Unit = ()) extends Measure[Int, Unit] {
        override def withNext(elem: Int): Measure[Int, Unit] = fail("withNext should not be invoked!")
      }

      Measure.calculate(Nil: List[Int])(Err()) shouldBe ()
    }
  }
}
