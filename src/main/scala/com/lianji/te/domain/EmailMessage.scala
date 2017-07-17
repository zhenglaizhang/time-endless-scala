package com.lianji.te.domain

import javax.persistence.{ Column, Entity, GeneratedValue, Id }

import scala.annotation.meta.field
import scala.beans.BeanProperty

@Entity
case class EmailMessage(
  @BeanProperty @(Id@field) @(GeneratedValue@field) var id: java.lang.Long,
  @BeanProperty var name: String,
  @BeanProperty var email: String,
  @BeanProperty var subject: String,
  @BeanProperty var message: String
) {
  // TODO: better way to simulate this?
  def this() = this(null, null, null, null, null)
}
