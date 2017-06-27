package com.lianji.te.service

import java.lang

import com.lianji.te.domain.Author
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource
trait AuthorRepository extends PagingAndSortingRepository[Author, lang.Long]

