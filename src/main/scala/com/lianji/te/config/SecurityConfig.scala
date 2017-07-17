package com.lianji.te.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.SecurityProperties
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.{ EnableWebSecurity, WebSecurityConfigurerAdapter }

//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
class SecurityConfig @Autowired()() extends WebSecurityConfigurerAdapter {

//  @throws[Exception]
//  override def configure(http: HttpSecurity) = {
////    super[WebSecurityConfigurerAdapter].configure(http)
//    http.authorizeRequests()
//      .antMatchers("/", "/public/**").permitAll()
//      .antMatchers("/users/**").hasAuthority("ADMIN")
//      .anyRequest().fullyAuthenticated()
//
//
//  }
}
