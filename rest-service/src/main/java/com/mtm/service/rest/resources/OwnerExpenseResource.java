package com.mtm.service.rest.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.mtm.beans.Status;
import com.mtm.beans.dto.OwnerExpense;
import com.mtm.beans.dto.Route;
import com.mtm.dao.Dao;
import com.mtm.dao.RouteDao;
import com.mtm.dao.connection.OwnerExpenseDao;
import io.dropwizard.jersey.PATCH;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import javax.ws.rs.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 7/26/2020.
 */
public class OwnerExpenseResource extends AbstractRestResource {

    private static Dao dao = new OwnerExpenseDao();
    public OwnerExpenseResource() {
        super(dao);
    }

    @POST
    @Path("/ownerexpense")
    public Object addExpense(OwnerExpense ownerExpense)

    {

        DateTimeZone timeZoneIndia = DateTimeZone.forID( "Asia/Kolkata" );
        DateTime nowIndia = DateTime.now(timeZoneIndia);
        ownerExpense.setExpense_date(nowIndia.toLocalDateTime().toDate());

        return create(ownerExpense);
    }

    @PATCH
    @Path("/ownerexpense")
    public Object pathExpense(Route record)

    {
        if(patch(record)==1)
            return new Status("SUCCESS",0,0);
        else
            return new Status("FAILED",1,0);
    }

    @GET
    @Path("/ownerexpenses")
    public Object getRoutes()
    {
        return dao.getRecords("1=1");
    }


    @GET
    @Path("/ownerexpenses/search")
    public List<Object> search(@QueryParam("where") Optional<String> whereClause) {


        return get(whereClause.get());

    }

    @DELETE
    @Path("/ownerexpenses/{expenseid}")
    @Timed
    public Object deleteExpense(@PathParam("expenseid") Optional<String> exepenseId)
    {

        Status status = new Status();
        if(dao.delete(" expenseid = "+exepenseId.get())==1) {
            status.setMessage("SUCEESS");
            status.setReturnCode(0);

        }
        else
        {
            status.setReturnCode(1);
            status.setMessage("The expense could not be deleted");
        }
        return status;

    }

    @GET
    @Path("/ownerexpense/{expenseid}")
    @Timed
    public List<Object> getRoute(@PathParam("routeid") Optional<String> routeId) {

        String whereClause = " routeid = "+routeId.get();
        return get(whereClause);

    }

    public List<Route> getPaginatedRecords(@QueryParam("where") Optional<String> whereClause, @QueryParam("min") Optional<String> min, @QueryParam("max") Optional<String> max, @QueryParam("recordsPerPage") Optional<String> recordsPerPage) {
        return null;
    }


}
