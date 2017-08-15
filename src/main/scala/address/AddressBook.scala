package address

import com.github.tototoshi.csv._

import scala.io.Source
import scala.util.Try

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

  println(reader.all())
}
