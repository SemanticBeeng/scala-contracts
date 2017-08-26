name := "scala-contracts"

version := "0.0.1"

scalaVersion := "2.11.8"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"

scalacOptions ++= Seq(
  "-feature",
  "-language:_",
  "-Xfatal-warnings",
  "-deprecation",
  "-unchecked")
