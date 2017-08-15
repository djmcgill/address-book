package address

import java.time.LocalDate
import java.time.temporal.ChronoUnit.DAYS

import address.model.{Gender, Record}
import com.github.tototoshi.csv._

import scala.io.Source
import scala.util.Try

// This application reads and analyses an Address Book CSV file
// I'm working with the assumption that in production the file will be
// big enough that you don't necessarily want to keep it in memory
// and scan over it several times. If this is not the case, then
// this solution is extremely over-engineered.
object AddressBook extends App {
  implicit final val LocalDateOrdering = Ordering.fromLessThan[LocalDate](_.isBefore(_))

  println("greeting")

  val reader = Try {
    val addressBookSource = Source.fromResource(Config.addressBookFileName)
    CSVReader.open(addressBookSource)
  }.get // TODO: better error handling

  val records: Seq[Record] = Try(reader.all().map(Record.parse(_).get)).get
  // TODO: better error handling

  // Initial (raw) question answering:
  val menCount = records.count(_.gender == Gender.Male)
  val oldestPerson = records.minBy(_.dob).name
  val bill = records.find(_.name.startsWith("Bill")).get
  val paul = records.find(_.name.startsWith("Paul")).get
  val ageDifference = DAYS.between(bill.dob, paul.dob)

  println(s"1. $menCount")
  println(s"2. $oldestPerson")
  println(s"3. $ageDifference")
}
