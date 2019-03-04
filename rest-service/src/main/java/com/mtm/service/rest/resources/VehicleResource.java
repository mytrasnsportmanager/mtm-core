package com.mtm.service.rest.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.mtm.beans.Vehicle;
import com.mtm.beans.dto.Record;
import com.mtm.beans.dto.VehicleRecord;
import com.mtm.dao.Dao;
import com.mtm.dao.VehicleDao;
import com.sun.research.ws.wadl.Representation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2/24/2019.
 */
@Path("/vehicles/{ownerid}")
@Produces(MediaType.APPLICATION_JSON)
public class VehicleResource {


    public VehicleResource() {

    }
    @GET
    @Timed
    public List<Record> getMessage(@PathParam("ownerid") Optional<String> ownerId) {

        List<VehicleRecord> ownedVehicleList = new ArrayList<VehicleRecord>();
        //final String value = String.format(message, first.or(firstParameter), second.or(secondParameter));
        String whereClause = " ownerid = "+ownerId.get();
        Dao vehicleDao = new VehicleDao();
        return vehicleDao.getConvertedRecords(whereClause);

    }
}
