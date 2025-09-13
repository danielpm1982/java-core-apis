package com.danielpm1982.annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface VisibleMethod {}

/*
This Annotation is targeted at methods, and works, at runtime, by simply marking the methods that should be visible
or not. The implementation of how to treat that should be done by the user classes. More configurations could be
done here, for different use cases.
*/
