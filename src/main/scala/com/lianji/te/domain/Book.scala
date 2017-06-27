package com.lianji.te.domain

import java.lang.Long
import java.util
import javax.persistence._

import scala.beans.BeanProperty

// @Entity indicates that the annotated class should be mapped to a database table
//  every entity class should have a default protected constructor, which is needed for automated instantiation and Hibernate interactions
@Entity
class Book extends Serializable {
  @BeanProperty
  @Id
  @GeneratedValue
  var id: java.lang.Long = _

  @BeanProperty
  var isbn: String = _

  @BeanProperty
  var description: String = _

  @BeanProperty
  @ManyToOne
  var author: Author = _

  @BeanProperty
  @ManyToOne
  var publisher: Publisher = _

  @ManyToMany
  var reviewers: util.List[Reviewer] = _
}

object Book {
  def apply(id: java.lang.Long, isbn: String, description: String, author: Author, publisher: Publisher): Book = {
    val book = new Book()
    book.id = id
    book.isbn = isbn
    book.description = description
    book.author = author
    book.publisher = publisher
    book
  }

  def unapply(book: Book): Option[(java.lang.Long, String, String, Author, Publisher)] = {
    Some(book.id, book.isbn, book.description, book.author, book.publisher)
  }
}


@Entity
class Author(fn: String = null, ln: String = null) {

  @BeanProperty
  @Id
  @GeneratedValue
  var id: Long = _

  @BeanProperty
  var firstName: String = fn

  @BeanProperty
  var lastName: String = ln

  @BeanProperty
  @OneToMany(mappedBy = "author")
  var books: util.List[Book] = _
}

@Entity
class Publisher(n: String = null) {
  @BeanProperty
  @Id
  @GeneratedValue
  var id: Long = _

  @BeanProperty
  var name: String = n

  // The mappedBy attribute in @OneToMany annotation declaration defines a reverse association mapping.
  @OneToMany(mappedBy = "publisher")
  @BeanProperty
  var books: java.util.List[Book] = _
}


@Entity
class Reviewer(fn: String = null, ln: String = null) {

  @BeanProperty
  @Id
  @GeneratedValue
  var id: Long = _

  @BeanProperty
  var firstName: String = fn

  @BeanProperty
  var lastName: String = ln
}
