package com.mtm.service.rest.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.mtm.beans.Status;
import com.mtm.beans.dto.*;
import com.mtm.dao.ConsignerDao;
import com.mtm.dao.Dao;
import com.mtm.dao.OwnerConsignerDao;
import io.dropwizard.jersey.PATCH;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Admin on 3/5/2019.
 */

@Produces(MediaType.APPLICATION_JSON)
public class ConsignerResource extends AbstractRestResource{
    private static final Dao dao = new ConsignerDao();
    public ConsignerResource() {
        super(dao);
    }



    @PATCH
    @Path("/consigners")
    public Object patchVehicleRecord(OwnerConsigner record)

    {
        if(patch(record)==1)
            return new Status("SUCCESS",0,0);
        else
            return new Status("FAILED",1,0);
    }

    @GET
    @Path("/consigners")
    public Object getConsigners()
    {
        return dao.getRecords("1=1");
    }

    @GET
    @Path("/consigners/search")
    public List<Object> search(@QueryParam("where") Optional<String> whereClause) {


        return get(whereClause.get());

    }

    @GET
    @Path("/consigners/{consignerid}")
    @Timed
    public List<Object> getConsigner(@PathParam("consignerid") Optional<String> consignerId) {

        String whereClause = " consignerid = "+consignerId.get();
        return get(whereClause);

    }

    public List<Consigner> getPaginatedRecords(@QueryParam("where") Optional<String> whereClause, @QueryParam("min") Optional<String> min, @QueryParam("max") Optional<String> max, @QueryParam("recordsPerPage") Optional<String> recordsPerPage) {
        return null;
    }
}
