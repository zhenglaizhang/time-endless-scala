package com.lianji.te.domain

import javax.persistence.Id
import javax.persistence.GeneratedValue
import java.lang.Long
import java.time.LocalDate
import javax.persistence.Entity

import scala.beans.BeanProperty

import org.hibernate.validator.constraints.NotEmpty
import scala.annotation.meta.field

@Entity
class Photo(
  @(Id@field) @(GeneratedValue@field) @BeanProperty var id: Long,
  @BeanProperty @(NotEmpty@field) var name: String,
  @BeanProperty @(NotEmpty@field) var description: String,
  @BeanProperty @(NotEmpty@field) var dateTimeOriginal: LocalDate,
  @BeanProperty @(NotEmpty@field) var width: Integer,
  @BeanProperty @(NotEmpty@field) var height: Integer,
  @BeanProperty @(NotEmpty@field) var exposureTime: String,
  @BeanProperty @(NotEmpty@field) var fNumber: String,
  @BeanProperty @(NotEmpty@field) var model: String,
  @BeanProperty @(NotEmpty@field) var make: String,
  @BeanProperty @(NotEmpty@field) var copyright: String,
  @BeanProperty @(NotEmpty@field) var isoSpeedRatings: Integer,
  @BeanProperty @(NotEmpty@field) var apetureValue: String,
  @BeanProperty @(NotEmpty@field) var maxApetureValue: String,
  @BeanProperty @(NotEmpty@field) var focalLength: String,
  @BeanProperty @(NotEmpty@field) var url: String
) {
  def this() = this(
    null, null, null, null,
    null, null, null, null,
    null, null, null, null,
    null, null, null, null
  )
}
