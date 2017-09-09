package com.lianji.te

import org.springframework.boot.SpringApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.support.SpringBootServletInitializer


object Application {
  @throws[Exception]
  def main(args: Array[String]): Unit = {
    SpringApplication.run(classOf[ApplicationConfig], args: _*)
  }
}

class Application extends SpringBootServletInitializer {
  override protected def configure(application: SpringApplicationBuilder): SpringApplicationBuilder =
    application.sources(classOf[ApplicationConfig])
}
