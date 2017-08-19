package address.model

import scala.util.Try

case class Analysis(menCount: Long, oldestPersonName: Option[String], billPaulAgeDifference: Try[Long])
