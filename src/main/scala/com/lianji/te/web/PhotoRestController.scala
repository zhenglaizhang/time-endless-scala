package com.lianji.te.web

import java.util.Optional

import com.lianji.te.domain.{Category, Photo}
import com.lianji.te.service.PhotoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.{Page, Pageable}
import org.springframework.web.bind.annotation.{GetMapping, RequestParam, RestController}

@RestController
@GetMapping(Array("/api"))
class PhotoRestController @Autowired()(private val photoService: PhotoService) {
  @GetMapping(Array("/photos"))
  def listPhotos(pageable: Pageable, @RequestParam("category") category: Optional[String]): Page[Photo] = {
    // TODO: use Option 
    if (category.isPresent) {
//      photoService.findAllPageable(pageable, Category.valueOf(category.get()))
      photoService.findAllPageable(pageable, category.get())
    } else {
      photoService.findAllPageable(pageable)
    }
  }
}
