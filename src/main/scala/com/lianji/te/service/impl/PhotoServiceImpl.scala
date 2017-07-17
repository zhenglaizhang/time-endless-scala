package com.lianji.te.service.impl

import com.lianji.te.domain.Photo
import com.lianji.te.service.{PhotoRepository, PhotoService}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.{Page, Pageable}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PhotoServiceImpl @Autowired()(
  photoRepository: PhotoRepository
) extends PhotoService {

  @Transactional
  override def findAllPageable(pageable: Pageable): Page[Photo] = photoRepository.findAll(pageable)
}
