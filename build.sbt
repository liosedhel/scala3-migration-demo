val scala2Version = "2.13.7"
val scala3Version = "3.1.1"

scalaVersion := scala2Version

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
    libraryDependencies ++= Seq("dev.zio" %% "izumi-reflect" % "2.0.8"),
    // https://mvnrepository.com/artifact/org.reflections/reflections
    libraryDependencies += "org.reflections" % "reflections" % "0.10.2",
    libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.2.7" % Test cross CrossVersion.for3Use2_13,
    libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.6.18" % Test, // cross CrossVersion.for3Use2_13,
    libraryDependencies += "com.typesafe.akka" %% "akka-slf4j" % "2.6.18" % Test, // cross CrossVersion.for3Use2_13,
    libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.6.18" % Test, // cross CrossVersion.for3Use2_13,
    libraryDependencies += {
      ("org.elasticmq" %% "elasticmq-rest-sqs" % "0.15.8" % Test cross CrossVersion.for3Use2_13)
        .exclude("com.typesafe.akka", "akka-stream_2.13")
        .exclude("com.typesafe.akka", "akka-actor_2.13")
        .exclude("com.typesafe.akka", "akka-protobuf-v3_2.13")
        //.exclude //(Akka.groupId, "akka-cluster-sharding_2.13")
        //.exclude //(Akka.groupId, "akka-cluster_2.13")
        //.exclude //(Akka.groupId, "akka-discovery_2.13")
        //.exclude //(Akka.groupId, "akka-distributed_data_2.13")
        //.exclude //(Akka.groupId, "akka-stream-testkit_2.13")
        //.exclude //(Akka.groupId, "akka-http-core_2.13")
        //.exclude //(Akka.groupId, "akka-http_2.13")
        .exclude("org.scala-lang", "scala-library")
        .exclude("org.scala-lang.modules", "scala-xml_2.13")
        .exclude(
          "com.typesafe.akka",
          "akka-slf4j_2.13"
        ) // to simulate sqs queue in tests
    },
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
      else Seq.empty //Seq("-source:3.0-migration")
    }
    // ThisBuild / semanticdbEnabled := true,
    // ThisBuild / semanticdbOptions += "-P:semanticdb:synthetics:on", // make sure to add this
    // ThisBuild / semanticdbVersion := scalafixSemanticdb.revision,
    // ThisBuild / scalafixScalaBinaryVersion := CrossVersion.binaryScalaVersion(
    //   scalaVersion.value
    // ),
    // ThisBuild / scalafixDependencies += "org.scala-lang" %% "scala-rewrites" % "0.1.3"
  )
  .dependsOn(
    web % "compile->compile;test->test",
    utils % "compile->compile;test->test"
  ) // tests depend on tests

lazy val hello = taskKey[Unit]("Generates graph for sbt modules")
hello := {
  println((Compile / configuration).value.extendsConfigs)
  val a = thisProjectRef.value

  buildDependencies.value.aggregate.map { case (parent, child) =>
    child.foreach(c => println(s"${parent.project}, ${c.project}"))
    println(productDirectories.in(Compile).value)
  }
  println(update.value)
  //root.dependencies.foreach { clpDepth => }
}
