package com.mtm.service.rest.resources;

import com.google.common.base.Optional;
import com.mtm.beans.dto.Trip;
import com.mtm.beans.dto.TripDetail;
import com.mtm.beans.dto.User;
import com.mtm.dao.TripDao;
import com.mtm.dao.UserDao;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 9/15/2019.
 */
public class UserResource extends  AbstractRestResource {
    private static UserDao dao = new UserDao();

    public UserResource() {
        super(dao);

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
