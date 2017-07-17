package com.lianji.te.domain


import javax.persistence._

import scala.annotation.meta.field
import scala.beans.BeanProperty

import org.springframework.beans.factory.annotation.Autowired

@Entity
class Album @Autowired()(
  @BeanProperty @(Id@field) @(GeneratedValue@field) var id: java.lang.Long,
  @BeanProperty var name: String,
  @BeanProperty var description: String
//  @BeanProperty @OneToMany var photos: java.util.List[Photo]
) {
  def this() = this(null, null, null)
}
