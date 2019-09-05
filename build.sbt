name := "exploding"

version := "0.1"

scalaVersion := "2.12.9"

libraryDependencies ++= Seq(
  "org.typelevel"              %% "cats-core"               % "1.6.0",
  "co.fs2" %% "fs2-core" % "1.0.4",
  "org.scalatest"              %% "scalatest"               % "3.0.7",
  "org.scalamock"              %% "scalamock"                % "4.4.0" % Test,
)
