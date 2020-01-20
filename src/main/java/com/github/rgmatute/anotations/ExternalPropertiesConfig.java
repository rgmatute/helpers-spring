package com.github.rgmatute.anotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.rgmatute.ExternalProperties;

/**
 * Source code: https://github.com/rgmatute
 * 
 * @author Ronny Matute
 */
@Retention(RetentionPolicy.RUNTIME)
//@Retention(RetentionPolicy.SOURCE)

@Target(ElementType.TYPE)
public @interface ExternalPropertiesConfig {
	String value() default "";

		String pathFile() default "";

		boolean autorefresh() default false;

		Class<?> classHandler() default ExternalProperties .class;

		String methodHandler() default "";

}
