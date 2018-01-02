name := "akka-persistence-s3"

scalaVersion := "2.12.4"

organization := "io.findify"

crossScalaVersions := Seq("2.11.8", "2.12.1")

version := "0.1.1"

val akkaVersion = "2.5.8"

licenses := Seq("MIT" -> url("https://opensource.org/licenses/MIT"))

homepage := Some(url("https://github.com/findify/akka-persistence-s3"))

libraryDependencies ++= Seq(
  "com.amazonaws" % "aws-java-sdk-core" % "1.11.224",
  "com.amazonaws" % "aws-java-sdk-s3" % "1.11.224",
  "com.typesafe.akka" %% "akka-persistence" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-persistence-tck" % akkaVersion % "test",
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test",
  "org.scalatest" %% "scalatest" % "2.1.7" % "test",
  "commons-io" % "commons-io" % "2.4" % "test",
  "org.hdrhistogram" % "HdrHistogram" % "2.1.8" % "test",
  "io.findify" %% "s3mock" % "0.2.4" % "test"
)

parallelExecution in Test := false

publishMavenStyle := true

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

pomExtra := (<scm>
    <url>git@github.com:findify/akka-persistence-s3.git</url>
    <connection>scm:git:git@github.com:findify/akka-persistence-s3.git</connection>
  </scm>
    <developers>
      <developer>
        <id>romangrebennikov</id>
        <name>Roman Grebennikov</name>
        <url>http://www.dfdx.me</url>
      </developer>
    </developers>)
