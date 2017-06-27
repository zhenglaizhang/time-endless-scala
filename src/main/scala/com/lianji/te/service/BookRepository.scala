package com.lianji.te.service

import com.lianji.te.domain.Book
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
trait BookRepository extends CrudRepository[Book, java.lang.Long] {
  // convention-named mapping that translates the method name into a SQL query
  def findBookByIsbn(isbn: String): Book
}
