lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "uk.co.djmcgill",
      scalaVersion := "2.12.3",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "AddressBook",
    libraryDependencies ++= List(
      "com.github.tototoshi" %% "scala-csv" % "1.3.4",
      "com.typesafe" % "config" % "1.3.1",
      "org.scalatest" %% "scalatest" % "3.0.3" % Test
    )
  )
