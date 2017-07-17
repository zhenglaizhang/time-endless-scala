package com.lianji.te.service

import java.lang.Long

import com.lianji.te.domain.{Category, Photo}
import org.springframework.data.domain.{Page, Pageable}
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
trait PhotoRepository extends PagingAndSortingRepository[Photo, Long] {

  // extend with a custom finder
  def findByName(name: String): java.lang.Iterable[Photo]


  def findByCategory(category: Category): java.lang.Iterable[Photo]

  def findByCategory(category: Category, pageable: Pageable): Page[Photo]

  /**
    * findByFirstNameAndLastName
    * findTop10ByFirstName
    * findDistinctEmployeesByFirstName
    * findByFirstNameAndLastNameAllIgnoreCase
    * findByFirstNameOrderByLastNameAsc
    * findByLastNameIsNull
    *
    * stream, async, future support...
    */
}
