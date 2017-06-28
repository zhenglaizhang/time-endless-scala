package com.lianji.te

import java.io.File
import java.lang.Boolean
import java.util.concurrent.TimeUnit
import javax.servlet.Filter

import scala.beans.BeanProperty

import com.lianji.dbcount.DbCountRunner
import com.lianji.te.domain.BookFormatter
import com.lianji.te.service.BookRepository
import org.apache.catalina.connector.Connector
import org.apache.catalina.filters.RemoteIpFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory
import org.springframework.boot.context.embedded.{ ConfigurableEmbeddedServletContainer, EmbeddedServletContainerCustomizer }
import org.springframework.boot.context.properties.{ ConfigurationProperties, EnableConfigurationProperties }
import org.springframework.context.annotation.{ Bean, Configuration, PropertySource }
import org.springframework.data.repository.CrudRepository
import org.springframework.format.FormatterRegistry
import org.springframework.http.converter.ByteArrayHttpMessageConverter
import org.springframework.web.servlet.config.annotation.{ InterceptorRegistry, PathMatchConfigurer, ResourceHandlerRegistry,
WebMvcConfigurerAdapter }
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor

/*
 Spring Boot automatically adds OrderedCharacterEncodingFilter and HiddenHttpMethodFilter

@ComponentScan instructs Spring Boot to detect WebConfiguration as a
@Configuration class and add its definitions to the context.

Servlet Filters are a part of the Servlet API and have really nothing to do with
Spring—besides being automatically added in the filter chain

Spring Boot already exposes many properties to configure the
application settings, including a whole set of settings for the server configuration. These
values get bound to an internal Spring Boot class: ServerProperties.
 */
@PropertySource(Array("classpath:/tomcat.https.properties"))
@EnableConfigurationProperties(Array(classOf[TomcatSslConnectorProperties]))
@Configuration
class WebConfiguration extends WebMvcConfigurerAdapter {

  //  @Bean
  def servletContainer(properties: TomcatSslConnectorProperties) = {
    val tomcat = new TomcatEmbeddedServletContainerFactory()
    tomcat.addAdditionalTomcatConnectors(createSslConnector(properties))
    tomcat
  }

  private[this] def createSslConnector(properties: TomcatSslConnectorProperties) = {
    val connector = new Connector()
    properties.configureConnector(connector)
    connector
  }

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
  . This method gets invoked after all the WebMvcConfigurers get called for configureMessageConverters and the list of converters is
  fully populated
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
  override def addResourceHandlers(registry: ResourceHandlerRegistry) = {
    registry.addResourceHandler("/internal/**")
      .addResourceLocations("classpath:/")
      .setCachePeriod(1000)
    // http://localhost:8080/internal/application.properties
  }


  /*
  D uring the application startup, the Spring Boot autoconfiguration detects the presence of
the customizer and invokes the customize(…) method, passing the reference to a servlet
container. In our specific case, we actually get an instance of the
TomcatEmbeddedServletContainerFactory implementation; but depending on the kind of
servlet container that is used, such as Jetty, or Undertow, the implementation will vary
   */
  @Bean
  def embeddedServletContainerCustomizer = new EmbeddedServletContainerCustomizer {
    // set session timeout
    override def customize(container: ConfigurableEmbeddedServletContainer) =
      container.setSessionTimeout(1, TimeUnit.MINUTES)
  }

  @Bean
  def dbCountRunner(repositories: java.util.Collection[CrudRepository[_, _]]) = new DbCountRunner(repositories) {
    override def run(args: String*) = {
      log.info("Manually declared DbCountRunner")
    }
  }
}

@ConfigurationProperties(prefix = "custom.tomcat.https")
class TomcatSslConnectorProperties {
  @BeanProperty
  var port: Integer = _

  @BeanProperty
  var ssl: Boolean = true

  @BeanProperty
  var secure: Boolean = true

  @BeanProperty
  var schema: String = "https"

  @BeanProperty
  var keystore: File = _

  @BeanProperty
  var keystorePassword: String = _

  def configureConnector(connector: Connector) = {
    if (port != null) connector.setPort(port)
    if (secure != null) {connector.setSecure(secure)}
    if (schema != null) {connector.setScheme(schema)}
    if (ssl != null) {connector.setProperty("SSLEnabled", ssl.toString)}
    if (keystore != null && keystore.exists()) {
      connector.setProperty("keystoreFile", keystore.getAbsolutePath)
      connector.setProperty("keystorePassword", keystorePassword)
    }
  }
}
