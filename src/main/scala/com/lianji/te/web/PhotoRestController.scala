package com.lianji.te.web

import java.util.Optional
import scala.collection.JavaConverters._

import com.lianji.te.domain.{Category, Photo}
import com.lianji.te.service.PhotoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.{Page, Pageable}
import org.springframework.mobile.device.Device
import org.springframework.web.bind.annotation.{GetMapping, RequestParam, RestController}

@RestController
@GetMapping(Array("/api"))
class PhotoRestController @Autowired()(private val photoService: PhotoService) {
  @GetMapping(Array("/photos"))
  def listPhotos(pageable: Pageable, device: Device, @RequestParam("category") category: Optional[String]): Page[Photo] = {
    // TODO: use Option
    val photos: Page[Photo] = if (category.isPresent) {
      //      photoService.findAllPageable(pageable, Category.valueOf(category.get()))
      photoService.findAllPageable(pageable, category.get())
    } else {
      photoService.findAllPageable(pageable)
    }

    if (device.isMobile) {
      photos.map { p =>
        p.setUrl(p.getUrl_mobile)
        p.setUrl_index(p.getUrl_mobile_index)
        p
      }
    } else {
      photos
    }
  }
}
