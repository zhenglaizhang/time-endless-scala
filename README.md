## Time endless web app

### Twelve-Factor App Methodology

- https://12factor.net/
- http://www.sybj.com/may.php?c=w&a=organizationCommunity&t=1&hid=1126&id=2508

### Dev

-`export OSS_KEY_ID=xxx`
-`export OSS_KEY_SECRET=xxx`
- `[DEBUG=true] ./gradlew clean bootRun [--debug|-Ddebug=true]`
- `./gradlew build`
- `java -jar build/libs/time-endless-app-0.3.0.jar`
- `./gradlew clean bootRepackage`
- `/build/libs/time-endless-app-0.0.1-SNAPSHOT-exec.jar --spring.profiles.active=logger,inmemorydb`
  - use the `inmemorydb` Profile configuration in order to use the in-memory database instead of the file-based one.

goto `http://localhost:8080/photos` to upload photos


### Photos Rest api

try following requests (auth header ignored):
```
http://localhost:8080/api/photos
http://localhost:8080/api/photos?page=0&size=1
http://localhost:8080/api/photos?page=0&size=1&category=Porttrait
# categories: Porttrait(0), Landscape(1), Animal(2), Other(3);
```

sample output:

```json
{
  "content": [
    {
      "id": 1,
      "category": "Porttrait",
      "name": "name",
      "description": "description",
      "dateTimeOriginal": {
        "year": 2015,
        "month": "JUNE",
        "era": "CE",
        "dayOfYear": 169,
        "dayOfWeek": "THURSDAY",
        "leapYear": false,
        "dayOfMonth": 18,
        "monthValue": 6,
        "chronology": {
          "calendarType": "iso8601",
          "id": "ISO"
        }
      },
      "width": 5760,
      "height": 3840,
      "exposureTime": "1/320 sec",
      "meteringMode": "Multi-segment",
      "exposureProgram": "Program normal",
      "model": "Canon EOS 5D Mark III",
      "make": "Canon",
      "copyright": "LiuMang",
      "isoSpeedRatings": 100,
      "apertureValue": "f/4.6",
      "maxApertureValue": "f/2.8",
      "focalLength": "140 mm",
      "url": "http://timendless.oss-ap-southeast-1.aliyuncs.com/Porttrait/1.jpg",
      "fnumber": "f/4.5"
    }
  ],
  "totalPages": 2,
  "totalElements": 2,
  "last": false,
  "numberOfElements": 1,
  "first": true,
  "sort": null,
  "size": 1,
  "number": 0
}
```

=== Using sbt as the build tool

Start up sbt console

[source, bash]
----
sbt
----

Start App

[source, bash]
----
~re-start
----

## TODO

- `start.spring.io` (switch to `full version`)
- JPA
- H2
- Thymeleaf
- Actuator
- Remote Shell (CRaSH Shell Integration)
- DevTools
- Security
- Test
- `banner.txt`
- check `spring.provides` to see real artifacts provided in each starter
- integrate with jdbc template
- tomcat vs jetty vs Undertow
- fix tomcat https
- Spring Boot Uber JAR

> The Uber JAR is typically known as an application bundle encapsulated in a single
  composite jar that internally contains a /lib directory with all the dependent inner jars and
  optionally a /bin directory with the executables.

## Others

```
# generate certificate store
$JAVA_HOME/bin/keytool -genkey -alias tomcat -keyalg RSA
```

## ELK

- Kibana (User Interface)
- Elasticsearch (Store, index & Analyze)
    - Horizontal Scale
    - Real-Time Availability
    - Flexible Data Model
    - Rapid Query Execution
    - Sophisticated Query Language
    - Schemaless
- Logstash / Beats (Ingest)
