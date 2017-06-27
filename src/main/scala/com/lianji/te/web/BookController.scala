package com.lianji.te.web

import java.lang

import com.lianji.te.domain.Book
import com.lianji.te.service.BookRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation._

//@Controller
//@ResponseBody
@RestController
@RequestMapping(Array("/books"))
class BookController {
  @Autowired
  private var bookRepository: BookRepository = _

//  @RequestMapping(method = Array(RequestMethod.GET))
  @GetMapping
  def getAllBooks: lang.Iterable[Book] = bookRepository.findAll()

//  @RequestMapping(value = Array("/{isbn}"), method = Array(RequestMethod.GET))
  @GetMapping(Array("/{isbn}"))
  def getBook(@PathVariable isbn: String): Book = bookRepository.findBookByIsbn(isbn)
}
