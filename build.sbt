name := "jackhammer"
organization := "me.tongfei"
version := "0.2.5-SNAPSHOT"

isSnapshot := true

scalaVersion := "2.11.8"

resolvers += Resolver.sonatypeRepo("snapshots")

libraryDependencies += "me.tongfei" %% "config"  % "0.1.2-SNAPSHOT"
libraryDependencies += "me.tongfei" %% "probe"   % "0.7.0-SNAPSHOT"
libraryDependencies += "me.tongfei" %% "poly-io" % "0.2.0-SNAPSHOT"
libraryDependencies += "me.tongfei" %% "granite" % "4.10.3-SNAPSHOT"

libraryDependencies += "edu.stanford.nlp" % "stanford-parser" % "3.5.2"
libraryDependencies += "edu.stanford.nlp" % "stanford-parser" % "3.5.2" classifier "models"
libraryDependencies += "edu.stanford.nlp" % "stanford-corenlp" % "3.5.2"
libraryDependencies += "edu.stanford.nlp" % "stanford-corenlp" % "3.5.2" classifier "models"
