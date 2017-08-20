package address.measures.record

import address.measures.Measure
import org.scalatest.{Matchers, WordSpec}

class MinBySpec extends WordSpec with Matchers {
  "The MinBy measure" should {
    "return None on an empty list" in {
      Measure.calculate(Nil: List[Int])(MinBy(identity)) shouldBe None
    }

    "return Some on a single element list" in {
      val element = 1
      Measure.calculate(List(element))(MinBy(identity)) shouldBe Some(element)
    }

    "return the minimum of the result of a function on a list" in {
      val list = List(1, 2, 3, 4, 5)
      def f(x: Int) = (3-x).abs
      Measure.calculate(list)(MinBy(f)) shouldBe Some(list.minBy(f))
    }
  }
}
