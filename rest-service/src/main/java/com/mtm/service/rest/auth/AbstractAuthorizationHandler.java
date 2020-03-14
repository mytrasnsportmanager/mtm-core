package com.mtm.service.rest.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

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

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public String getHttpMethodName() {
        return httpMethodName;
    }

    public void setHttpMethodName(String httpMethodName) {
        this.httpMethodName = httpMethodName;
    }

    protected HttpServletRequest req;

    protected HttpServletResponse res;

    protected  ResourceInfo resourceInfo;

    protected UriInfo uriInfo;

    protected String resourcePath;
    protected String httpMethodName;


}
