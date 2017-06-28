package com.lianji.dbcount

import java.lang.annotation._

import scala.annotation.ClassfileAnnotation

import org.springframework.context.annotation.Import

/*
@Import(DbCountAutoConfiguration.class), which makes things happen. This is an
annotation that is provided by Spring, which can be used to annotate other annotations
with declarations of which configuration classes should be imported in the process. By
annotating our BookPubApplication class with @EnableDbCounting, we transitively tell
Spring that it should include DbCountAutoConfiguration as a part of the application
context as well.
 */

@Target(Array(ElementType.TYPE))
@Retention(RetentionPolicy.RUNTIME)
@Import(Array(classOf[DbCountAutoConfiguration]))
@Documented
class EnableDbCounting extends ClassfileAnnotation
