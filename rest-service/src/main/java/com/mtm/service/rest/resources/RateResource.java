package com.mtm.service.rest.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.mtm.beans.RateType;
import com.mtm.beans.Status;
import com.mtm.beans.dto.Rate;
import com.mtm.beans.dto.TripDetail;
import com.mtm.beans.dto.Vehicle;
import com.mtm.dao.Dao;
import com.mtm.dao.RateDao;
import com.mtm.dao.VehicleDao;
import io.dropwizard.jersey.PATCH;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 3/5/2019.
 */

@Produces(MediaType.APPLICATION_JSON)
public class RateResource extends AbstractRestResource{
    private static RateDao dao = new RateDao();
    public RateResource() {
        super(dao);
    }

    @POST
    @Path("/rates")
    public Object addRate(Rate rate)

    {
        return create(rate);
    }

    @PATCH
    @Path("/rates")
    public Object patchVehicleRecord(Rate record)

    {
        if(patch(record)==1)
            return new Status("SUCCESS",0,0);
        else
            return new Status("FAILED",1,0);
    }

    @GET
    @Path("/rates/types")
    public Object getRateTypes()
    {
        return RateType.getTypeValues();
    }

    @GET
    @Path("/rates")
    public Object getRates()
    {
        return dao.getRecords("1=1");
    }


    @GET
    @Path("/rates/search")
    public List<Object> search(@QueryParam("where") Optional<String> whereClause) {


        return get(whereClause.get());

    }


    @GET
    @Path("/rates/{rateid}")
    @Timed
    public List<Object> getRate(@PathParam("rateid") Optional<String> rateId) {

        String whereClause = " rateid = "+rateId.get();
        return get(whereClause);

    }

    public List<Rate> getPaginatedRecords(@QueryParam("where") Optional<String> whereClause, @QueryParam("min") Optional<String> min, @QueryParam("max") Optional<String> max, @QueryParam("recordsPerPage") Optional<String> recordsPerPage) {
        return null;
    }
}
