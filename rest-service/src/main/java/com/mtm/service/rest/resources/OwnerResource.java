package com.mtm.service.rest.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.mtm.beans.Status;
import com.mtm.beans.dto.*;

import com.mtm.dao.Dao;
import com.mtm.dao.OwnerDao;
import io.dropwizard.jersey.PATCH;
import org.glassfish.hk2.api.PerThread;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 3/10/2019.
 */


@Produces(MediaType.APPLICATION_JSON)
public class OwnerResource extends AbstractRestResource {
    private static final OwnerDao dao = new OwnerDao();
    public OwnerResource() {
        super(dao);
    }

    @POST
    @Path("/owners")
    public Object addOwner(Owner owner)

    {
        return create(owner);
    }

    @PATCH
    @Path("/owners")
    public Object patchVehicleRecord(Owner record)

    {
        if(patch(record)==1)
            return new Status("SUCCESS",0,0);
        else
            return new Status("FAILED",1,0);
    }
    @GET
    @Path("/owners")
    public Object getOwners()
    {
        return dao.getRecords("1=1");
    }

    @GET
    @Path("/owners/{ownerid}/consigners")
    public Object getConsigners(@PathParam("ownerid") Optional<String> ownerId)
    {
        return dao.getConsigners(ownerId.get());
    }

    @GET
    @Path("/owners/{ownerid}/routesdetailed")
    public List<Route> getAvailableRoutes(@PathParam("ownerid") Optional<String> ownerId) {

        String routeQuery = "select r.routeid,r.source ,r.destination,r.consignerid,r.source_district,r.source_state ,r.destination_district ,r.destination_state,r.source_longitude ,r.source_latitude,r.destination_longitude ,r.destination_latitude ,r.ownerid ,r.rate ,r.rate_type, c.consigner_name, c.consigner_contact, c.image_url as consigner_image from route r left outer join owner_consigner c on r.consignerid = c.consignerid and r.ownerid=c.ownerid where r.ownerid="+ownerId.get().toString();
        List<List<String>> records = dao.executeQuery(routeQuery);
        List<Route> routes = new ArrayList<Route>();
        for(List<String> record: records)
        {
            Route route = new Route();
            route.setRouteid(Long.parseLong(record.get(0)));
            route.setSource(record.get(1));
            route.setDestination(record.get(2));
            route.setConsignerid(Long.parseLong(record.get(3)));
            route.setSource_district(record.get(4));
            route.setSource_state(record.get(5));
            route.setDestination_district(record.get(6));
            route.setDestination_state(record.get(7));
            route.setSource_longitude(Double.parseDouble(record.get(8)));
            route.setSource_latitude(Double.parseDouble(record.get(9)));
            route.setDestination_longitude(Double.parseDouble(record.get(10)));
            route.setDestination_latitude(Double.parseDouble(record.get(11)));
            route.setOwnerid(Long.parseLong(record.get(12)));
            route.setRate(Double.parseDouble(record.get(13)));
            route.setRate_type(record.get(14));
            route.setConsigner_name(record.get(15));
            route.setConsigner_contact(record.get(16));
            route.setConsigner_image(record.get(17));
            routes.add(route);

        }
        return routes;

    }


    @GET
    @Path("/owners/search")
    public List<Object> search(@QueryParam("where") Optional<String> whereClause) {


        return get(whereClause.get());

    }
    @GET
    @Path("/owners/{ownerid}")
    @Timed
    public List<Object> getOwner(@PathParam("ownerid") Optional<String> ownerId) {

        String whereClause = " ownerid = "+ownerId.get();
        return get(whereClause);

    }

    public List<Owner> getPaginatedRecords(@QueryParam("where") Optional<String> whereClause, @QueryParam("min") Optional<String> min, @QueryParam("max") Optional<String> max, @QueryParam("recordsPerPage") Optional<String> recordsPerPage) {
        return null;
    }
}
