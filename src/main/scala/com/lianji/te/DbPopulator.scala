package com.lianji.te

import com.lianji.te.domain.{ Author, Book, Hotel, Publisher }
import com.lianji.te.service.{ AuthorRepository, BookRepository, HotelRepository, PublisherRepository }
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

/**
  * Responsible for populating some initial data into the database..
  */

@Component
class DbPopulator @Autowired()(
  val hotelRepository: HotelRepository,
  val authorRepository: AuthorRepository,
  val publisherRepository: PublisherRepository,
  val bookRepository: BookRepository
) extends CommandLineRunner {
  override def run(args: String*): Unit = {
    (1 to 10).foreach(i => {
      hotelRepository.save(new Hotel(id = null, name = s"Hotel $i", address = s"Address $i", zip = s"Zip $i"))
    })

    val author = authorRepository.save(new Author("Meow", "foo"))
    val publisher = publisherRepository.save(new Publisher("Oreily"))
    val book = bookRepository.save(Book("978-1-78528-415-2", "Spring Boot Recipes", author, publisher))
  }
}
