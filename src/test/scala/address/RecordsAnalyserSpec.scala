package address

import java.time.LocalDate

import address.model.{Analysis, Gender, Record}
import org.scalatest.TryValues._
import org.scalatest._

import scala.util.Success

class RecordsAnalyserSpec extends WordSpec with RecordsAnalyser with Matchers {
  "RecordsAnalyser.analyseRecords" should {
    "correctly return results from records" in {
      val billDob = LocalDate.parse("1977-03-16")
      val ageDifference = 124
      val paulDob = billDob.plusDays(ageDifference)
      val billName = "Bill McKnight"
      val records = List(
        Record(billName, Gender.Male, billDob),
        Record("Paul Robinson", Gender.Male, paulDob)
      )
      val expectedResult = Analysis(
        menCount = 2,
        oldestPersonName = Some(billName),
        billPaulAgeDifference = Success(ageDifference)
      )
      analyseRecords(records) shouldBe expectedResult
    }

    "not crash when given no records" in {
      val analysis = analyseRecords(Nil)
      analysis.menCount shouldBe 0
      analysis.oldestPersonName shouldBe None
      analysis.billPaulAgeDifference.failure
    }

    "not crash when the names do not exist" in {
        val billDob = LocalDate.parse("1977-03-16")
        val ageDifference = 124
        val paulDob = billDob.plusDays(ageDifference)
        val billName = "Bill McKnight"
        val records = List(
          Record(billName, Gender.Male, billDob),
          Record("John Robinson", Gender.Male, paulDob)
        )

        val analysis = analyseRecords(records)
        analysis.menCount shouldBe 2
        analysis.oldestPersonName shouldBe Some(billName)
        analysis.billPaulAgeDifference.failure
    }

    "return a negative number when Bill is younger than Paul" in {
      val billDob = LocalDate.parse("1977-03-16")
      val ageDifference = 124
      val paulDob = billDob.minusDays(ageDifference)
      val paulName = "Paul Robinson"
      val records = List(
        Record("Bill McKnight", Gender.Male, billDob),
        Record(paulName, Gender.Male, paulDob)
      )
      val expectedResult = Analysis(
        menCount = 2,
        oldestPersonName = Some(paulName),
        billPaulAgeDifference = Success(-ageDifference)
      )
      analyseRecords(records) shouldBe expectedResult
    }
  }
}
