package com.pamarin.monitoring.gatling

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._
import scala.concurrent.duration._
import com.typesafe.config._

/**
 * @author jittagornp
 * create 24/11/2015
 */
class JdbcSimulation extends Simulation {

  val conf = ConfigFactory.load();
  val baseUrl = conf.getString("baseUrl");

  val jdbcUrl = conf.getString("jdbcUrl");
  val jdbcUsername = conf.getString("jdbcUsername");
  val jdbcPassword = conf.getString("jdbcPassword");

  val jdbc = jdbcFeeder(
    jdbcUrl,
    jdbcUsername,
    jdbcPassword,
    "select 'hello db' as message from dual"
  );

  val httpConf = http
    .baseURL(baseUrl)
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36");

  val scn = scenario("jdbc connection")
    .feed(jdbc)
    .exec(session => {
      println(session.get("message").as[String]);      
      session;
    })
    .exec(http("get index").get("/"));

  setUp(scn.inject(atOnceUsers(1)).protocols(httpConf))
}
