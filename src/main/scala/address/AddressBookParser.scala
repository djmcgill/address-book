package address

import address.model.Record
import com.github.tototoshi.csv.CSVReader

import scala.io.Source
import scala.util.Try

trait AddressBookParser {
  def parseRecordBook(): Try[Iterator[Try[Record]]] = {
    val tryIterator = Try {
      val addressBookSource = Source.fromResource(Config.addressBookFileName)
      CSVReader
        .open(addressBookSource)
        .iterator
    }
    tryIterator.map(_.map(Record.parse))
  }
}
