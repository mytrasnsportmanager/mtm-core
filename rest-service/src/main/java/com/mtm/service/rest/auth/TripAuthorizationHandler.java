package com.mtm.service.rest.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtm.beans.UserSession;
import com.mtm.beans.UserType;
import com.mtm.beans.dto.Trip;
import com.mtm.beans.rest.RestMethodRequest;
import com.mtm.dao.VehicleDao;
import com.mtm.service.rest.RestResourceType;
import com.mtm.service.rest.resources.AbstractRestResource;
import com.mtm.service.rest.resources.TripResource;
import com.mtm.service.rest.validation.AuthorizationCheck;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import java.io.IOException;

/**
 * Created by Admin on 3/8/2020.
 */
public class TripAuthorizationHandler extends AbstractAuthorizationHandler {

    VehicleDao vehicleDao = new VehicleDao();

    public boolean isAuthorized(AbstractRestResource abstractRestResource) {

        Trip postedTrip =null;
        HttpSession session = req.getSession();
        UserSession userSession = (UserSession)session.getAttribute("user_session");
        UserType userType = userSession.getUserType();
        TripResource tripResource = (TripResource)abstractRestResource;

     postedTrip = tripResource.getTrip();


        if(resourcePath.equalsIgnoreCase("mtm/trips") && httpMethodName.equalsIgnoreCase("GET"))
       {
           if(userType==UserType.DRIVER)
               return false;
           return true;
       }
        if(resourcePath.equalsIgnoreCase("mtm/trips") && (httpMethodName.equalsIgnoreCase("POST")))
        {
            if(userType==UserType.CONSIGNER || userType==UserType.DRIVER)
                return false;

            // Check if user is the owner for the vehicle
           // String query = "select * from vehicle where vehicleid ="+postedTrip.getVehicleid()+" and ownerid ="+userid;
            if(!userSession.getVehicleIdList().contains(postedTrip.getVehicleid()))
                return false;
            return true;


        }

        if(resourcePath.matches("mtm/trips/\\d*") && (httpMethodName.equalsIgnoreCase("PUT")))
        {

        }



        return false;
    }



    class DownloadAllTripsAuthorizer
    {
        private boolean isAuthroized(HttpMethod method, long userid, UserType userType)
        {
            if(userType==UserType.CONSIGNER || userType==UserType.OWNER||userType==UserType.DRIVER)
                return false;
            return true;
        }
    }



}
