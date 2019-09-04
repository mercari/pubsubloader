import sbt._
import Keys._

val scioVersion = "0.7.4"
val beamVersion = "2.11.0"

// recommended setting:: http://www.scalatest.org/user_guide/using_scalatest_with_sbt
logBuffered in Test := false

lazy val commonSettings = Defaults.coreDefaultSettings ++ Seq(
  organization := "com.mercari",
  scalaVersion := "2.11.12",
  scalacOptions ++= Seq("-target:jvm-1.8", "-deprecation", "-feature", "-unchecked"),
  javacOptions ++= Seq("-source", "1.8", "-target", "1.8")
)

lazy val noPublishSettings = Seq(
  publish := {},
  publishLocal := {},
  publishArtifact := false
)

lazy val root = project
  .in(file("."))
  .settings(
    commonSettings ++ noPublishSettings,
    name := "pubsubloader",
    description := "load generator for Cloud Pub/Sub",
    libraryDependencies ++= Seq(
      "com.spotify"         %% "scio-core"                              % scioVersion,
      "org.apache.beam"     % "beam-runners-google-cloud-dataflow-java" % beamVersion,
      "org.apache.beam"     % "beam-runners-direct-java"                % beamVersion,
      "com.google.protobuf" % "protobuf-java-util"                      % "3.5.1",
      "org.scalatest"       %% "scalatest"                              % "3.0.5",
      "com.spotify"         %% "ratatool-scalacheck"                    % "0.3.10",
      "com.spotify"         %% "scio-test"                              % scioVersion % Test
    )
  )

addCommandAlias("fmt", "all scalafmtSbt scalafmt test:scalafmt")
addCommandAlias("check", "all scalafmtSbtCheck scalafmtCheck test:scalafmtCheck")
