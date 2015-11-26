# ตัวอย่างการใช้ gatling (load testing)

HelloWorldSimulation.scala
```scala
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
import com.typesafe.config._

/**
 * @author jittagornp
 * create 23/11/2015
 */
class HelloWorldSimulation extends Simulation {

  // 1. โหลด config
  val conf = ConfigFactory.load();
  val baseUrl = conf.getString("baseUrl");

  // 2. setup http
  val httpConf = http
    .baseURL(baseUrl)
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.86 Safari/537.36");

  // 3. เขียน scenario  
  val scn = scenario("hello world")
    .exec(http("get index").get("/"));

  // 4. ยิง (atOnceUsers คือจำนวน user ที่เข้าใช้งาน ณ ขณะนั้น หรือจำนวน concurrent)
  setUp(scn.inject(atOnceUsers(1)).protocols(httpConf))

}
```
## การใช้งาน
1. command line cd เข้าไปที่ root project นี้
2. run 
```shell
$ mvn gatling:execute
```
ในกรณีที่มีหลาย simulation (ขึ้นตัวเลข choise ให้เลือก)  ให้พิมพ์ตัวเลขนั้นๆ แล้วกด Enter แล้วรอดูผลลัพธ์

## Require (ติดตั้ง)
1. java (lastest version) อย่าลืม set JAVA_HOME ในตัวแปร PATH
2. maven (https://maven.apache.org/) อย่าลืม set MAVENT_HOME ในตัวแปร PATH
3. scala (http://scala-lang.org/)

## อธิบาย
เมื่อทำการ execute ผลลัพธ์จะถูก generate เป็น file  index.html ใน folder /target/results ที่ config ไว้ใน pom.xml<br/>

*** เขียน code ให้ไปเขียนที่ folder src/test/resources/simulations ***


## เอกสารการเขียน code
http://gatling.io/docs/2.1.7/general/index.html


ผมใช้ Netbeans IDE มันเหมาะกับการขึ้น maven project อยู่แล้ว เลยค่อนข้างง่ายครับ  <br/>
ส่วนการเขียน scala ผมก็หา plugin netbeans มาติดตั้งเพิ่มเติม  <br/>
แต่จริงๆ  จะใช้อะไรก็ได้  ตามที่เราสะดวกครับ
