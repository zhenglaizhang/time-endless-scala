package com.lianji.te.domain

import javax.persistence._

import scala.annotation.meta.field
import scala.beans.BeanProperty

@Entity
class User(
//  @BeanProperty
//  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  @Column(name = "id", nullable = false, updatable = false)
//  var id: java.lang.Long,
//
  @BeanProperty @(Id@field) @(GeneratedValue@field) var id: java.lang.Long,

  @BeanProperty
  @Column(name = "uname", nullable = false, unique = true)
  var uname: String,

  @BeanProperty
  @Column(name = "email", nullable = false, unique = true)
  var email: String,

  @BeanProperty
  @Column(name = "password_hash", nullable = false)
  var passwordHash: String,

  @BeanProperty
  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false)
  var role: Role
) {
  def this() = this(null, null, null, null, null)
}
