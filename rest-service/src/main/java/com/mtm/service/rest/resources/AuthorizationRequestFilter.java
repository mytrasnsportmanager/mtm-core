package com.mtm.service.rest.resources;

import com.mtm.beans.UserSession;
import com.mtm.service.rest.auth.AuthorizationHandler;
import com.mtm.service.rest.auth.CanAuthorize;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.Provider;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 10/11/2019.
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class AuthorizationRequestFilter implements ContainerRequestFilter {

    @Context HttpServletRequest req;
    @Context  HttpServletResponse res;
    @Context ResourceInfo resourceInfo;
    private static List<String> noAuthCheckURIList = new ArrayList<String>();

    static
    {
        noAuthCheckURIList.add("mtm/login");
        noAuthCheckURIList.add("mtm/users");
        noAuthCheckURIList.add("mtm/privacy_policy");
        noAuthCheckURIList.add("mtm/credits");
        noAuthCheckURIList.add("mtm/version");
       // noAuthCheckURIList.add("mtm/vehicles");
       // noAuthCheckURIList.add("mtm/vehicles");
    }

    public void filter(ContainerRequestContext containerRequest)
            throws WebApplicationException {

       /* Map<String,Cookie> cookies = containerRequest
                .getCookies();
        for(Map.Entry<String, Cookie> cookieEntry : cookies.entrySet())
        {
            String key = cookieEntry.getKey();
            Cookie cookie = cookieEntry.getValue();
            System.out.println(" Cookie Name , "+key+" , value "+cookie.getValue());
        }*/



        UriInfo uriInfo = containerRequest.getUriInfo();
        String path = uriInfo.getPath();
        System.out.println("The path is "+path);
        HttpSession session= req.getSession(true);
        //session.setMaxInactiveInterval(100);
        javax.servlet.http.Cookie[] cookies = req.getCookies();
        if(cookies!=null) {
            for (javax.servlet.http.Cookie cookie : cookies) {
               // System.out.println("Cookie Name " + cookie.getName() + ", value " + cookie.getValue());
            }
        }
        Object userSession = session.getAttribute("user_session");
        if (userSession!=null && isAuthorized(containerRequest,path)) {

            // Let it pass
        }
        else if (isAuthTobeBypassed(path))
        {
            //Do nothing

        }

        else {

            //System.out.println("no session found");
            res.setStatus(401);
            String msg = String.format("Unauthorized");
            CacheControl cc = new CacheControl();
            cc.setNoStore(true);
            Response response = Response.status(Response.Status.UNAUTHORIZED)
                    .cacheControl(cc)
                    .entity(msg)
                    .build();
            containerRequest.abortWith(response);
        }
    }


    private boolean isAuthTobeBypassed(String path)
    {
        for(String bypassedPath : noAuthCheckURIList)
        {
            if(path.contains(bypassedPath))
                return true;
        }
        return false;
    }



    private boolean isAuthorized(ContainerRequestContext containerRequest, String path)
    {

        if(true)
            return true;

        Method method = resourceInfo.getResourceMethod();
        String jsonBody = null;
       /* if(!method.isAnnotationPresent(CanAuthorize.class))
            return true;
        Class authoirzationHandlerClass = method.getAnnotation(CanAuthorize.class).value();

        try {
            AuthorizationHandler authorizationHandler = (AuthorizationHandler)authoirzationHandlerClass.newInstance();
            HttpSession httpSession = req.getSession();
            UserSession userSession = (UserSession)(httpSession.getAttribute("user_session"));
                    //System.out.println(req.get);
            if(containerRequest.getMethod().equalsIgnoreCase("POST") || containerRequest.getMethod().equalsIgnoreCase("PATCH")) {

                jsonBody = IOUtils.toString(containerRequest.getEntityStream(),"UTF8");
            }


            String queryString = req.getQueryString();


          // return authorizationHandler.isAuthorized(path,containerRequest.getMethod(),userSession,queryString,jsonBody);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }*/

        return true;


    }



}


