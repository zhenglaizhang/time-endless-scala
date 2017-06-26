package com.lianji.te

import org.springframework.boot.SpringApplication
import org.springframework.context.annotation.ComponentScan


//@ComponentScan(Array("com.lianji.te.service"))
//@SpringBootApplication
object Application extends App {
  SpringApplication.run(classOf[BootConfig])
}
