package com.mtm.service.rest.resources;

import com.google.common.base.Optional;
import com.mtm.beans.Status;
import com.mtm.beans.dto.Owner;
import com.mtm.beans.dto.Trip;
import com.mtm.beans.dto.TripDetail;
import com.mtm.beans.dto.User;
import com.mtm.dao.TripDao;
import com.mtm.dao.UserDao;
import io.dropwizard.jersey.PATCH;
import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 9/15/2019.
 */
public class UserResource extends  AbstractRestResource {
   // private static final String privacyPolicyHTMLLOcation = "C:/prj/mtm/privacy_policy.html";
    private static final String privacyPolicyHTMLLOcation = "/home/mtmuser/proj/deployed/mtm/privacy_policy.html";
    private static UserDao dao = new UserDao();

    public UserResource() {
        super(dao);

    }

    @GET
    @Path("/privacy_policy")
    @Produces({MediaType.TEXT_HTML})
    public InputStream viewHome()
    {
        File f = new File(privacyPolicyHTMLLOcation);
        try {
            return new FileInputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PATCH
    @Path("/users")
    public Object updateUser(User record)

    {
        record.setPassphrase(DigestUtils.sha1Hex(record.getPassphrase()));
        if(dao.patch(record,"usertype='"+record.getUsertype()+"'")==1)
            return new Status("SUCCESS",0,0);
        else
            return new Status("FAILED",1,0);
    }



    @POST
    @Path("/users")
    public Object addUser(User user)

    {
        return dao.addUser(user);
    }

    public List<User> getPaginatedRecords(@QueryParam("where") Optional<String> whereClause, @QueryParam("min") Optional<String> min, @QueryParam("max") Optional<String> max, @QueryParam("recordsPerPage") Optional<String> recordsPerPage) {
        return null;
    }
}
