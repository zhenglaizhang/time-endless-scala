package com.lianji.te.web

import java.lang

import com.lianji.te.domain.{ Book, Isbn }
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

  // the IsbnEditor is indeed at work, creating an instance of an Isbn class object from the {isbn} parameter
  //  @RequestMapping(value = Array("/{isbn}"), method = Array(RequestMethod.GET))
  @GetMapping(Array("/{isbn}"))
  def getBook(@PathVariable isbn: Isbn): Book = bookRepository.findBookByIsbn(isbn.getIsbn)

  @GetMapping(Array("/{isbn}/reviewers"))
  def getReviewers(@PathVariable("isbn") book: Book) = book.getReviewers
}
