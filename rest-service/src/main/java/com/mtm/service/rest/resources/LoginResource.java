package com.mtm.service.rest.resources;

import com.google.common.base.Optional;
import com.mtm.beans.Status;
import com.mtm.beans.UserType;
import com.mtm.beans.dto.TripDetail;
import com.mtm.beans.dto.User;
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
        session.setAttribute("userid","1");
        session.setAttribute("type","OWNER");
        res.setHeader("userid","1");
        status.setReturnCode(0);
        status.setMessage("SUCCESS");
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
            session.setAttribute("userid",user.getUserid());
            session.setAttribute("type",user.getUsertype());
            res.setHeader("userid",""+user.getUserid());
            status.setReturnCode(0);
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
        String userQuery = "select usertype, userid from user where contact = "+contact;
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
}
