package com.mtm.service.rest.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.mtm.beans.Status;
import com.mtm.beans.dto.OwnerConsigner;
import com.mtm.beans.dto.Rate;
import com.mtm.beans.dto.TripDetail;
import com.mtm.beans.dto.Vehicle;
import com.mtm.dao.Dao;
import com.mtm.dao.OwnerConsignerDao;
import com.mtm.dao.RateDao;
import io.dropwizard.jersey.PATCH;

import javax.ws.rs.*;
import java.util.List;

/**
 * Created by Admin on 3/13/2019.
 */
public class OwnerConsignerResource extends AbstractRestResource {

    private static Dao dao = new OwnerConsignerDao();
    public OwnerConsignerResource() {
        super(dao);
    }

    @POST
    @Path("/ownerconsigners")
    public Object addOwnerConsigner(OwnerConsigner ownerConsigner)

    {
        long ownerid = ownerConsigner.getOwnerid();
        long consignerid = ownerConsigner.getConsignerid();
        String whereClause = " ownerid="+ownerid+" and consignerid = "+consignerid;
        if(get(whereClause).size()==0)
        return create(ownerConsigner);
        else {
            Status failedStatus = new Status();
            failedStatus.setReturnCode(1);
            failedStatus.setMessage("Owner consigner relationship already exists");
            return  failedStatus;

        }
    }

    @PATCH
    @Path("/ownerconsigners")
    public Object patchVehicleRecord(OwnerConsigner record)

    {
        if(patch(record)==1)
            return new Status("SUCCESS",0,0);
        else
            return new Status("FAILED",1,0);
    }

    @GET
    @Path("/ownerconsigners")
    public Object getRates()
    {
        return dao.getRecords("1=1");
    }


    @GET
    @Path("/ownerconsigners/search")
    public List<Object> search(@QueryParam("where") Optional<String> whereClause) {


        return get(whereClause.get());

    }


    public List<OwnerConsigner> getPaginatedRecords(@QueryParam("where") Optional<String> whereClause, @QueryParam("min") Optional<String> min, @QueryParam("max") Optional<String> max, @QueryParam("recordsPerPage") Optional<String> recordsPerPage) {
        return null;
    }
}
