package br.com.api.ml.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = {ExistsByIdValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistsById {

	String message() default "{br.com.api.cdc.validation.ExistsById}";
	
	Class<?>[] groups() default { };
	
	Class<? extends Payload>[] payload() default { };
	
	String fieldName();
	
	Class<?> 	domainName();
}
