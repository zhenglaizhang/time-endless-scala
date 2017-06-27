= spring-boot-scala-web
A sample web application using spring-boot and Scala


=== Using gradle as the build tool:

Build using `./gradlew build`
Run using `./gradlew bootRun`
OR
Run using: `java -jar build/libs/spring-boot-scala-web-0.3.0.jar`

A url listing the hotels is at http://localhost:8080/hotels[http://localhost:8080/hotels]

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
