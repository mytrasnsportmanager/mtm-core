package com.mtm.service.rest.resources;

import com.google.common.base.Optional;
import com.mtm.beans.Status;
import com.mtm.beans.UserSession;
import com.mtm.beans.UserType;
import com.mtm.beans.dto.OwnerConsigner;
import com.mtm.beans.dto.TripDetail;
import com.mtm.beans.dto.User;
import com.mtm.beans.dto.Vehicle;
import com.mtm.dao.*;
import org.apache.commons.codec.digest.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Admin on 7/15/2019.
 */
@Produces(MediaType.APPLICATION_JSON)

public class LoginResource extends AbstractRestResource {

    @Context
    HttpServletRequest req;
    @Context
    HttpServletResponse res;
    private static LoginDao loginDao = new LoginDao();
    private static UserDao userDao = new UserDao();


    public LoginResource() {
        super(loginDao);
    }


    @GET
    @Path("/logindummy")
    public Object login() {
        Status status = new Status();
        HttpSession session= req.getSession(true);
        UserSession userSession = new UserSession();
        userSession.setId(1);
        userSession.setUserType(UserType.OWNER);
        session.setAttribute("user_session",userSession);
        res.setHeader("userid","1");
        status.setReturnCode(0);
        status.setMessage("SUCCESS");
        return status;

    }

    @POST
    @Path("/logout")
    public Object logout() {
        HttpSession session= req.getSession();
        if(session!=null)
            session.invalidate();
        Status status = new Status();
        status.setMessage("SUCCESS");
        status.setReturnCode(0);
        return status;
    }


        @POST
    @Path("/login")
    public Object login(User user)
    {
       Status status = new Status();


        List<Object> userDBRecords = userDao.getRecords("contact = "+user.getContact());
        if(userDBRecords.size()!=1)
        {
            status.setReturnCode(1);
            status.setMessage("LOGIN_FAILURE");
            return status;
        }
        User userDBRecord = (User)userDBRecords.get(0);
       if(userDBRecord.getPassphrase()!=null && userDBRecord.getPassphrase().equals(DigestUtils.sha1Hex(user.getPassphrase())))

        {
            HttpSession session= req.getSession(true);

            UserSession userSession = populateSession(userDBRecord.getUserid(),UserType.valueOf(userDBRecord.getUsertype()));
            session.setAttribute("user_session",userSession);
            res.setHeader("userid",""+user.getUserid());
            status.setReturnCode(0);
            // Update device_id
            userDBRecord.setDevice_id(user.getDevice_id());
            userDao.patch(userDBRecord,"usertype='"+userDBRecord.getUsertype()+"'");


            status.setMessage("SUCCESS");
        }
        else
       {
           HttpSession session= req.getSession();
           if(session!=null)
               session.invalidate();
           status.setReturnCode(1);
           status.setMessage("LOGIN_FAILURE");

       }

        return status;
    }



    @GET
    @Path("/users/{contact}")
    public Object getUserByContact(@PathParam("contact") Optional<String> contactNumber)
    {
        long contact = Long.parseLong(contactNumber.get());
        String userQuery = "select usertype, userid from user where registered_by <> 'OWNER' and contact = "+contact;
        List<List<String>> records = loginDao.executeQuery(userQuery);
        User user = new User(UserType.NONE);
        if(records!=null && records.size() >0 )
        {
            String userType = records.get(0).get(0);
            user.setUserid(Long.parseLong(records.get(0).get(1)));
            if(userType.equalsIgnoreCase(UserType.OWNER.toString())) {
                 user.setUsertype(UserType.OWNER.toString());
                             }
            else if (userType.equalsIgnoreCase(UserType.CONSIGNER.toString()))
            {
                user.setUsertype(UserType.CONSIGNER.toString());
            }
            else if(userType.equalsIgnoreCase(UserType.DRIVER.toString()))
            {
                user.setUsertype(UserType.DRIVER.toString());
            }

        }
        return user;

    }


    public List<User> getPaginatedRecords(@QueryParam("where") Optional<String> whereClause, @QueryParam("min") Optional<String> min, @QueryParam("max") Optional<String> max, @QueryParam("recordsPerPage") Optional<String> recordsPerPage) {
        return null;
    }

    private UserSession populateSession(long userid, UserType userType)
    {
        UserSession userSession = new UserSession();
        userSession.setId(userid);
        userSession.setUserType(userType);
        switch (userType)
        {

            case OWNER:
                populateForOwner(userSession,userid);
                break;
            case CONSIGNER:
                populateForConsigner(userSession,userid);
                break;
            case DRIVER:
                populateForDriver(userSession,userid);
                break;
            case NONE:
                break;
        }
        return userSession;
    }


    private void populateForOwner(UserSession userSession , long ownerid)
    {
        VehicleDao vehicleDao = new VehicleDao();
        OwnerConsignerDao ownerConsignerDao = new OwnerConsignerDao();
        List<Object> vehicleList = vehicleDao.getRecords(" ownerid = "+ownerid);
        List<Object> associatedConsigners = ownerConsignerDao.getRecords(" ownerid = "+ownerid);

        for(Object obj : vehicleList)
        {
            userSession.getVehicleIdList().add(((Vehicle)obj).getVehicleid());
            userSession.getAssociatedDriversList().add(((Vehicle)obj).getDriverid());
        }

        for(Object obj : associatedConsigners)
        {
            userSession.getAssociatedConsigersList().add(((OwnerConsigner)obj).getConsignerid());
        }

    }

    private void populateForConsigner(UserSession userSession , long consignerid)
    {
        VehicleDao vehicleDao = new VehicleDao();
        OwnerConsignerDao ownerConsignerDao = new OwnerConsignerDao();
        String consignerAssociatedVehicles ="select distinct vehicleid from trip where routeid in (select distinct routeid from route where consignerid = "+consignerid+")";
        List<List<String>> vehicleListRecords = vehicleDao.executeQuery(consignerAssociatedVehicles);
        List<Object> associatedOwners = ownerConsignerDao.getRecords(" consignerid = "+consignerid);

        for(List<String> vehicleIdRecord : vehicleListRecords)
        {
            userSession.getVehicleIdList().add(Long.parseLong(vehicleIdRecord.get(0)));
        }

        for(Object obj : associatedOwners)
        {
            userSession.getAssociatedConsigersList().add(((OwnerConsigner)obj).getOwnerid());
        }

    }

    private void populateForDriver(UserSession userSession , long driverid)
    {
        VehicleDao vehicleDao = new VehicleDao();
        List<Object> vehicleList = vehicleDao.getRecords(" driverid = "+driverid);
        for(Object obj : vehicleList)
        {
            userSession.getVehicleIdList().add(((Vehicle)obj).getVehicleid());
            userSession.getAssociatedOwnerList().add(((Vehicle)obj).getOwnerid());
        }
    }




}
