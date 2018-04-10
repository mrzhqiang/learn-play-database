name := "learn-touchat"

version := "1.0"

lazy val `learn-touchat` = (project in file(".")).enablePlugins(PlayJava)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

scalaVersion := "2.11.11"

libraryDependencies ++= Seq(javaJdbc, cache, javaWs,
  "redis.clients" % "jedis" % "2.8.1",
  "com.datastax.cassandra" % "cassandra-driver-core" % "3.1.0",
  "com.datastax.cassandra" % "cassandra-driver-mapping" % "3.1.0",
  "org.elasticsearch.client" % "rest" % "5.0.2",
  "org.apache.httpcomponents" % "fluent-hc" % "4.5.3"
)

unmanagedResourceDirectories in Test <+= baseDirectory(_ / "target/web/public/test")