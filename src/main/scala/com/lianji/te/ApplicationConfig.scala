package com.lianji.te

import com.lianji.dbcount.EnableDbCounting
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.{ Bean, Profile }
import org.springframework.scheduling.annotation.EnableScheduling

//@Configuration // the annotated class contains Spring configuration definitions such as the @Bean, @Component, and @Service declarations
//@EnableAutoConfiguration
//@ComponentScan // scan our application packages—starting from the package of our annotated class as a default package root

/*
Conventionally, this is done by passing the --spring.profiles.active option in the
command line during the application startup.
, another way that this can be done is using the @ActiveProfiles("profile") annotation on the test class

use `spring.profiles.include` option to configure. Any profiles that are set up this way will be added to the list of active profiles

It is also possible to negate profiles such as @Profile("!production").


As the OS Environment Variables typically don’t support dots (.) or dashes (-),
Spring Boot provides an automatic remapping mechanism that replaces the
underscores (_) with dots (.) during the property evaluation as well as handles the
case conversion. Thus, JAVA_HOME becomes synonymous to java.home.

application-{profile}.properties: These are the Profile-specific files that get
applied only if a corresponding Profile gets activated.
 */

@SpringBootApplication
@EnableScheduling
@EnableDbCounting
class ApplicationConfig {
  @Bean
  @Profile(Array("logger", "!prod"))
  def schedulerRunner = new StartupRunner

  def configValuePrinter(
    @Value("${my.config.value}") configValue: String
  ): CommandLineRunner = new CommandLineRunner {
    override def run(args: String*) =
      LoggerFactory.getLogger(getClass).info("Value of my.config.value property={}", configValue)
  }
}
