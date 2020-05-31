package com.mtm.service.rest.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.mtm.beans.Status;
import com.mtm.beans.dto.Route;
import com.mtm.beans.dto.TripDetail;
import com.mtm.beans.dto.Vehicle;
import com.mtm.dao.Dao;
import com.mtm.dao.RouteDao;
import com.mtm.dao.VehicleDao;
import io.dropwizard.jersey.PATCH;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Admin on 3/10/2019.
 */

@Produces(MediaType.APPLICATION_JSON)
public class RouteResource extends AbstractRestResource {
    private static Dao dao = new RouteDao();

    public RouteResource() {
        super(dao);

    }

    @POST
    @Path("/routes")
    public Object createRoute(Route route)

    {


        return create(route);
    }

    @PATCH
    @Path("/routes")
    public Object patchVehicleRecord(Route record)

    {
        if(patch(record)==1)
            return new Status("SUCCESS",0,0);
        else
            return new Status("FAILED",1,0);
    }

    @GET
    @Path("/routes")
    public Object getRoutes()
    {
        return dao.getRecords("1=1");
    }


    @GET
    @Path("/routes/search")
    public List<Object> search(@QueryParam("where") Optional<String> whereClause) {


        return get(whereClause.get());

    }

    @DELETE
    @Path("/routes/{routeid}")
    @Timed
    public Object deleteRoute(@PathParam("routeid") Optional<String> routeId)
    {

     Status status = new Status();
     if(dao.delete(" routeid = "+routeId.get())==1) {
         status.setMessage("SUCEESS");
         status.setReturnCode(0);

     }
     else
     {
         status.setReturnCode(1);
         status.setMessage("The route could not be deleted, there are active trips");
     }
        return status;

    }

    @GET
    @Path("/routes/{routeid}")
    @Timed
    public List<Object> getRoute(@PathParam("routeid") Optional<String> routeId) {

        String whereClause = " routeid = "+routeId.get();
        return get(whereClause);

    }

    public List<Route> getPaginatedRecords(@QueryParam("where") Optional<String> whereClause, @QueryParam("min") Optional<String> min, @QueryParam("max") Optional<String> max, @QueryParam("recordsPerPage") Optional<String> recordsPerPage) {
        return null;
    }
}
