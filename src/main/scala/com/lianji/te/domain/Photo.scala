package com.lianji.te.domain

import java.lang.Long
import java.time.LocalDate
import javax.persistence.{ Entity, GeneratedValue, Id }

import scala.annotation.meta.field
import scala.beans.BeanProperty

import org.hibernate.validator.constraints.NotEmpty

@Entity
case class Photo(
  @(Id@field) @(GeneratedValue@field) @BeanProperty var id: Long,
  @BeanProperty @(NotEmpty@field) var name: String,
  @BeanProperty @(NotEmpty@field) var description: String,
  @BeanProperty var dateTimeOriginal: LocalDate,
  @BeanProperty var width: Integer,
  @BeanProperty var height: Integer,
  @BeanProperty @(NotEmpty@field) var exposureTime: String,
  @BeanProperty @(NotEmpty@field) var fNumber: String,
  @BeanProperty @(NotEmpty@field) var model: String,
  @BeanProperty @(NotEmpty@field) var make: String,
  @BeanProperty @(NotEmpty@field) var copyright: String,
  @BeanProperty var isoSpeedRatings: Integer,
  @BeanProperty @(NotEmpty@field) var apertureValue: String,
  @BeanProperty @(NotEmpty@field) var maxApertureValue: String,
  @BeanProperty @(NotEmpty@field) var focalLength: String,
  @BeanProperty @(NotEmpty@field) var url: String
) {
  def this() = this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)
}
