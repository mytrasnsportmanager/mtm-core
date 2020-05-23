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
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;

/**
 * Created by Admin on 3/8/2020.
 */
public class TripAuthorizationHandler extends AbstractAuthorizationHandler {

    VehicleDao vehicleDao = new VehicleDao();

    @CanAuthorize(AuthorizationRule.TRIP_CANNOT_BE_ADDED_BY_NON_OWNER)
    public boolean isAuthorized(HttpServletRequest req, HttpServletResponse res, ResourceInfo resourceInfo, UriInfo uriInfo , Trip trip) {


        super.initialize(req,res,resourceInfo,uriInfo);
        Trip postedTrip =trip;

             // Check if user is the owner for the vehicle
           // String query = "select * from vehicle where vehicleid ="+postedTrip.getVehicleid()+" and ownerid ="+userid;
            if(!userSession.getVehicleIdList().contains(postedTrip.getVehicleid()))
                return false;
            return true;

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
