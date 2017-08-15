package address

import java.time.LocalDate

import address.model.{Gender, Record}
import org.scalatest._
import org.scalatest.TryValues._

class RecordSpec extends WordSpec with Matchers {
  "Record" should {

    "parse a 20XX date" in {
      val input = Seq("Bill McKnight", "Male", "16/03/11")
      val expectedOutput = Record("Bill McKnight", Gender.Male, LocalDate.parse("2011-03-16"))
      val actualOutput = Record.parse(input)

      actualOutput.success.value shouldBe expectedOutput
    }

    "parse a 19XX date" in {
      val input = Seq("Bill McKnight", "Male", "16/03/77")
      val expectedOutput = Record("Bill McKnight", Gender.Male, LocalDate.parse("1977-03-16"))
      val actualOutput = Record.parse(input)

      actualOutput.success.value shouldBe expectedOutput
    }

    "parse the year as 20XX when the date is before the current date" in {
      val testDate = LocalDate.now.minusDays(5)
      val input = Seq("Bill McKnight", "Male", testDate.format(Record.DobFormat))
      val expectedOutput = Record("Bill McKnight", Gender.Male, testDate)
      val actualOutput = Record.parse(input)

      actualOutput.success.value shouldBe expectedOutput
    }

    "parse the year as 19XX when the date is before the current date" in {
      val testDate = LocalDate.now.plusDays(5)
      val input = Seq("Bill McKnight", "Male", testDate.format(Record.DobFormat))
      val expectedOutput = Record("Bill McKnight", Gender.Male, testDate.minusYears(100))
      val actualOutput = Record.parse(input)

      actualOutput.success.value shouldBe expectedOutput
    }

    "trim all fields before parsing" in {
      val input = Seq("   Bill McKnight  ", "  Male  ", "     16/03/11")
      val expectedOutput = Record("Bill McKnight", Gender.Male, LocalDate.parse("2011-03-16"))
      val actualOutput = Record.parse(input)

      actualOutput.success.value shouldBe expectedOutput
    }

    "fail to parse an invalid date" in {
      val input = Seq("Bill McKnight", "Male", "16/03/XX")
      val actualOutput = Record.parse(input)

      actualOutput.failure
    }

    "fail when there are too few fields" in {
      val input = Seq("Bill McKnight", "Male")
      val actualOutput = Record.parse(input)

      actualOutput.failure
    }

    "fail when there are too many fields" in {
      val input = Seq("Bill McKnight", "Male", "16/03/77", "1 Fake Street")
      val actualOutput = Record.parse(input)

      actualOutput.failure
    }
  }
}
