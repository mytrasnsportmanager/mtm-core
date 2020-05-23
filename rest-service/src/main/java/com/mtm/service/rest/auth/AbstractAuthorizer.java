package com.mtm.service.rest.auth;

import com.google.common.base.Optional;
import com.mtm.beans.dto.Trip;
import com.mtm.service.rest.resources.AbstractRestResource;
import com.mtm.service.rest.validation.AuthorizationCheck;
import org.apache.xpath.operations.Bool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Admin on 3/14/2020.
 */
@SupportedValidationTarget(ValidationTarget.PARAMETERS)
public class AbstractAuthorizer  implements ConstraintValidator<AuthorizationCheck, Object[]> {

    private AuthorizationRule authorizationRule;

    @Context
    private HttpServletRequest req;
    @Context
    private HttpServletResponse res;
    @Context
    private ResourceInfo resourceInfo;
    @Context
    private UriInfo uriInfo;

    @Override
    public void initialize(AuthorizationCheck authorizationCheck) {
      this.authorizationRule = authorizationCheck.value();
    }

    @Override
    public boolean isValid(Object[] objects, ConstraintValidatorContext constraintValidatorContext) {

        AuthorizationHandler authorizationHandler = AuthorizationHandlerFactory.getHandler(authorizationRule);
         Method[] methods = authorizationHandler.getClass().getMethods();
         for(Method  method : methods)
         {
            if(canMethodAuthorizeForAuthorizationRule(method))
            {
                // Invoke the authorization method in the handler
                try {
                   Object validationResult =  method.invoke(authorizationHandler,objects);
                   return (boolean)(validationResult);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
         }


        /*if(paramaters.length>=1)*/



        return true;

    }

    private boolean canMethodAuthorizeForAuthorizationRule(Method method )
    {
        if(method.isAnnotationPresent(CanAuthorize.class))
        {
            CanAuthorize canAuthorize = method.getAnnotation(CanAuthorize.class);
            if(canAuthorize.value()==authorizationRule)
            return true;

        }
        return false;
    }
}
