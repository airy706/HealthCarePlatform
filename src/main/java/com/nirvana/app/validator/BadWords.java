package com.nirvana.app.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Retention(RetentionPolicy.RUNTIME)
@Target(value={FIELD,METHOD,CONSTRUCTOR,ANNOTATION_TYPE,PARAMETER})
@Documented
@Constraint(validatedBy={BadWordsValidator.class})
public @interface BadWords {
	String message() default "请不要包含不文明的词汇";
	
	Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
	
}
