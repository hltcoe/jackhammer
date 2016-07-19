name := "jackhammer"
organization := "me.tongfei"
version := "0.2.0-SNAPSHOT"

isSnapshot := true

scalaVersion := "2.11.8"

resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies += "me.tongfei" %% "config"  % "0.1.2-SNAPSHOT"
libraryDependencies += "me.tongfei" %% "probe"   % "0.6.5-SNAPSHOT"
libraryDependencies += "me.tongfei" %% "poly-io" % "0.2.0-SNAPSHOT"
libraryDependencies += "me.tongfei" %% "granite" % "0.5.1-SNAPSHOT"
