package address

import address.model.Record
import com.github.tototoshi.csv.CSVReader

import scala.io.Source
import scala.util.Try

trait AddressBookParser {
  def parseRecordBook(source: Source): Try[Iterator[Try[Record]]] = {
    val tryIterator = Try(CSVReader.open(source).iterator)
    tryIterator.map(_.map(Record.parse))
  }
}
