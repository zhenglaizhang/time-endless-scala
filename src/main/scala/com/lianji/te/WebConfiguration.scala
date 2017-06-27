package com.lianji.te

import javax.servlet.Filter

import org.apache.catalina.filters.RemoteIpFilter
import org.springframework.context.annotation.{ Bean, Configuration }
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.config.annotation.{ InterceptorRegistry, WebMvcConfigurerAdapter }
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor

/*
 Spring Boot automatically adds OrderedCharacterEncodingFilter and HiddenHttpMethodFilter

@ComponentScan instructs Spring Boot to detect WebConfiguration as a
@Configuration class and add its definitions to the context.

Servlet Filters are a part of the Servlet API and have really nothing to do with
Spring—besides being automatically added in the filter chain
 */
@Configuration
class WebConfiguration extends WebMvcConfigurerAdapter {

  // When Spring Boot detects all the beans of javax.servlet.Filter, it will add them to the filter chain automatically
  @Bean
  def remoteIpFilter: Filter = new RemoteIpFilter

  @Bean
  def localeChangeInterceptor: LocaleChangeInterceptor = new LocaleChangeInterceptor

  /*
During the MVC autoconfiguration phase, Spring Boot, just as in the case of
Filters, detects instances of WebMvcConfigurer and sequentially calls the callback methods
on all of them. It means that we can have more than one implementation of the
WebMvcConfigurer class if we want to have some logical separation.
   */
  override def addInterceptors(registry: InterceptorRegistry) = {
    super.addInterceptors(registry)
    registry.addInterceptor(localeChangeInterceptor)
  }
}