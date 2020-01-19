package com.mtm.service.rest.resources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 10/11/2019.
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class AuthorizationRequestFilter implements ContainerRequestFilter {

    @Context HttpServletRequest req;
    @Context  HttpServletResponse res;
    private static List<String> noAuthCheckURIList = new ArrayList<String>();


    static
    {
        noAuthCheckURIList.add("mtm/login");
        noAuthCheckURIList.add("mtm/users");
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
        javax.servlet.http.Cookie[] cookies = req.getCookies();
        if(cookies!=null) {
            for (javax.servlet.http.Cookie cookie : cookies) {
                System.out.println("Cookie Name " + cookie.getName() + ", value " + cookie.getValue());
            }
        }
        Object usercontact = session.getAttribute("usercontact");
        if (usercontact!=null) {
            //CookieParam cookieParam = session.get
            System.out.println(usercontact.toString());
        }
        else if (isAuthTobeBypassed(path))
        {
            //Do nothing

        }


        else {

            System.out.println("no session found");
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

}

