package address.measures.record

import address.measures.Measure
import org.scalatest.{Matchers, WordSpec}

class FirstSpec extends WordSpec with Matchers {
  "The First measure" should {
    "return None on an empty list" in {
      Measure.calculate(Nil: List[Int])(First(_ => true)) shouldBe None
    }

    "return Some on a single element list with an always true predicate" in {
      val element = 1
      Measure.calculate(List(element))(First(_ => true)) shouldBe Some(element)
    }

    "return None with an always false predicate" in {
      Measure.calculate(List(1, 2, 3))(First(_ => false)) shouldBe None
    }

    "return the first suitable element when multiple ones fit" in {
      val list = List(1, 2, 3)
      def pred(x: Int) = x >= 2
      Measure.calculate(list)(First(pred)) shouldBe list.find(pred)
    }
  }
}
