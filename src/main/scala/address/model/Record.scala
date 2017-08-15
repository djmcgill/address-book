package address.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import address.model.Gender._

import scala.util.{Failure, Try}


object Record {
  final val DobFormat = DateTimeFormatter.ofPattern("dd/MM/yy")

  def parse(fields: Seq[String]): Try[Record] = fields match {
    case Seq(name, gender, dob) => Try {
      val rawDob = LocalDate.parse(dob, DobFormat)
      val pastDob = if (rawDob.isAfter(LocalDate.now)) rawDob.minusYears(100) else rawDob
      Record(
        name = name,
        gender = Gender.withName(gender),
        dob = pastDob
      )
    }
    case _ => Failure(
      new IllegalArgumentException(s"Incorrect number of fields found: ${fields.length}")
    )
  }
}

case class Record(name: String, gender: Gender, dob: LocalDate)
