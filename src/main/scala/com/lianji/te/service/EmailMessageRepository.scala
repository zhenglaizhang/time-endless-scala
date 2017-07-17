package com.lianji.te.service

import com.lianji.te.domain.EmailMessage
import org.springframework.data.repository.CrudRepository

trait EmailMessageRepository extends CrudRepository[EmailMessage, java.lang.Long]

