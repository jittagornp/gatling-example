package com.pamarin.monitoring.gatling

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import com.typesafe.config._

/**
 * @author jittagornp
 * create 26/11/2015
 */
class HomePageSimulation extends Simulation {

  val conf = ConfigFactory.load();
  val homePageUrl = conf.getString("homePageUrl");

  val httpConf = http
    .baseURL(homePageUrl)
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .inferHtmlResources( /* include html resources */
      BlackList(),
      WhiteList()
    )
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36");

  val scn = scenario("Go to Home page")
    .exec(http("index").get("/"))

  setUp(scn.inject(atOnceUsers(1)).protocols(httpConf))
}
