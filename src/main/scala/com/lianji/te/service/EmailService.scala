package com.lianji.te.service

import scala.beans.BeanProperty

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.{ JavaMailSender, JavaMailSenderImpl }
import org.springframework.stereotype.Service

@Service
class EmailService {

  @Autowired
  @BeanProperty
  var javaMailSender: JavaMailSender = {
    val mailSender = new JavaMailSenderImpl
    mailSender.setHost("smtp.gmail.com")
    mailSender.setPort(587)
    mailSender.setUsername("zhenglaizhang@gmail.com")
    mailSender.setPassword("Zzl@073218")
    val props = mailSender.getJavaMailProperties
    props.put("mail.transport.protocol", "smtp")
    props.put("mail.smtp.auth", "true")
    props.put("mail.smtp.starttls.enable", "true")
    props.put("mail.debug", "true")
    mailSender
  }

  def sendMail(from: String, to: String, subject: String, message: String) = {
    val mailMessage = {
      val msg = new SimpleMailMessage()
//      msg.setFrom(from)
      msg.setTo(to)
      msg.setSubject(subject)
      msg.setText(message)
      msg
    }

    javaMailSender.send(mailMessage)
  }

}
