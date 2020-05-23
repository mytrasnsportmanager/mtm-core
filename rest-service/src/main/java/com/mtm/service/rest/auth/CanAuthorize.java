package com.mtm.service.rest.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Admin on 3/8/2020.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CanAuthorize {

    public AuthorizationRule value() default AuthorizationRule.ALLOW_ALL;


}
