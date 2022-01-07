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
  .aggregate(utils, web, ui)

lazy val utils = project
  .in(file("utils"))
  .settings(
    name := "utils",
    version := "0.1",
    scalaVersion := scala2Version,
    libraryDependencies ++= baseTestLibraries
  )

lazy val web = project
  .in(file("web"))
  .settings(
    name := "web",
    version := "0.1",
    scalaVersion := scala2Version,
    libraryDependencies ++= baseTestLibraries
  )
  .dependsOn(utils % "compile->compile;test->test") // tests depend on tests

// to be migrated first, most outer module
lazy val ui = project
  .in(file("ui"))
  .settings(
    name := "ui",
    version := "0.1",
    scalaVersion := scala2Version, //want to migrate to scala3
    libraryDependencies ++= baseTestLibraries
  )
  .dependsOn(web % "compile->compile;test->test", utils % "compile->compile;test->test") // tests depend on tests

