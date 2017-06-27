package com.lianji.te

import org.apache.commons.logging.LogFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.core.annotation.Order

/*
Command line runners are a useful functionality to execute the various types of code that
only have to be run once, right after application startup.

We can also use an @Order annotation or implement an
Ordered interface so as to define the exact order in which we want Spring Boot to execute
them
 */

@Order(12)
class StartupRunner extends CommandLineRunner {
  private[this] val log = LogFactory.getLog(getClass)

  override def run(args: String*) = log.info("echo...")
}

