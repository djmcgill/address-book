package address

import java.time.temporal.ChronoUnit.DAYS

import address.measures.{Measure, QuadMeasure}
import address.measures.record.{Count, First, MinBy}
import address.model.{Analysis, Gender, Record}


import scala.collection.TraversableOnce
import scala.util.{Failure, Success}

trait RecordsAnalyser {
  final val OlderFirstName = "Bill"
  final val YoungerFirstName = "Paul"

  def analyseRecords(records: TraversableOnce[Record]): Analysis = {
    import Config.LocalDateOrdering

    val measure: Measure[Record, (Long, Option[Record], Option[Record], Option[Record])] =
      QuadMeasure(
        Count(_.gender == Gender.Male),
        MinBy(_.dob),
        First(_.name.startsWith(OlderFirstName)),
        First(_.name.startsWith(YoungerFirstName))
      )

    val (menCount, maybeOldestName, bills, pauls) =
      Measure.calculate(records)(measure)

    val ageDifference = (bills, pauls) match {
      case (Some(bill), Some(paul)) => Success(DAYS.between(bill.dob, paul.dob))
      case (None, Some(_)) => Failure(new IllegalArgumentException(s"Could not find $YoungerFirstName"))
      case (Some(_), None) => Failure(new IllegalArgumentException(s"Could not find $OlderFirstName"))
      case _ => Failure(new IllegalArgumentException(s"Could not find $OlderFirstName or $YoungerFirstName"))
    }

    Analysis(
      menCount = menCount,
      oldestPersonName = maybeOldestName.map(_.name),
      billPaulAgeDifference = ageDifference
    )
  }
}
