package com.lianji.te.domain

import java.lang
import java.util.Locale

import scala.beans.BeanProperty

import com.lianji.te.service.BookRepository
import org.springframework.format.Formatter

/*
since version 3, Spring has added a Formatter interface as a replacement for PropertyEditor.
The Formatters are intended to provide a similar functionality but in a completely thread-safe manner and focusing on a very specific
task of parsing a String in an object type and converting an object to its String representation

As Formatters are stateless, we don’t need to do the registration in our controller for every call; we have to do it only once and this will ensure Spring to use it for every web request.

if you want to define a conversion of a common type, such as String or Boolean—as we did in our IsbnEditor example—it is best to do this via PropertyEditors initialization in Controller’s InitBinder method because such a change is probably not globally desired and is only needed for a particular Controller’s functionality
 */
class BookFormatter(
  @BeanProperty var bookRepository: BookRepository
) extends Formatter[Book] {

  override def parse(bookIdentifier: String, locale: Locale) = {
    val book = bookRepository.findBookByIsbn(bookIdentifier)
    if (book != null) book
    else bookRepository.findOne(lang.Long.valueOf(bookIdentifier))
  }

  override def print(book: Book, locale: Locale) = {
    book.getIsbn
  }
}
