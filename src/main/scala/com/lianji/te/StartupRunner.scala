package com.lianji.te

import javax.sql.DataSource

import com.lianji.te.service.{ BookRepository, PhotoRepository }
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
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
  private[this] val log = LoggerFactory.getLogger(getClass)

  /*
 Spring Boot recognized that we’ve autowired a DataSource dependency and automatically created one initializing the in-memory H2 data store.

@Bean tells Spring 'here is an instance of this class, please keep hold of it and give it back to me when I ask'.
Annotating@Bean only register this service as a bean(i.e a kind of Object) in spring application context. In simple word, it is just registration but nothing.

@Autowired says 'please give me an instance of this class, for example, one that I created with an @Bean annotation earlier'.
Annotating a variable with @Autowired inject a BookingService bean(i.e Object) from Spring Application Context.
   */
  @Autowired
  private var ds: DataSource = _

  @Autowired
  private var bookRepository: BookRepository = _

  @Autowired
  private var photoRepository: PhotoRepository = _

  @throws[Exception]
  override def run(args: String*) = {
    log.info("Datasource: {}", ds.toString)
    log.info("book counts: {}", bookRepository.count())
    log.info("photo counts: {}", photoRepository.count())
  }
}

