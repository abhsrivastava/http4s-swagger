val prj = (project in file(".")).
  enablePlugins(PartialUnification).
  settings(
    name := "rho-swagger-tutorial",
    organization := "com.abhi",
    scalaVersion := "2.12.5",
    version := "1.0.0",
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-core" % "0.18.7",
      "org.http4s" %% "rho-swagger" % "0.18.0",
      "org.http4s" %% "http4s-dsl" % "0.18.7",
      "org.http4s" %% "http4s-blaze-server" % "0.18.7"
    )
  )