package com.lianji.te.service

import com.lianji.te.domain.{Category, Photo}
import org.springframework.data.domain.{Page, Pageable}

trait PhotoService {

  def findAllPageable(pageable: Pageable, category: String): Page[Photo]

  def findAllPageable(pageable: Pageable, category: Category): Page[Photo]

  def findAllPageable(pageable: Pageable): Page[Photo]
}
