lazy val Settings = Seq(
  organization := "com.exploding",
  name := "exploding",
  version := "0.0.1-SNAPSHOT",
  scalaVersion := "2.12.9",
  cancelable in Global := true,
  scalafmtOnCompile in ThisBuild := true,
  scalacOptions := Seq("-Ywarn-unused:imports", "-Xfatal-warnings", "-feature", "-language:higherKinds")
)

lazy val root = (project in file("."))
  .settings(Settings,
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-core" % "1.6.0",
      "co.fs2" %% "fs2-core" % "1.0.4",
      "org.scalatest" %% "scalatest" % "3.0.7",
      "org.scalamock" %% "scalamock" % "4.4.0" % Test,
    ))
