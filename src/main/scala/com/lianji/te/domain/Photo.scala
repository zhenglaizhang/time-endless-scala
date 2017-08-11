package com.lianji.te.domain

import java.lang.Long
import java.time.LocalDate
import javax.persistence._

import scala.annotation.meta.field
import scala.beans.BeanProperty

import org.hibernate.validator.constraints.NotEmpty

// JPA Entity (domain object)
@Entity
case class Photo(
  @BeanProperty @(Id@field) @(GeneratedValue@field) var id: Long = null,
  @BeanProperty @Enumerated(EnumType.ORDINAL) var category: Category = null,
  @BeanProperty @(NotEmpty@field) var name: String = null,
  @BeanProperty @(NotEmpty@field) var description: String = null,
  @BeanProperty var dateTimeOriginal: LocalDate = null,
  @BeanProperty var width: Integer = null,
  @BeanProperty var height: Integer = null,
  @BeanProperty @(NotEmpty@field) var exposureTime: String = null,
  @BeanProperty @(NotEmpty@field) var meteringMode: String = null,
  @BeanProperty @(NotEmpty@field) var exposureProgram: String = null,
  @BeanProperty @(NotEmpty@field) var fNumber: String = null,
  @BeanProperty @(NotEmpty@field) var model: String = null,
  @BeanProperty @(NotEmpty@field) var make: String = null,
  @BeanProperty @(NotEmpty@field) var copyright: String = null,
  @BeanProperty var isoSpeedRatings: Integer = null,
  @BeanProperty @(NotEmpty@field) var apertureValue: String = null,
  @BeanProperty @(NotEmpty@field) var maxApertureValue: String = null,
  @BeanProperty @(NotEmpty@field) var focalLength: String = null,
  @BeanProperty @(NotEmpty@field) var url: String = null,//,
  @BeanProperty @(NotEmpty@field) var url_index: String = null//,
//  @BeanProperty @(NotEmpty@field) @OneToOne var album: Album
) {
  // required ctor.
  def this() = this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null)

  // convenient ctors...
}
