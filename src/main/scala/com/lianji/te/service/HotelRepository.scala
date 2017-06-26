package com.lianji.te.service

import org.springframework.data.repository.CrudRepository
import java.lang.Long

import com.lianji.te.domain.Hotel

trait HotelRepository extends CrudRepository[Hotel, Long]
