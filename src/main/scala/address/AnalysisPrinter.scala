package address

import address.model.Analysis

import scala.util.{Failure, Success}

trait AnalysisPrinter {
  def printAnalysis(analysis: Analysis): Unit = {
    val ageDifferenceString = analysis.billPaulAgeDifference match {
      case Success(ageDifference) => ageDifference.toString
      case Failure(ex) => ex.getMessage
    }

    println(s"1. ${analysis.menCount}")
    println(s"2. ${analysis.oldestPersonName.getOrElse("N/A")}")
    println(s"3. $ageDifferenceString")
  }
}
