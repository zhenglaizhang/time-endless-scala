package com.lianji.te.domain.request

import javax.persistence.{ EnumType, Enumerated }

import scala.beans.BeanProperty

import com.lianji.te.domain.Category
import org.springframework.web.multipart.MultipartFile

case class PhotoRequest(
  @BeanProperty @Enumerated(EnumType.ORDINAL) var category: Category,
  @BeanProperty var name: String,
  @BeanProperty var description: String,
  @BeanProperty var index: MultipartFile,
  @BeanProperty var file: MultipartFile
) {
  def this() = this(null, null, null, null, null)
}
