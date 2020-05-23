package com.mtm.service.rest.auth;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Admin on 3/8/2020.
 */
public class AuthorizationHandlerFactory {

    private static Map<AuthorizationRule, AuthorizationHandler> authorizationHandlerMap = new HashMap<>();

    static
    {
        authorizationHandlerMap.put(AuthorizationRule.TRIP_CANNOT_BE_ADDED_BY_NON_OWNER,new TripAuthorizationHandler());
    }


    public synchronized static AuthorizationHandler getHandler(AuthorizationRule authorizationRule)
    {
        return authorizationHandlerMap.get(authorizationRule);
    }


}
