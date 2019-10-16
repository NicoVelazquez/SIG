name := "backend"
 
version := "1.0" 
      
lazy val `backend` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"
      
scalaVersion := "2.12.2"

libraryDependencies ++= Seq(
  jdbc , ehcache , ws , specs2 % Test , guice, evolutions,
  "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % Test,
  "io.swagger" %% "swagger-play2" % "1.6.1",
  "io.getquill" %% "quill-async-postgres" % "3.4.9",
  "org.postgresql" % "postgresql" % "42.2.8",
  "io.getquill" %% "quill-jdbc" % "3.4.9"
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

