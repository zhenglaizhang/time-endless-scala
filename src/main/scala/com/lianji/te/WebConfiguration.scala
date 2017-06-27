package com.lianji.te

import javax.servlet.Filter

import com.lianji.te.domain.BookFormatter
import com.lianji.te.service.BookRepository
import org.apache.catalina.filters.RemoteIpFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{ Bean, Configuration }
import org.springframework.format.FormatterRegistry
import org.springframework.http.converter.ByteArrayHttpMessageConverter
import org.springframework.web.servlet.config.annotation.{ InterceptorRegistry, PathMatchConfigurer, ResourceHandlerRegistry, WebMvcConfigurerAdapter }
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


  /*
   Spring Boot automatically configured HttpMessageConverters to translate our
entity beans objects into a JSON representation using Jackson library, writing the resulting
JSON data to an HTTP response output stream. When multiple converters are available,
the most applicable one gets selected based on the message object class and the requested
content type.

If Spring detects a bean of the HttpMessageConverter type, it will add it
to the list automatically.
   */
  @Bean
  def byteArrayHttpMessageConverter: ByteArrayHttpMessageConverter = new ByteArrayHttpMessageConverter

  // same as above
  /*
there is no guarantee that our method can get called in any particular order
   */
//  override def configureMessageConverters(converters: util.List[HttpMessageConverter[_]]) =
//    converters.add(new ByteArrayHttpMessageConverter)

  /*
  . This method gets invoked after all the WebMvcConfigurers get called for configureMessageConverters and the list of converters is fully populated
   */
  // more control
//  override def extendMessageConverters(converters: util.List[HttpMessageConverter[_]]) = {
//    converters.clear()
//    converters.add(new ByteArrayHttpMessageConverter)
//  }


  @Autowired
  private var bookRepository: BookRepository = _

  override def addFormatters(registry: FormatterRegistry) = {
    registry.addFormatter(new BookFormatter(bookRepository))
  }


  override def configurePathMatch(configurer: PathMatchConfigurer) = {
    configurer.setUseSuffixPatternMatch(false) // not use the suffix pattern match of .*
      .setUseTrailingSlashMatch(true)          // not to strip the values after the dot when parsing the parameters
    // support: http://localhost:8080/books/978-1-78528-415-1.1/reviewers
  }

  /*
  define custom mappings for static resource URLs and connect them with the resources on the file system or application classpath
   */
  override def addResourceHandlers(registry: ResourceHandlerRegistry) =  {
    registry.addResourceHandler("/internal/**")
      .addResourceLocations("classpath:/")
      .setCachePeriod(1000)
    // http://localhost:8080/internal/application.properties
  }
}
