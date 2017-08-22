package com.lianji.te.web

import java.io.{File, IOException}
import java.nio.file.Files
import java.util.Optional

import com.lianji.te.service.PhotoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation._

@Controller
class HomeController {

  @Autowired
  private var photoRepository: PhotoRepository = _

  @GetMapping(Array("/"))
  def index(model: Model, @RequestParam("category") category: Optional[String]) = {
    if (category.isPresent) {
      model.addAttribute("category", category.get())
    }
    "index"
  }

  @RequestMapping(value = Array("/image/{category}/{name}"))
  @ResponseBody
  @throws[IOException]
  def getImage(@PathVariable(value = "category") category: String,
               @PathVariable(value = "name") name: String) = {
    val serverFile = new File(s"/tmp/imgs/$category/$name.jpg")
    Files.readAllBytes(serverFile.toPath)
  }
}
