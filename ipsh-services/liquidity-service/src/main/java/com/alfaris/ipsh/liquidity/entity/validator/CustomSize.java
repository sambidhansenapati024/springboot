package com.alfaris.ipsh.liquidity.entity.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.nimbusds.jose.Payload;

import jakarta.validation.Constraint;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = CustomSizeValidator.class)
public @interface CustomSize {
	String message() default "Value can't be empty";
	Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
	String maxKey() default "1";
	String minKey() default "100";
}
