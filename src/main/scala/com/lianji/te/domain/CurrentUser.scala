package com.lianji.te.domain

import org.springframework.security.core.authority.AuthorityUtils

class CurrentUser(
  user: User
) extends org.springframework.security.core.userdetails.User(
  user.getEmail, user.getPasswordHash, AuthorityUtils.createAuthorityList(user.getRole.toString)
) {

  def getUser = user

  def getId = user.getId

  def getRole = user.role

  override def toString = {
    s"CurrentUser{user=$user} ${super.toString}"
  }
}
