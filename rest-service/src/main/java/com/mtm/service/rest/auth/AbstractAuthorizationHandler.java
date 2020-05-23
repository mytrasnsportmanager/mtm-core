package com.mtm.service.rest.auth;

import com.mtm.beans.UserSession;
import com.mtm.beans.UserType;
import com.mtm.service.rest.resources.AbstractRestResource;
import com.mtm.service.rest.resources.TripResource;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;

/**
 * Created by Admin on 3/14/2020.
 */
public abstract class AbstractAuthorizationHandler implements AuthorizationHandler {


    public HttpServletRequest getReq() {
        return req;
    }

    public void setReq(HttpServletRequest req) {
        this.req = req;
    }

    public HttpServletResponse getRes() {
        return res;
    }

    public void setRes(HttpServletResponse res) {
        this.res = res;
    }

    public ResourceInfo getResourceInfo() {
        return resourceInfo;
    }

    public void setResourceInfo(ResourceInfo resourceInfo) {
        this.resourceInfo = resourceInfo;
    }

    public UriInfo getUriInfo() {
        return uriInfo;
    }

    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }


    protected HttpServletRequest req;

    protected HttpServletResponse res;

    protected  ResourceInfo resourceInfo;

    protected UriInfo uriInfo;

    protected String httpMethodName;

    protected  String resourcePath;

    protected UserSession userSession;

    protected UserType userType;

    protected String jsonBody;

    protected void initialize(HttpServletRequest req, HttpServletResponse res, ResourceInfo resourceInfo, UriInfo uriInfo)
    {

        HttpSession session = req.getSession();
        userSession = (UserSession)session.getAttribute("user_session");
        userType = userSession.getUserType();
        resourcePath = uriInfo.getPath();
        httpMethodName = req.getMethod();


    }


}
