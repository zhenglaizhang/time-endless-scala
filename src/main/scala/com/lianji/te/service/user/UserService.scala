package com.lianji.te.service.user

import java.util

import com.lianji.te.domain.{ User, UserCreateForm }

// In service layer, where the business logic should,
trait UserService {

  def getUserById(id: java.lang.Long): Option[User]

  def getUserByEmail(email: String): Option[User]

  def getAllUsers: util.Collection[User]

  def create(form: UserCreateForm): User
}
