package com.lianji.te.domain

import com.lianji.te.service.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.validation.{ Errors, Validator }

@Component
class UserCreateFormValidator @Autowired()(
  val userService: UserService
) extends Validator {

  override def supports(clazz: Class[_]) = clazz.equals(classOf[UserCreateForm])

  def validatePassword(errors: Errors, form: UserCreateForm) = {
    if (form.getPassword != form.getPasswordRepeated) {
      errors.reject("password.no_match", "Passwords do not match")
    }
  }

  def validateEmail(errors: Errors, form: UserCreateForm) = {
    if (userService.getUserByEmail(form.getEmail).isDefined) {
      errors.reject("email.exits", "User with this email already exits")
    }
  }

  override def validate(target: scala.Any, errors: Errors) = {
    val form = target.asInstanceOf[UserCreateForm]
    validatePassword(errors, form)
    validateEmail(errors, form)
  }
}
