package com.mtm.service.rest.auth;

import com.mtm.beans.UserSession;
import com.mtm.beans.UserType;
import com.mtm.service.rest.resources.AbstractRestResource;

import javax.ws.rs.HttpMethod;

/**
 * Created by Admin on 3/8/2020.
 */
public class AllowAll extends AbstractAuthorizationHandler {


    public boolean isAuthorized(String resourceName, String method, long userid, String userType)
    {
        System.out.println(""+resourceName+","+method+","+userid+","+userType);
        return true;
    }

    @Override
    public boolean isAuthorized(AbstractRestResource restResource) {
        return false;
    }
}
