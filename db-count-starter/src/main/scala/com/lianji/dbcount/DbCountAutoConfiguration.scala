package com.lianji.dbcount

import java.util

import org.springframework.context.annotation.{ Bean, Configuration }
import org.springframework.data.repository.CrudRepository
/*
Spring Boot Auto-Configuration Starter is nothing more than a regular Spring Java
Configuration class annotated with the @Configuration annotation and the presence of
spring.factories in the classpath in the META-INF directory with the appropriate
configuration entries.
During the application startup, Spring Boot uses SpringFactoriesLoader, which is a part
of Spring Core, in order to get a list of the Spring Java Configurations that are configured
for the org.springframework.boot.autoconfigure.EnableAutoConfiguration property
key. Under the hood, this call collects all the spring.factories files located in the METAINF directory from all the jars or other entries in the classpath and builds a composite list
to be added as application context configurations.

a Spring Boot Starter does not need to depend on the Spring Boot library as its compile time dependency
 */

@Configuration
class DbCountAutoConfiguration {
  @Bean
  def dbCountRunner(repositories: util.Collection[CrudRepository[_, _]]) =
    new DbCountRunner(repositories)
}
