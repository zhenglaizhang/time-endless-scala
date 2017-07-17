package com.lianji.te.service

import com.lianji.te.domain.Photo
import org.springframework.data.domain.{Page, Pageable}

trait PhotoService {

  def findAllPageable(pageable: Pageable): Page[Photo]
}
