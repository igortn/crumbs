name := "tst-test"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",

  /*
   * Added here to illustrate wrapping the Scala Future
   * that was discussed during my face-to-face interview at TST.
   */
  "org.typelevel" %% "cats-core" % "1.1.0"
)