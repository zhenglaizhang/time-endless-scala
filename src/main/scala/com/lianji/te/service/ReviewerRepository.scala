package com.lianji.te.service

import java.lang

import com.lianji.te.domain.Reviewer
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource

//@RepositoryRestResource(collectionResourceRel = "reviewers", path = "reviewers")
@RepositoryRestResource
trait ReviewerRepository extends PagingAndSortingRepository[Reviewer, lang.Long]

