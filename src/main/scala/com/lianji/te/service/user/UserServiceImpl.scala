package com.lianji.te.service.user

import java.{ lang, util }

import com.lianji.te.domain.{ User, UserCreateForm }
import com.lianji.te.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

// the service proxies to the UserRepository most of the time
@Service
class UserServiceImpl @Autowired()(
  val userRepository: UserRepository
) extends UserService {

  override def getUserById(id: lang.Long): Option[User] = {
    Option(userRepository.findOne(id))
  }

  override def getUserByEmail(email: String): Option[User] = {
    Option(userRepository.findOneByEmail(email).orElse(null))
  }

  override def getAllUsers: util.List[User] = {
    userRepository.findAll()
  }

  override def create(form: UserCreateForm) = {
    val user = new User(
      id = null,
      uname = form.getUname,
      email = form.getEmail,
      passwordHash = new BCryptPasswordEncoder().encode(form.getPassword),
      role = form.getRole
    )
    userRepository.save(user)
  }
}
