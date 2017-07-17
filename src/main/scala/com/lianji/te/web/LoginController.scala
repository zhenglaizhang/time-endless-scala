package com.lianji.te.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{ GetMapping, RequestParam }
import org.springframework.web.servlet.ModelAndView

@Controller
class LoginController {

  @GetMapping(Array("/login"))
  def getLoginPage(
    @RequestParam error: Option[String]
  ) = new ModelAndView("login", "error", error)
}
