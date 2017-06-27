package com.lianji.te

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling

//@Configuration // the annotated class contains Spring configuration definitions such as the @Bean, @Component, and @Service declarations
//@EnableAutoConfiguration
//@ComponentScan // scan our application packages—starting from the package of our annotated class as a default package root
@SpringBootApplication
@EnableScheduling
class ApplicationConfig {
  @Bean
  def schedulerRunner = new StartupRunner
}
