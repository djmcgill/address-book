package address

import java.time.LocalDate

import com.typesafe.config.ConfigFactory

object Config {
  private lazy val configFactory = ConfigFactory.load()
  lazy val addressBookFileName: String =
    configFactory.getString("addressBook.fileName")
  implicit final val LocalDateOrdering =
    Ordering.fromLessThan[LocalDate](_.isBefore(_))
}
