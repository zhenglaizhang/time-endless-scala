package com.lianji.te

import com.lianji.te.service._
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

/**
  * Responsible for populating some initial data into the database..
  */
@Component
class DbPopulator @Autowired()(
  photoRepository: PhotoRepository,
  photoService: PhotoService,
  photoMetadataService: PhotoMetadataService
) extends CommandLineRunner {
  override def run(args: String*): Unit = {

  }
}
