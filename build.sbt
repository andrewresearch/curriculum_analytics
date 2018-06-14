// Copyright (C) 2018 the original author or authors.
// See the LICENCE.txt file distributed with this work for additional
// information regarding copyright ownership.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

//import LocalSbtSettings._

lazy val projectVersion = "0.1.0"
lazy val projectOrganisation = "au.edu.qut"
lazy val projectName = "curriculum_analytics"
lazy val serverName = s"${projectName}_server"
lazy val clientName = s"${projectName}_client"
lazy val sharedName = s"${projectName}_shared"

//Versions
scalaVersion in ThisBuild := "2.12.6"

lazy val vScalaTags = "0.6.7"
lazy val vXmlBind = "2.3.0"
lazy val vUpickle = "0.6.6"
lazy val vPlayJson = "2.6.9"
lazy val vSilhouette = "5.0.4"
lazy val vPostgresDriver = "42.2.2"
lazy val vSlick = "3.2.1"
lazy val vSlickpg = "0.16.1"
lazy val vPlaySlick = "3.0.3"

lazy val vScalaJsDom = "0.9.5"
lazy val vWebpack = "4.10.2"
lazy val vWebpackDevServer = "3.1.4"

lazy val vBootstrap = "4.1.1"
lazy val vJquery = "3.2.1"
lazy val vPopper = "1.14.3"
lazy val vD3 = "5.4.0"

//Settings
val sharedSettings = Seq(
  organization := projectOrganisation,
  version := projectVersion
)

//Dependencies

val playDeps = Seq(ws, guice, ehcache, specs2 % Test)

val generalDeps = Seq(
  "com.typesafe.play" %% "play-json" % vPlayJson,
  "javax.xml.bind" % "jaxb-api" % vXmlBind, //to fix issue with missing xml in Java9
  "com.lihaoyi" %% "scalatags" % vScalaTags, //Using ScalaTags instead of Twirl
  "com.lihaoyi" %% "upickle" % vUpickle //Using uJson for main JSON
)

val authDeps = Seq(
  "com.mohiva" %% "play-silhouette" % vSilhouette,
  "com.mohiva" %% "play-silhouette-password-bcrypt" % vSilhouette,
  "com.mohiva" %% "play-silhouette-persistence" % vSilhouette,
  "com.mohiva" %% "play-silhouette-crypto-jca" % vSilhouette,
  "net.codingwell" %% "scala-guice" % "4.1.1", //extention to guice DI - latest version breaks
  "com.iheart" %% "ficus" % "1.4.3", //extention to typesafe config
  "com.mohiva" %% "play-silhouette-testkit" % vSilhouette % "test",
  //"net.minidev" % "json-smart" % "2.3"
)

val dbDeps = Seq(
  "org.postgresql" % "postgresql" % vPostgresDriver,
  "com.typesafe.play" %% "play-slick" % vPlaySlick,
  "com.github.tminglei" %% "slick-pg" % vSlickpg exclude("org.postgresql","postgresql"), //provided by postgresql
  "com.github.tminglei" %% "slick-pg_play-json" % vSlickpg

)

lazy val curriculum_analytics = project.in(file("."))
  .dependsOn(server,client)
  .aggregate(server,client)
  .settings(
    sharedSettings,
    libraryDependencies ++= playDeps,
    libraryDependencies ++= generalDeps,
    libraryDependencies ++= authDeps,
    publish := {},
    publishLocal := {}
  ).enablePlugins(PlayScala)

lazy val server = project.in(file(serverName))
  .settings(
    sharedSettings,
    libraryDependencies ++= dbDeps,
    //      buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    //      buildInfoPackage := projectOrganisation,
    //      buildInfoOptions += BuildInfoOption.BuildTime,
  )

lazy val client = project.in(file(clientName))
  .settings(
    sharedSettings,
    scalaJSUseMainModuleInitializer := true,
    artifactPath in(Compile, fastOptJS) := baseDirectory.value / ".." / "public" / "javascripts" / "client-fastOptJS.js",
    webpackBundlingMode := BundlingMode.LibraryAndApplication(),
    version in webpack := vWebpack,
    version in startWebpackDevServer := vWebpackDevServer,
    libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % vScalaJsDom,
      "org.singlespaced" %%% "scalajs-d3" % "0.3.4",
      "com.github.karasiq" %%% "scalajs-bootstrap-v4" % "2.3.1",
      "com.lihaoyi" %%% "scalatags" % vScalaTags, //Using ScalaTags instead of Twirl
      "com.lihaoyi" %%% "upickle" % vUpickle //Using uJson for main JSON
    ),
    npmDependencies in Compile ++= Seq(
      "bootstrap" -> vBootstrap,
      "jquery" -> vJquery, //used by bootstrap
      "popper.js" -> vPopper, //used by bootstrap
      "d3" -> vD3
    )
  )
  .enablePlugins(ScalaJSPlugin)
  .enablePlugins(ScalaJSBundlerPlugin)

