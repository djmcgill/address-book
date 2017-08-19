package address.measures.record

import address.measures.Measure
import org.scalatest._

class CountSpec extends WordSpec with Matchers {
  "The Count measure" should {
    "return 0 when given an empty list" in {
      val pred : Int => Boolean = _ => true
      Measure.calculate(Nil)(Count(pred)) shouldBe 0L
    }

    "count the length when given an always true predicate" in {
      val pred: Int => Boolean = _ > 0
      val list = List(1, 2, 3, 4)
      Measure.calculate(list)(Count(pred)) shouldBe list.length
    }

    "return 0 when given an always false predicate" in {
      val pred: Int => Boolean = _ < 0
      val list = List(1, 2, 3, 4)
      Measure.calculate(list)(Count(pred)) shouldBe 0
    }

    "correctly count when the predicate returns true" in {
      val pred: Int => Boolean = _ < 0
      val list = List(-1, -2, 3, 4, 5)
      Measure.calculate(list)(Count(pred)) shouldBe list.count(pred)
    }
  }
}
