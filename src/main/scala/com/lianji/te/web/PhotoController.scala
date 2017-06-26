package com.lianji.te.web

import java.lang.Long
import javax.validation.Valid

import com.lianji.te.domain.Photo
import com.lianji.te.domain.request.PhotoRequest
import com.lianji.te.service.{ PhotoMetadataService, PhotoRepository }
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{ HttpStatus, ResponseEntity }
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation._

@Controller
@RequestMapping(Array("/photos"))
class PhotoController @Autowired()(
  private val photoRepository: PhotoRepository,
  // TODO: auto bean injection!
  private val photoService: PhotoMetadataService = new PhotoMetadataService
) {
  private[this] val log = LoggerFactory.getLogger(getClass)

  @GetMapping
  def list(model: Model) = {
    val photos = photoRepository.findAll()
    model.addAttribute("photos", photos)
    "photos/list"
  }

  @GetMapping(Array("/edit/{id}"))
  def edit(@PathVariable("id") id: Long, model: Model) = {
    model.addAttribute("photo", photoRepository.findOne(id))
    "photos/edit"
  }

  @GetMapping(params = Array("form"))
  def createForm(model: Model) = {
    model.addAttribute("photo", new Photo())
    "photos/create"
  }

  @PostMapping
  def create(@Valid photo: Photo, bindingResult: BindingResult) =
    if (bindingResult.hasErrors) {
      "photos/create"
    } else {
      photoRepository.save(photo)
      "redirect:/photos"
    }

  @PostMapping(Array("/form"))
  def createForm(@ModelAttribute req: PhotoRequest) = {
    log.info("creating photo with request = {}", req)
    val photo = photoService
      .getPhoto(req.name, req.description, req.file.getInputStream)
    log.info("created photo = {}", photo)
    val savedPhoto = photoRepository.save(photo)
    log.info("saved photo = {}", savedPhoto)
    new ResponseEntity("Successfully created photo.", HttpStatus.OK)
  }

  @PostMapping(value = Array("/update"))
  def update(@Valid photo: Photo, bindingResult: BindingResult) =
    if (bindingResult.hasErrors) {
      "photos/edit"
    } else {
      photoRepository.save(photo)
      "redirect:/photos"
    }


  @GetMapping(value = Array("/delete/{id}"))
  def delete(@PathVariable("id") id: Long) = {
    photoRepository.delete(id)
    "redirect:/photos"
  }

}
