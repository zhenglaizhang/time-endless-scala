package com.lianji.te.web

import java.lang
import java.lang.Long
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

import com.lianji.te.domain.request.PhotoRequest
import com.lianji.te.domain.{Category, Photo}
import com.lianji.te.service.{OssService, PhotoMetadataService, PhotoRepository}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
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


  val endpoint = "oss-ap-southeast-1.aliyuncs.com"
  val id = sys.env("OSS_KEY_ID")
  val secret = sys.env("OSS_KEY_SECRET")
  val bucket = "timendless"

  @GetMapping
  def list(model: Model) = {
    val photos = photoRepository.findAll()
    model.addAttribute("photos", photos)
    "photos/list"
  }

  @GetMapping(Array("/{category}"))
  @ResponseBody
  def getByCategory(
    @PathVariable(value = "category") category: Category
  ): lang.Iterable[Photo] = {
    val photos = photoRepository.findByCategory(category)
    photos
  }

  @GetMapping(Array("/edit/{id}"))
  def edit(@PathVariable("id") id: Long, model: Model) = {
    model.addAttribute("photo", photoRepository.findOne(id))
    "photos/edit"
  }

  @GetMapping(params = Array("form"))
  def createForm(model: Model) = {
    model.addAttribute("photo", new PhotoRequest())
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

  // TODO: exception handling 
  @PostMapping(Array("/form"))
  def createForm(@ModelAttribute @Valid req: PhotoRequest, httpServletRequest: HttpServletRequest): String = {
    log.info("creating photo with request = {}", req)
    val photo = photoService
      .getPhoto(req.category, req.name, req.description, url = "dummy", req.file.getInputStream)
    val savedPhoto = photoRepository.save(photo)
    val sizedInputStream = photoService.crapImgInputStream(req.file.getInputStream)
    val oss = new OssService(endpoint, id, secret)
    oss.setBucketPublicReadable(bucket)
    val key = s"${ req.category.name }/${ savedPhoto.id }.jpg"
    oss.uploadJpg(bucket, key, sizedInputStream)
    photo.url = s"http://$bucket.$endpoint/$key"
    photoRepository.save(photo)
//    new ResponseEntity("Successfully created photo.", HttpStatus.OK)
    "redirect:/photos"
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
