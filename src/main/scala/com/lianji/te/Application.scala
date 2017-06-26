package com.lianji.te

import org.springframework.boot.SpringApplication


//@ComponentScan(Array("com.lianji.te.service"))
//@SpringBootApplication
object Application extends App {
  SpringApplication.run(classOf[BootConfig], args: _*)
}
