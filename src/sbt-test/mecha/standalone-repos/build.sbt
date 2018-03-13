
lazy val `standalone-repos` = (project in file("."))
  .settings(
    name := "standalone-repos",
    scalaVersion := "2.11.4",
    version := "0.1.0-SNAPSHOT",
    organization := "com.storm-enroute",

    TaskKey[Unit]("publishUtilsLocal") := {
      val process = sbt.Process(
        Seq("sbt", "publishLocal"),
        file("examples-core-utils")
      )
      val exitCode = (process!<)
      if (exitCode != 0) {
        sys.error(s"Nonzero exit value: $exitCode")
      }
      ()
    },

    TaskKey[Unit]("checkApp") := {
      val pluginVersion = sys.props("plugin.version")
      println(s"pluginVersion: $pluginVersion")

      val process = sbt.Process(
        Seq("sbt", s"""-Dplugin.version=$pluginVersion""", "assembly", "check"),
        file("examples-application")
      )
      val exitCode = (process!<)
      if (exitCode != 0) {
        sys.error(s"Nonzero exit value: $exitCode")
      }
      ()
    }
  )
