package address

import java.time.temporal.ChronoUnit.DAYS

import address.model.{Gender, Record}
import com.github.tototoshi.csv._

import scala.io.Source
import scala.util.Try
import Config.LocalDateOrdering
import address.measures.{Measure, QuadMeasure}
import address.measures.record._

// This application reads and analyses an Address Book CSV file
// I'm working with the assumption that in production the file will be
// big enough that you don't necessarily want to keep it in memory
// and scan over it several times. If this is not the case, then
// this solution is extremely over-engineered.
object AddressBook extends App {
  println("greeting")

  val reader = Try {
    val addressBookSource = Source.fromResource(Config.addressBookFileName)
    CSVReader.open(addressBookSource)
  }.get // TODO: better error handling

  val records: Iterator[Record] = reader.iterator.map(Record.parse(_).get)
  // TODO: better error handling

  val OlderFirstName = "Bill"
  val YoungerFirstName = "Paul"

  // n.b. the measures are specialised to Record in this case to make type inference better.
  val measure = QuadMeasure(
    Count(_.gender == Gender.Male),
    MinBy(_.dob),
    Filter(_.name.startsWith(OlderFirstName)),
    Filter(_.name.startsWith(YoungerFirstName))
  )

  val (menCount, maybeOldestName, bills, pauls) =
    Measure.calculate(records)(measure)

  val oldestPerson = maybeOldestName.map(_.name).getOrElse("N/A")
  val ageDifference = (bills, pauls) match {
    case (Seq(bill), Seq(paul)) => DAYS.between(bill.dob, paul.dob).toString
    case _ =>
      s"""|There was not one $OlderFirstName and one $YoungerFirstName.
          |Instead there were ${bills.length} and ${pauls.length}""".stripMargin
  }

  println(s"1. $menCount")
  println(s"2. $oldestPerson")
  println(s"3. $ageDifference")
}
