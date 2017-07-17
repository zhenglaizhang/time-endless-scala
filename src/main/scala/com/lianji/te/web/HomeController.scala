package com.lianji.te.web

import java.io.{ File, IOException }
import java.nio.file.Files

import com.lianji.te.service.PhotoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.{ GetMapping, PathVariable, RequestMapping, ResponseBody }

@Controller
class HomeController {

//  @RequestMapping(Array("/"))
//  def handleRootRequest(): String = "redirect:/hotels"

  @Autowired
  private var photoRepository: PhotoRepository = _

  @GetMapping(Array("/"))
  def index(model: Model) = {
    model.addAttribute("photos", photoRepository.findAll())
    "index"
  }

  @RequestMapping(value = Array("/image/{category}/{name}"))
  @ResponseBody
  @throws[IOException]
  def getImage(
    @PathVariable(value = "category") category: String,
    @PathVariable(value = "name") name: String
  ) = {
    val serverFile = new File(s"/tmp/imgs/$category/$name.jpg")
    Files.readAllBytes(serverFile.toPath)
  }
}
