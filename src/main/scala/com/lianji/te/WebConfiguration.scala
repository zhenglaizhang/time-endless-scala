package com.lianji.te

import javax.servlet.Filter

import org.apache.catalina.filters.RemoteIpFilter
import org.springframework.context.annotation.{ Bean, Configuration }

/*
 Spring Boot automatically adds OrderedCharacterEncodingFilter and HiddenHttpMethodFilter

@ComponentScan instructs Spring Boot to detect WebConfiguration as a
@Configuration class and add its definitions to the context.

Servlet Filters are a part of the Servlet API and have really nothing to do with
Spring—besides being automatically added in the filter chain
 */
@Configuration
class WebConfiguration {

  // When Spring Boot detects all the beans of javax.servlet.Filter, it will add them to the filter chain automatically
  @Bean
  def remoteIpFilter: Filter = new RemoteIpFilter
}
