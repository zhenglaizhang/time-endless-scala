## Time endless web app


### Dev

- `./gradlew build`
- `[DEBUG=true] ./gradlew clean bootRun [--debug|-Ddebug=true]`
- `java -jar build/libs/time-endless-app-0.3.0.jar`

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

## Others

```
# generate certificate store
$JAVA_HOME/bin/keytool -genkey -alias tomcat -keyalg RSA
```
