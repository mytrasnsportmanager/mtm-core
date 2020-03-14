package com.mtm.service.rest.validation;

import com.mtm.service.rest.auth.AbstractAuthorizationHandler;
import com.mtm.service.rest.auth.AbstractAuthorizer;
import com.mtm.service.rest.auth.TripAuthorizationHandler;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

/**
 * Created by Admin on 3/14/2020.
 */
@Target({ METHOD, FIELD, TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { AbstractAuthorizer.class })
public @interface  AuthorizationCheck {
    String message() default "Invalid Request";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
