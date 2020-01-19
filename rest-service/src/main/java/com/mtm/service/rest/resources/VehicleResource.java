package com.mtm.service.rest.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.mtm.beans.RateType;
import com.mtm.beans.Status;
import com.mtm.beans.dto.*;
import com.mtm.dao.*;
import io.dropwizard.jersey.PATCH;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 2/24/2019.
 */

@Produces(MediaType.APPLICATION_JSON)
public class VehicleResource extends AbstractRestResource {

    private static VehicleDao dao = new VehicleDao();
    private static ExpenseDao expenseDao = new ExpenseDao();
    private static BillingDao billingDao = new BillingDao();
    private static TripDao tripDao = new TripDao();
    private static VehicleLocationDao vehicleLocationDao = new VehicleLocationDao();


    public VehicleResource() {
        super(dao);

    }

    
    @POST
    @Path("/vehicles")
    public Object createVehicle(Vehicle vehicle)

    {

        Status status =  (Status)create(vehicle);
        long vehicleid = status.getInsertedId();
        VehicleLocation vehicleLocation = new VehicleLocation();
        vehicleLocation.setVehicleid(vehicleid);
        vehicleLocationDao.insert(vehicleLocation);
        return status;
    }


    @PATCH
    @Path("/vehicles")
    public Object patchVehicleRecord(Vehicle record)

    {
        if(patch(record)==1)
            return new Status("SUCCESS",0,0);
        else
            return new Status("FAILED",1,0);
    }


    @GET
    @Path("/vehicles")
    public Object getVehicles()
    {
        return dao.getRecords("1=1");
    }


    @GET
    @Path("/vehicles/search")
    public List<Object> search(@QueryParam("where") Optional<String> whereClause) {


        return get(whereClause.get());

    }


    @GET
    @Path("/vehicles/{vehicleid}")
    @Timed
    public List<Object> getVehicle(@PathParam("vehicleid") Optional<String> vehicleId) {

        String whereClause = " vehicleid = "+vehicleId.get();
        return get(whereClause);

    }

    @GET
    @Path("/vehicles/{vehicleid}/lastTrip")
    @Timed
    public List<TripDetail> getLastTrip(@PathParam("vehicleid") Optional<String> vehicleId) {

        return getRecentTrips(vehicleId, 1);

    }



    @GET
    @Path("/vehicles/{vehicleid}/getRecentTrips")
    @Timed
    public List<TripDetail> getRecentTrips(@PathParam("vehicleid") Optional<String> vehicleId, int... limit) {

        int numberRecords;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String whereClause = " vehicleid = "+vehicleId.get();

        if (limit == null || limit.length == 0)
            numberRecords = 7;
        else
            numberRecords = limit[0];

        String query = "select b.vehicleid, b.routeid, b.driverid, c.consignerid, a.source, a.destination, a.rate, a.rate_type, \n" +
                "case \n" +
                "when a.rate_type = 'per tonne' then concat(b.work_done , \" tonnes, \",a.rate,\" per tonne\")\n" +
                "when a.rate_type = 'per km' then concat(b.work_done , \" KMs, \",a.rate,\" per KM\")\n" +
                "when a.rate_type = 'per month' then concat(b.work_done , \" Months, \",a.rate, \"per Month\")\n" +
                "else\n" +
                "concat(b.work_done)\n" +
                "end as work_done_detail, b.starttime, b.endtime,  c.name as consigner_name, b.material_name, c.image_url, (b.work_done*a.rate) as rent_earned, b.work_done, b.tripid  from route a inner join trip  b on a.routeid = b.routeid inner join consigner c on a.consignerid = c.consignerid  where b.vehicleid ="+vehicleId.get()+" order  by b.starttime desc  limit "+numberRecords ;
        List<List<String>> records = dao.executeQuery(query);
        List<TripDetail> tripDetails = new ArrayList<TripDetail>();
        for(List<String> record : records)
        {
            TripDetail tripDetail = new TripDetail();
            tripDetail.setVehicleid(Long.parseLong(record.get(0)));
            tripDetail.setRouteid(Long.parseLong(record.get(1)));
            tripDetail.setDriverid(Long.parseLong(record.get(2)));
            tripDetail.setConsignerid(Long.parseLong(record.get(3)));
            tripDetail.setSource(record.get(4));
            tripDetail.setDestination(record.get(5));
            if(record.get(6)!=null)
            tripDetail.setRate(Float.parseFloat(record.get(6)));
            if(record.get(7)!=null)
            tripDetail.setRateType(RateType.fromValue(record.get(7)));
            if(record.get(8)!=null)
            tripDetail.setWork_done_detail(record.get(8));
            tripDetail.setStartTime(record.get(9));
            tripDetail.setEndTime(record.get(10));
            tripDetail.setConsignerName(record.get(11));
            tripDetail.setName_of_material(record.get(12));
            tripDetail.setConsigner_image_url(record.get(13));
            tripDetail.setRentEarned(Double.parseDouble(record.get(14)));
            tripDetail.setWork_done(Double.parseDouble(record.get(15)));
            tripDetail.setTripid(Long.parseLong(record.get(16)));
            tripDetails.add(tripDetail);

        }
        return tripDetails;


    }

    @GET
    @Path("/vehicles/{vehicleid}/getRecentExpenses")
    @Timed
    public List<Object> getRecentExpenses(@PathParam("vehicleid") Optional<String> vehicleId) {

       return expenseDao.getRecords(" vehicleid = "+vehicleId.get()+" order by expensedate desc limit 7");

    }

    @GET
    @Path("/vehicles/unbilledstatement")
    public Object downloadUnbilledStatement(@QueryParam("vehicleid") Optional<String> vehicleIdStr , @QueryParam("consignerid") Optional<String> consignerIdStr)
    {
        long vehicleId = Long.parseLong(vehicleIdStr.get());
        long consignerid = Long.parseLong(consignerIdStr.get());
        try {
            return billingDao.downloadStatement(vehicleId,consignerid,true);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @POST
    @Path("/vehicles/{vehicleid}/repeatTrip")
    public Object createVehicle(Trip tripArg)
    {
        Trip tripToBeRepeated = (Trip)((tripDao.getRecords(" tripid = "+tripArg.getTripid()).get(0)));
        if(tripArg.getWork_done()!=0)
        tripToBeRepeated.setWork_done(tripArg.getWork_done());
        tripToBeRepeated.setBillingid(0);
        //tripToBeRepeated.setStarttime(null);
        tripToBeRepeated.setEndtime(null);
        Date date = new Date();
        DateTimeZone timeZoneIndia = DateTimeZone.forID( "Asia/Kolkata" );
        DateTime nowIndia = DateTime.now(timeZoneIndia);
        tripToBeRepeated.setStarttime(nowIndia.toDate());
        Status status = new Status();
        long insertedId = tripDao.insert(tripToBeRepeated);
        status.setMessage("SUCCESS");
        status.setReturnCode(0);
        status.setInsertedId(insertedId);
        return status;

    }


    public List<Vehicle> getPaginatedRecords(@QueryParam("where") Optional<String> whereClause, @QueryParam("min") Optional<String> min, @QueryParam("max") Optional<String> max, @QueryParam("recordsPerPage") Optional<String> recordsPerPage) {
        return null;
    }




}
