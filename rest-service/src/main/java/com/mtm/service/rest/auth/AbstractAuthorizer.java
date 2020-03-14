package com.mtm.service.rest.auth;

import com.mtm.service.rest.resources.AbstractRestResource;
import com.mtm.service.rest.validation.AuthorizationCheck;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Admin on 3/14/2020.
 */
public class AbstractAuthorizer  implements ConstraintValidator<AuthorizationCheck, AbstractRestResource> {


    @Override
    public void initialize(AuthorizationCheck authorizationCheck) {

    }

    @Override
    public boolean isValid(AbstractRestResource restResource, ConstraintValidatorContext constraintValidatorContext) {

        AuthorizationHandler authorizationHandler = restResource.getAuthorizationHandler();
        return restResource.getAuthorizationHandler().isAuthorized(restResource);

    }
}
