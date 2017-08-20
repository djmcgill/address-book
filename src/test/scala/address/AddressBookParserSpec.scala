package address

import java.time.LocalDate

import address.model.{Gender, Record}
import org.scalatest.TryValues._
import org.scalatest._

import scala.io.Source
import scala.util.Success

class AddressBookParserSpec extends WordSpec with AddressBookParser with Matchers {
  "AddressBookParser.parseRecordBook" should {
    "parse the given file" in {
      val source = Source.fromResource("TestAddressBook.csv")
      val records = parseRecordBook(source).get.toList
      records shouldBe List(
        Success(Record("Bill McKnight", Gender.Male, LocalDate.parse("1977-03-16"))),
        Success(Record("Paul Robinson", Gender.Male, LocalDate.parse("1985-01-15"))),
        Success(Record("Gemma Lane", Gender.Female, LocalDate.parse("1991-11-20"))),
        Success(Record("Sarah Stone", Gender.Female, LocalDate.parse("1980-09-20"))),
        Success(Record("Wes Jackson", Gender.Male, LocalDate.parse("1974-08-14")))
      )
    }

    "return Success even when one line cannot be parsed" in {
      val source = Source.fromString("Bill McKnight, Male, xx-03-16")
      val records = parseRecordBook(source).get.toList
      records should have length 1
      records(0).failure
    }

    "parse the other lines even when one cannot be parsed" in {
      val source = Source.fromString("Bill McKnight, Male, xx-03-16\nPaul Robinson, Male, 15/01/85")
      val records = parseRecordBook(source).get.toList
      records should have length 2
      records(0).failure
      records(1).success.value shouldBe Record("Paul Robinson", Gender.Male, LocalDate.parse("1985-01-15"))
    }
  }
}
