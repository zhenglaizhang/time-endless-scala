package com.lianji.dbcount

import java.util

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
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
key. Under the hood, this call collects all the spring.factories files located in the METAINF directory from all the jars or other
entries in the classpath and builds a composite list
to be added as application context configurations.

a Spring Boot Starter does not need to depend on the Spring Boot library as its compile time dependency

@ConditionalOnMissingBean
  create the instance of DbCountRunner only if no other bean instance of this class has already been created and added to the application
   context.

Spring Boot will automatically process all the
configuration class entries from spring.factories during the application context
creation.

Without any extra guidance, everything that is annotated with an @Bean
annotation will be used to create a Spring Bean.

Spring Boot adds an ability to conditionally control the rules for when certain @Configuration or
@Bean annotations should be executed and when it is best to ignore them

@ConditionalOnMissingBean annotation to instruct Spring Boot
to create our DbCountRunner bean only if there is no other bean matching either the class
type or bean name already declared elsewhere.
 */

@Configuration
class DbCountAutoConfiguration {
  @Bean
  @ConditionalOnMissingBean
  def dbCountRunner(repositories: util.Collection[CrudRepository[_, _]]) =
    new DbCountRunner(repositories)
}
