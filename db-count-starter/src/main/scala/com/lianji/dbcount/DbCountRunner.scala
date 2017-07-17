package com.lianji.dbcount

import java.util

import scala.collection.JavaConverters._

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.data.repository.CrudRepository

class DbCountRunner(
  val repositories: util.Collection[CrudRepository[_, _]]
) extends CommandLineRunner {
  val log = LoggerFactory.getLogger(getClass)

  private[this] def getRepositoryName[A <: CrudRepository[_, _]](crudRepositoryClass: Class[A]): String = {
    crudRepositoryClass
      .getInterfaces
      .find(_.getName.startsWith("com.lianji"))
      .map(_.getSimpleName)
      .getOrElse("UnknownRepository")
  }

  @throws[Exception]
  override def run(args: String*) = {
    repositories.asScala.foreach { crudRepository =>
      log.info(
        "{} has {} entries",
        getRepositoryName(crudRepository.getClass),
        crudRepository.count()
      )
    }
  }
}
