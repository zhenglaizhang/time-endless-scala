package com.lianji.te.web

import javax.validation.Valid

import scala.util.control.NonFatal
import scala.util.{ Failure, Success, Try }

import com.lianji.te.domain.{ UserCreateForm, UserCreateFormValidator }
import com.lianji.te.service.user.UserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation._
import org.springframework.web.servlet.ModelAndView

@Controller
class UserController @Autowired()(
  val userService: UserService,
  var userCreateFormValidator: UserCreateFormValidator
) {

  private[this] val log = LoggerFactory.getLogger(getClass)

  @InitBinder(Array("form"))
  def initBinder(binder: WebDataBinder) = binder.addValidators(userCreateFormValidator)

  @GetMapping(Array("/users"))
  def getUsersPage =
    new ModelAndView("users", "users", userService.getAllUsers)

  @GetMapping(Array("/user/{id}"))
  def getUserPage(@PathVariable id: java.lang.Long) = {
    new ModelAndView("user", "user", userService.getUserById(id).get)
  }

  @GetMapping(Array("/user/create"))
  def getUserCreatePage =
    new ModelAndView("user_create", "form", UserCreateForm())

  @PostMapping(Array("/users"))
  def createUser(
    @Valid @ModelAttribute("form") form: UserCreateForm,
    bindingResult: BindingResult
  ) = {
    if (bindingResult.hasErrors) {
      "user_create"
    } else {
      // todo do constraint check ahead of time
      Try {userService.create(form)} match {
        case Failure(NonFatal(e: DataIntegrityViolationException)) =>
          log.error("failed to create user", e)
          bindingResult.reject("user.exits", "uname or email already exits")
          "user_create"
        case Success(_) =>
          "redirect:/users"
      }
    }
  }
}
