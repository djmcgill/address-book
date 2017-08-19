package address

import address.model.{Analysis, Record}

import scala.util.Try

// This application reads and analyses an Address Book CSV file
// I'm working with the assumption that in production the file will be
// big enough that you don't necessarily want to keep it in memory
// and scan over it several times. If this is not the case, then
// this solution is extremely over-engineered.
object AddressBook extends App with AddressBookParser with RecordsAnalyser with AnalysisPrinter {

  val records: Iterator[Try[Record]] =
    parseRecordBook().recover{ case ex =>
      throw new IllegalStateException(s"Could not open the recordbook file with:", ex)
    }.get

  // Since we 1) want to stop if any record fails to parse, and
  // 2) parse the records lazily, the exception will be thrown from inside the
  // analyseRecords method c.f. The Curse of the Excluded Middle by Erik Meijer
  val analysis: Analysis = Try {
    val allRecords: Iterator[Record] =
      records.map( tryRecord =>
        tryRecord.recover { case ex =>
          throw new IllegalArgumentException(s"Could not parse a record with:", ex)
        }.get
      )
    analyseRecords(allRecords)
  }
  .recover{case ex => throw new IllegalArgumentException(s"Could not analyse the records with:", ex)}
  .get

  printAnalysis(analysis)
}
