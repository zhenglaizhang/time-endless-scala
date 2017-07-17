package com.lianji.te.service

import com.lianji.te.domain.Publisher
import java.lang
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource
trait PublisherRepository extends PagingAndSortingRepository[Publisher, lang.Long]
