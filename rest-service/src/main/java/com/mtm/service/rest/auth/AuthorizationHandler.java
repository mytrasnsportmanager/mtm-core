package com.mtm.service.rest.auth;

import com.mtm.beans.UserSession;
import com.mtm.beans.UserType;
import com.mtm.service.rest.resources.AbstractRestResource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.container.ResourceInfo;

/**
 * Created by Admin on 3/8/2020.
 */
public interface AuthorizationHandler {
    public boolean isAuthorized(AbstractRestResource restResource);
    public void setReq(HttpServletRequest req) ;
    public void setRes(HttpServletResponse res);
    public void setResourceInfo(ResourceInfo resourceInfo) ;
    public void setResourcePath(String resourcePath);
    public void setHttpMethodName(String httpMethodName) ;

}
