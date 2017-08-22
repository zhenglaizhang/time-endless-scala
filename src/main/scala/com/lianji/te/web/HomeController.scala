package com.lianji.te.web

import java.io.{File, IOException}
import java.nio.file.Files
import java.util.Optional

import com.lianji.te.domain.Category
import com.lianji.te.service.PhotoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation._
import org.springframework.web.servlet.ModelAndView

import scala.util.Try

@Controller
class HomeController {

  @Autowired
  private var photoRepository: PhotoRepository = _

  @GetMapping(Array("/"))
  def index(@RequestParam("category") category: Optional[String]) = {
    val mv = new ModelAndView("index")
    if (category.isPresent) {
      Try {
        Category.valueOf(category.get())
      }.toOption.foreach { _ =>
        mv.addObject("category", category.get())
      }
    }
    mv
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
