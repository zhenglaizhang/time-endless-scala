package com.lianji.te.repository

import java.util.Optional

import com.lianji.te.domain.User
import org.springframework.data.jpa.repository.JpaRepository

trait UserRepository extends JpaRepository[User, java.lang.Long] {
  def findOneByEmail(email: String): Optional[User]
}
