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
