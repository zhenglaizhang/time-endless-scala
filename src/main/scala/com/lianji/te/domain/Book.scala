package com.lianji.te.domain

import java.beans.PropertyEditorSupport
import java.lang.Long
import java.util
import javax.persistence._

import scala.beans.BeanProperty

import org.springframework.util.StringUtils
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.InitBinder

class Isbn(
  @BeanProperty var isbn: String = null
)

class IsbnEditor() extends PropertyEditorSupport {
  @throws[IllegalArgumentException]
  override def setAsText(text: String) = {
    if (StringUtils.hasText(text)) {
      setValue(new Isbn(text.trim))
    } else {
      setValue(null)
    }
  }

  override def getAsText = {
    val isbn = getValue.asInstanceOf[Isbn]
    if (isbn != null) {isbn.getIsbn}
    else {""}
  }

  // PropertyEditor is not thread safe
  // So here we create a new instance of our custom editors for every web request and register them with WebDataBinder.
  @InitBinder
  def initBinder(binder: WebDataBinder) = {
    binder.registerCustomEditor(classOf[Isbn], new IsbnEditor)
  }
}

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

  @BeanProperty
  @ManyToMany
  var reviewers: util.List[Reviewer] = _
}

object Book {
  def apply(isbn: String, description: String, author: Author, publisher: Publisher): Book = {
    val book = new Book()
    book.isbn = isbn
    book.description = description
    book.author = author
    book.publisher = publisher
    book
  }

  def unapply(book: Book): Option[(String, String, Author, Publisher)] = {
    Some(book.isbn, book.description, book.author, book.publisher)
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

  def this() = this(null, null)
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

  def this() = this(null)
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
