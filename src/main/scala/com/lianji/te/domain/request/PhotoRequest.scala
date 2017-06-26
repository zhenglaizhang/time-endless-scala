package com.lianji.te.domain.request

import scala.beans.BeanProperty

import org.springframework.web.multipart.MultipartFile

case class PhotoRequest(
  @BeanProperty var name: String,
  @BeanProperty var description: String,
  @BeanProperty var file: MultipartFile
) {
  def this() = this(null, null, null)
}
