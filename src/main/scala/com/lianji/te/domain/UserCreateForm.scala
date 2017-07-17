package com.lianji.te.domain

import scala.beans.BeanProperty

import org.hibernate.validator.constraints.NotEmpty

// DTO (data transfer object) between web layer & service layer
case class UserCreateForm(
  @BeanProperty
  @NotEmpty
  var uname: String = "",
  @BeanProperty
  @NotEmpty // hibernate validator constraints
  var email: String = "",
  @BeanProperty
  @NotEmpty
  var password: String = "",
  @BeanProperty
  @NotEmpty
  var passwordRepeated: String = "",
  @BeanProperty
  @NotEmpty
  var role: Role = Role.USER
)
