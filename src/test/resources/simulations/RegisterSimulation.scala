package com.pamarin.monitoring.gatling

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import com.typesafe.config._

/**
 * @author jittagornp
 * create 25/11/2015
 */
class RegisterSimulation extends Simulation {

  val csvFeeder = csv("register.csv").queue;
  val template = ELFileBody("register_template.json");

  val conf = ConfigFactory.load();
  val baseUrl = conf.getString("baseUrl");

  val httpConf = http
    .baseURL(baseUrl)
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36");

  val scn = scenario("Register")
    .repeat(100) {
      feed(csvFeeder)
        .exec(
          http("register")
            .post("/register")
            .header("Content-Type", "application/json")
            .body(template).asJSON
            .check(
              status.is(200)
            )
        );
    };

  setUp(scn.inject(atOnceUsers(1)).protocols(httpConf))
}
