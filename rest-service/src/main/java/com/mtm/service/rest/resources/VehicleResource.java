package com.mtm.service.rest.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.api.client.util.Joiner;
import com.google.common.base.Optional;
import com.mtm.beans.RateType;
import com.mtm.beans.Status;
import com.mtm.beans.UserSession;
import com.mtm.beans.UserType;
import com.mtm.beans.dto.*;
import com.mtm.dao.*;
import io.dropwizard.jersey.PATCH;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.text.DecimalFormat;
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

    @Context
    HttpServletRequest req;
    @Context
    HttpServletResponse res;
    private static VehicleDao dao = new VehicleDao();
    private static ExpenseDao expenseDao = new ExpenseDao();
    private static BillingDao billingDao = new BillingDao();
    private static TripDao tripDao = new TripDao();
    private static VehicleLocationDao vehicleLocationDao = new VehicleLocationDao();
    private DecimalFormat decimalFormat = new DecimalFormat("#,###,##0.00");


    public VehicleResource() {
        super(dao);

    }

    
    @POST
    @Path("/vehicles")
    public Object createVehicle(Vehicle vehicle)

    {
        if(vehicle.getDriverid()==0)
        {
            /* Create a provisional driver who can later register and will be associated with this id */
            UserDao userDao = new UserDao();
            User user = new User();
            user.setContact(vehicle.getDriver_contact());
            user.setName(vehicle.getDriver_name());
            user.setUsertype(UserType.DRIVER.toString());
            user.setRegistered_by("OWNER");
            long driverid = userDao.addUser(user).getInsertedId();
            vehicle.setDriverid(driverid);

            //vehicleDriver.setName(vehicle.set);
        }

        Status status =  (Status)create(vehicle);
        long vehicleid = status.getInsertedId();
        updateVehicleInSession(vehicleid);
        VehicleLocation vehicleLocation = new VehicleLocation();
        vehicleLocation.setVehicleid(vehicleid);
        vehicleLocationDao.insert(vehicleLocation);
        return status;
    }


    @PATCH
    @Path("/vehicles")
    public Object patchVehicleRecord(Vehicle record)

    {
        long updatedCount = 0;

        updatedCount = patch(record);


        if(record.getKms_without_trip()!=0)
        {
            VehicleFuelConsumption vehicleFuelConsumption = new VehicleFuelConsumption();
           //vehicleFuelConsumption.
            Vehicle vehicle = (Vehicle)dao.getConvertedRecords("vehicleid = "+record.getVehicleid()).get(0);
            double mileage = vehicle.getExpected_mileage();
            double current_fuel_level = vehicle.getCurrent_fuel_level();
            double fuel_spent = record.getKms_without_trip() / mileage;
            current_fuel_level = current_fuel_level - fuel_spent;
            vehicle.setCurrent_fuel_level(current_fuel_level);
            patch(vehicle);


            Date date = new Date();
            DateTimeZone timeZoneIndia = DateTimeZone.forID( "Asia/Kolkata" );
            DateTime nowIndia = DateTime.now(timeZoneIndia);
            vehicleFuelConsumption.setConsumption_date(nowIndia.toLocalDateTime().toDate());
            vehicleFuelConsumption.setConsumption_amt(fuel_spent);
            if(StringUtils.isNotEmpty(record.getKms_without_trip_remarks()))
                vehicleFuelConsumption.setRemarks(record.getKms_without_trip_remarks());
            vehicleFuelConsumption.setVehicleid(record.getVehicleid());
            VehicleFuelConsumptionDao vehicleFuelConsumptionDao = new VehicleFuelConsumptionDao();
            vehicleFuelConsumptionDao.insert(vehicleFuelConsumption);


        }

        if(updatedCount==1)
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

        StringBuffer whereClauseStr = new StringBuffer(whereClause.get());
        String authorizedVehicleList = getAuthorizedVehicleList();
        if(StringUtils.isNotEmpty(authorizedVehicleList))
            whereClauseStr.append(" and vehicleid in ("+authorizedVehicleList+" )");

        List<Object> vehicles = get(whereClauseStr.toString());
        for(Object vehicleObj : vehicles)
        {
            Vehicle vehicle = (Vehicle)vehicleObj;
            long driverId = vehicle.getDriverid();
            DriverDao driverDao = new DriverDao();
            List<Object> vehicleDrivers  = driverDao.getRecords(" driverid = "+driverId);
           if(vehicleDrivers!=null && vehicleDrivers.size() > 0) {
               VehicleDriver vehicleDriver = (VehicleDriver) (vehicleDrivers.get(0));
               vehicle.setDriver_name(vehicleDriver.getName());
           }
        }
        return vehicles;

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

    @DELETE
    @Path("/vehicles/{vehicleid}")
    @Timed
    public Object deleteRoute(@PathParam("vehicleid") Optional<String> vehicleId)
    {

        return dao.delete(" vehicleid = "+vehicleId.get());


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
                "end as work_done_detail, b.starttime, b.endtime,  c.name as consigner_name, b.material_name, c.image_url, (b.work_done*a.rate) as rent_earned, b.work_done, b.tripid, b.expected_fuel_consumed  from route a inner join trip  b on a.routeid = b.routeid inner join consigner c on a.consignerid = c.consignerid  where b.vehicleid ="+vehicleId.get()+" order  by b.starttime desc  limit "+numberRecords ;
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
            if(StringUtils.isNotEmpty(record.get(17)))
            tripDetail.setExpected_fuel_consumed(decimalFormat.format(Double.parseDouble(record.get(17))));
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
            return billingDao.downloadStatement(vehicleId,consignerid,true, null, null);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @POST
    @Path("/vehicles/{vehicleid}/repeatTrip")
    public Object repeatTrip(Trip tripArg)
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
        tripToBeRepeated.setStarttime(nowIndia.toLocalDateTime().toDate());
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


    private String getAuthorizedVehicleList()
    {
        HttpSession session= req.getSession();

        UserSession userSession = (UserSession) session.getAttribute("user_session");
        return Joiner.on(',').join(userSession.getVehicleIdList());
    }

    private void updateVehicleInSession(long vehicleid)
    {

        HttpSession session= req.getSession();

        UserSession userSession = (UserSession) session.getAttribute("user_session");
        userSession.getVehicleIdList().add(vehicleid);
        session.setAttribute("user_session",userSession);
    }

    @GET

    @Path("/vehicles/{consignerid}/{ownerid}")

    public List<Record> getVehiclesOwnerConsigners(@PathParam("consignerid") Optional<String> consignerId, @PathParam("ownerid") Optional<String> ownerId)
    {
        long consignerIdVal = Long.parseLong(consignerId.get());
        long ownerIdVal = Long.parseLong(ownerId.get());
        String query = "select * from vehicle where vehicleid in (select vehicleid from trip where routeid in (select routeid from route where consignerid ="+consignerIdVal+" and ownerid ="+ownerIdVal+" ) " +
                " union all select vehicleid from trip_detailed_hist where consignerid = " +consignerIdVal+
                " union all select vehicleid from txn where consignerid = " +consignerIdVal+") " +
                " and ownerid = "+ownerIdVal;
        List<Record> records = dao.getConvertedRecordsForQuery(query);
        return records;


    }




}
