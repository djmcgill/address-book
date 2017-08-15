package address.model

import java.time.LocalDate

import address.model.Gender._

case class Record(name: String, gender: Gender, dob: LocalDate)
