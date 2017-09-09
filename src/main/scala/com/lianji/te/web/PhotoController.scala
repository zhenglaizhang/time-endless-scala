package com.lianji.te.web

import java.lang
import java.lang.Long
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

import com.lianji.te.domain.request.PhotoRequest
import com.lianji.te.domain.{Category, Pager, Photo}
import com.lianji.te.service.{OssService, PhotoMetadataService, PhotoRepository, PhotoService}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mobile.device.Device
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation._

@Controller
@RequestMapping(Array("/photos"))
class PhotoController @Autowired()(
                                    private val photoRepository: PhotoRepository,
                                    // TODO: auto bean injection!
                                    private val photoMetaService: PhotoMetadataService = new PhotoMetadataService,
                                    val photoService: PhotoService
                                  ) {
  private[this] val log = LoggerFactory.getLogger(getClass)

  private[this] val BUTTONS_TO_SHOW = 5
  private[this] val INITIAL_PAGE = 0
  private[this] val INITIAL_PAGE_SIZE = 5
  private[this] val PAGE_SIZES = Array(5, 10, 20)

  val endpoint = "oss-ap-southeast-1.aliyuncs.com"
  val id = sys.env("OSS_KEY_ID")
  val secret = sys.env("OSS_KEY_SECRET")
  val bucket = "timendless"

  import java.util.Optional

  import org.springframework.data.domain.PageRequest
  import org.springframework.web.bind.annotation.RequestParam
  import org.springframework.web.servlet.ModelAndView

  @ResponseBody
  @RequestMapping(Array("/detect-device"))
  def detectDevice(device: Device): String = {
    if (device.isMobile) "mobile"
    else if (device.isTablet) "tablet"
    else if (device.isNormal) "normal"
    else "unknown"
  }

  @GetMapping(Array("/view"))
  def showPersonsPage(
                       @RequestParam("category") category: Optional[String],
                       @RequestParam("pageSize") pageSize: Optional[Integer],
                       @RequestParam("page") page: Optional[Integer]
                     ) = {
    val modelAndView = new ModelAndView("photos")
    val evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE)
    val evalPage = if (page.orElse(0) < 1) INITIAL_PAGE else page.get - 1
    val photos = if (category.isPresent && category.get() != "all") {
      photoService.findAllPageable(new PageRequest(evalPage, evalPageSize), Category.valueOf(category.get()))
    } else {
      photoService.findAllPageable(new PageRequest(evalPage, evalPageSize))
    }
    val pager = new Pager(photos.getTotalPages, photos.getNumber, BUTTONS_TO_SHOW)
    println(s"$pager")
    modelAndView.addObject("photos", photos)
    modelAndView.addObject("selectedPageSize", evalPageSize)
    modelAndView.addObject("pageSizes", PAGE_SIZES)
    modelAndView.addObject("pager", pager)
    modelAndView.addObject("category", category.orElse("all"))
    modelAndView
  }

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

    if (photoRepository.findByName(req.getName).iterator().hasNext) {
      return "redirect:/photos";
    }


    val photo = photoMetaService
      .getPhoto(req.category, req.name, req.description, url = "dummy", req.file.getInputStream)
    val oss = new OssService(endpoint, id, secret)
    oss.setBucketPublicReadable(bucket)
    val savedPhoto = photoRepository.save(photo)

    //    val imgOnlyInputStream = photoMetaService.removeExif(req.file.getInputStream)

    // pc img
    val sizedInputStream = photoMetaService.crapImgInputStream(req.file.getInputStream, 1920)
    val key = s"${req.category.get(0).name}/${savedPhoto.name}.jpg"
    oss.uploadJpg(bucket, key, sizedInputStream)

    // pc index
    val key_index = s"${req.category.get(0).name}/${savedPhoto.name}_index.jpg"
    val sizedIndex = photoMetaService.crapImgInputStream(req.index.getInputStream, 720)
    oss.uploadJpg(bucket, key_index, sizedIndex)

    // mobile img
    val sizedInputStreamMobile = photoMetaService.crapImgInputStream(req.file.getInputStream, 960)
    val keyMobile = s"${req.category.get(0).name}/mobile/${savedPhoto.name}.jpg"
    oss.uploadJpg(bucket, keyMobile, sizedInputStreamMobile)

    // mobile index
    val keyIndexMobile = s"${req.category.get(0).name}/mobile/${savedPhoto.name}_index.jpg"
    val sizedIndexMobile = photoMetaService.crapImgInputStream(req.index.getInputStream, 480)
    oss.uploadJpg(bucket, keyIndexMobile, sizedIndexMobile)

    photo.url = s"http://$bucket.$endpoint/$key"
    photo.url_index = s"http://$bucket.$endpoint/$key_index"
    photo.url_mobile = s"http://$bucket.$endpoint/$keyMobile"
    photo.url_mobile_index = s"http://$bucket.$endpoint/$keyIndexMobile"
    photoRepository.save(photo)
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
