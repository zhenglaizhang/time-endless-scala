package com.lianji.te.service

import java.lang.Long

import com.lianji.te.domain.Photo
import org.springframework.data.repository.CrudRepository

trait PhotoRepository extends CrudRepository[Photo, Long]
