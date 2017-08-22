package com.lianji.te.config

import com.lianji.te.domain.Category
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.{EnableWebSecurity, WebSecurityConfigurerAdapter}

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
class SecurityConfig @Autowired()() extends WebSecurityConfigurerAdapter {

  @throws[Exception]
  override protected def configure(http: HttpSecurity): Unit = {
    http.authorizeRequests()
      .antMatchers("/", "/home").permitAll()
      .anyRequest().authenticated()
    .and()
      .antMatcher("/photos")
      .httpBasic()
  }
}
