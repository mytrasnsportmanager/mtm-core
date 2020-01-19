package com.mtm.service.rest.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.mtm.beans.Status;
import com.mtm.beans.dto.Txn;
import com.mtm.beans.dto.VehicleLocation;
import com.mtm.dao.Dao;
import com.mtm.dao.TxnDao;
import com.mtm.dao.VehicleLocationDao;
import io.dropwizard.jersey.PATCH;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 10/12/2019.
 */

@Produces(MediaType.APPLICATION_JSON)
public class VehicleLocationResource extends AbstractRestResource {
    private static Dao dao = new VehicleLocationDao();
    public VehicleLocationResource() {
        super(dao);
    }

    private void setDate(VehicleLocation location)
    {
        Date date = new Date();
        DateTimeZone timeZoneIndia = DateTimeZone.forID( "Asia/Kolkata" );
        DateTime nowIndia = DateTime.now(timeZoneIndia);
        location.setLast_seen_at(nowIndia.toDate());
    }
    @POST
    @Path("/location")
    public Object createTxn(VehicleLocation location)

    {
        // Add startTime
       setDate(location);
        return create(location);
    }
    @PATCH
    @Path("/location")
    public Object patchVehicleRecord(VehicleLocation location)

    {
        setDate(location);
        if(patch(location)==1)
            return new Status("SUCCESS",0,0);
        else
            return new Status("FAILED",1,0);
    }

    @GET
    @Path("/location/{vehicleid}")
    @Timed
    public List<Object> getLocation(@PathParam("vehicleid") Optional<String> vehicleid) {

        String whereClause = " vehicleid = "+vehicleid.get();
        return get(whereClause);

    }
}
