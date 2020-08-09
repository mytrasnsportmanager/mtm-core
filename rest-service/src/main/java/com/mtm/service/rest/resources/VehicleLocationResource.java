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
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Admin on 10/12/2019.
 */

@Produces(MediaType.APPLICATION_JSON)
public class VehicleLocationResource extends AbstractRestResource {
    private static Dao dao = new VehicleLocationDao();
    private static SimpleDateFormat istTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static SimpleDateFormat utcTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public VehicleLocationResource() {
        super(dao);
    }

    private void setDate(VehicleLocation location)
    {
        Date date = new Date();
        DateTimeZone timeUTC = DateTimeZone.UTC;
        DateTime nowUTC = DateTime.now(timeUTC);
        location.setLast_seen_at(nowUTC.toLocalDateTime().toDate());
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
        if(((Status)create(location)).getMessage().equalsIgnoreCase("success"))
            return new Status("SUCCESS",0,0);
        else
            return new Status("FAILED",1,0);
    }

    @GET
    @Path("/location/{vehicleid}")
    @Timed
    public List<Object> getLocation(@PathParam("vehicleid") Optional<String> vehicleid) {

        String whereClause = " vehicleid = "+vehicleid.get()+" and last_seen_at = (select max(last_seen_at) from vehicle_location where vehicleid = "+vehicleid.get()+")";
        VehicleLocation vehicleLocation = (VehicleLocation) get(whereClause).get(0);
       /* long timeInMilliSeconds = vehicleLocation.getLast_seen_at().getTime();
        int offset  = TimeZone.getTimeZone("Asia/Kolkata").getOffset(timeInMilliSeconds);

        Date date = new Date(timeInMilliSeconds+offset);

        vehicleLocation.setLast_seen_at(date);*/
        List<Object> vehicleLocations = new ArrayList();
        vehicleLocations.add(vehicleLocation);
        return  vehicleLocations;

    }

    @GET
    @Path("/locationsbydate/{vehicleid}")
    @Timed
    public List<Object> getLocationBetweenTime(@PathParam("vehicleid") Optional<String> vehicleid, @QueryParam("from") Optional<String> fromArg, @QueryParam("to") Optional<String> toArg) {

        String from = null;
        if(fromArg.isPresent())
            from = fromArg.get();
        String to = null;
        if(toArg.isPresent())
            to = toArg.get();

        StringBuffer whereClauseBuffer = new StringBuffer();

        whereClauseBuffer.append(" vehicleid = "+vehicleid.get());
        if(StringUtils.isNotEmpty(from))
        {
            whereClauseBuffer.append(" and last_seen_at >= "+quote(getUTCTimeStr(unqoute(from))));
        }

        if(StringUtils.isNotEmpty("to"))
        {
            whereClauseBuffer.append(" and last_seen_at <= "+quote(getUTCTimeStr(unqoute(to))));
        }





        return get(whereClauseBuffer.toString());

    }

    private static String getUTCTimeStr(String dateTime)
    {
        try {
            istTimeFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
            Date date = istTimeFormat.parse(dateTime);
            utcTimeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            return utcTimeFormat.format(date);

            // Change timzone
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;

    }

    private static String quote(String key)
    {
        return "'"+key+"'";
    }

    private static String unqoute(String key)
    {
      return  key.replaceAll("'","");
    }

    public static void main(String args[])
    {
        String time = "'2020-07-16 13:34:00'";
        System.out.println("Time is "+unqoute(time));

    }
}
