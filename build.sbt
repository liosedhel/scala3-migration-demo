val scala2Version = "2.13.7"
val scala3Version = "3.1.0"

scalaVersion := "2.13.7"

val baseTestLibraries = Seq(
  "org.scalatest" %% "scalatest" % "3.2.10" % Test
)

lazy val root = project
  .in(file("."))
  .settings(
    name := "scala3-migration-demo",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala2Version
  )
  .aggregate(ui, utils, web)

lazy val utils = project
  .in(file("utils"))
  .settings(
    name := "utils",
    version := "0.1",
    scalaVersion := scala2Version,
    crossScalaVersions ++= Seq(scala2Version, scala3Version),
    libraryDependencies ++= baseTestLibraries
  )

lazy val web = project
  .in(file("web"))
  .settings(
    name := "web",
    version := "0.1",
    scalaVersion := scala2Version,
    crossScalaVersions ++= Seq(scala2Version, scala3Version),
    libraryDependencies ++= baseTestLibraries
  )
  .dependsOn(utils % "compile->compile;test->test") // tests depend on tests

lazy val ui = project
  .in(file("ui"))
  .settings(
    name := "ui",
    version := "0.1",
    scalaVersion := scala2Version, //want to migrate to scala3
    libraryDependencies ++= baseTestLibraries,
    crossScalaVersions ++= Seq(scala2Version, scala3Version),
    scalacOptions := {
      if (scalaVersion.value == "2.13.7") Seq("-Xsource:3")
      else Seq("-source:3.0-migration")
    }
  )
  .dependsOn(
    web % "compile->compile;test->test",
    utils % "compile->compile;test->test"
  ) // tests depend on tests
