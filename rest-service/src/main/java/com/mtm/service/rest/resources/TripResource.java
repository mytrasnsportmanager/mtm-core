package com.mtm.service.rest.resources;

import com.codahale.metrics.annotation.Timed;
import com.mtm.beans.RateType;
import com.mtm.beans.Status;
import com.mtm.beans.dto.Trip;
import com.mtm.beans.dto.TripDetail;
import com.mtm.dao.TripDao;
import com.mtm.service.rest.RestResourceType;
import com.mtm.service.rest.auth.AuthorizationRule;
import com.mtm.service.rest.auth.TripAuthorizationHandler;
import com.mtm.service.rest.validation.AuthorizationCheck;
import io.dropwizard.jersey.PATCH;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 3/10/2019.
 */

@Produces(MediaType.APPLICATION_JSON)

public class TripResource extends AbstractRestResource{
    private static TripDao dao = new TripDao();
    private static final String PAGINATION_VIEW_NAME = "trip_detailed";
    private static final String PAGINATION_VIEW_ID_COLUMN="tripid";
    private static final String PAGINATION_VIEW_ENTITY_ID_COLUMN="vehicleid";
    private static final String PAGINATION_VIEW_ORDER_COLUMN="startTime";
    private static final List<String> PAGINATION_SELECT_COLUMNS = new ArrayList<String>();
    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private TripAuthorizationHandler tripAuthorizationHandler = new TripAuthorizationHandler();
    private DecimalFormat decimalFormat = new DecimalFormat("#,###,##0.00");


    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Trip getTrip() {
       return trip;
    }

    private Trip trip;


    static
    {
        PAGINATION_SELECT_COLUMNS.add("vehicleid"        );
        PAGINATION_SELECT_COLUMNS.add("routeid"          );
        PAGINATION_SELECT_COLUMNS.add("driverid"         );
        PAGINATION_SELECT_COLUMNS.add("consignerid"      );
        PAGINATION_SELECT_COLUMNS.add("source"           );
        PAGINATION_SELECT_COLUMNS.add("destination"      );
        PAGINATION_SELECT_COLUMNS.add("rate"             );
        PAGINATION_SELECT_COLUMNS.add("rate_type"        );
        PAGINATION_SELECT_COLUMNS.add("work_done_detail" );
        PAGINATION_SELECT_COLUMNS.add("starttime"        );
        PAGINATION_SELECT_COLUMNS.add("endtime"          );
        PAGINATION_SELECT_COLUMNS.add("consigner_name"   );
        PAGINATION_SELECT_COLUMNS.add("material_name"    );
        PAGINATION_SELECT_COLUMNS.add("drivername"       );
        PAGINATION_SELECT_COLUMNS.add("image_url"        );
        PAGINATION_SELECT_COLUMNS.add("tripid"           );
        PAGINATION_SELECT_COLUMNS.add("ownerid"          );
        PAGINATION_SELECT_COLUMNS.add("registration_num" );
        PAGINATION_SELECT_COLUMNS.add("billingid"         );
        PAGINATION_SELECT_COLUMNS.add("work_done"         );
        PAGINATION_SELECT_COLUMNS.add("expected_fuel_consumed"         );
    }
    public TripResource() {
        super(dao, new TripAuthorizationHandler());
        restResourceType = RestResourceType.TRIP;
        super.setPaginationSelectColumns(PAGINATION_SELECT_COLUMNS);
        super.setPaginationViewName(PAGINATION_VIEW_NAME);
        super.setPaginationViewIdColumn(PAGINATION_VIEW_ID_COLUMN);
        super.setPaginationViewEntityIdColumn(PAGINATION_VIEW_ENTITY_ID_COLUMN);
        super.setPaginationViewOrderColumn(PAGINATION_VIEW_ORDER_COLUMN);

    }

    @POST

    @Path("/trips")
    @AuthorizationCheck(AuthorizationRule.TRIP_CANNOT_BE_ADDED_BY_NON_OWNER)
    public Object createTrip(@Context HttpServletRequest req, @Context HttpServletResponse res, @Context ResourceInfo resourceInfo, @Context UriInfo uriInfo,  Trip tripParam)

    {
        // Add startTime
        trip = tripParam;
        Date date = new Date();
        DateTimeZone timeZoneIndia = DateTimeZone.forID( "Asia/Kolkata" );
        DateTime nowIndia = DateTime.now(timeZoneIndia);
        trip.setStarttime(nowIndia.toDate());
        double expectedMileage = 0;
        double distance = 0;
        // Calculate the expected fuel consumption for this trip
        List<List<String>> expectedMileageRecords = dao.executeQuery(" select expected_mileage from vehicle where vehicleid = "+trip.getVehicleid());
        List<List<String>> distanceRecords = dao.executeQuery(" select distance from route where routeid = "+trip.getRouteid());
        if(expectedMileageRecords.size()==1)
        {
            expectedMileage = Double.parseDouble(expectedMileageRecords.get(0).get(0));
        }

        if(distanceRecords.size()==1)
        {
            distance = (Double.parseDouble(distanceRecords.get(0).get(0)))/1000;
        }
        double fuel_needed = distance/expectedMileage;
        trip.setExpected_fuel_consumed(fuel_needed);
         return create(trip);
    }


    @PATCH
    @Path("/trips")
    public Object patchVehicleRecord(Trip tripParam)

    {
        trip = tripParam;
        if(patch(trip)==1)
            return new Status("SUCCESS",0,0);
        else
            return new Status("FAILED",1,0);
    }


    @GET
    @Path("/trips")
    public Object getTrips()
    {

        return dao.getRecords("1=1");
    }


    @GET
    @Path("/trips/search")
    public List<Object> search() {


        return get(whereClause.get());

    }

    @DELETE
    @Path("/trips/{tripid}")
    @Timed
    public Object deleteTrip()
    {

        Status status = new Status();
        if(dao.delete(" tripid = "+tripId.get())==1) {
            status.setMessage("SUCEESS");
            status.setReturnCode(0);

        }
        else
        {
            status.setReturnCode(1);
            status.setMessage("The request could not be processed");
        }
        return status;

    }


    @GET
    @Path("/trips/getpaginated")
    public List<TripDetail> getPaginatedRecords() {


        List<List<String>> records = super.getPaginated(whereClause,min,max,recordsPerPage);

        List<TripDetail> tripDetails = new ArrayList<TripDetail>();

        if(records.size()==0)
            return tripDetails;

        if(records==null || records.size()==0)
            return tripDetails;
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

            Date startDate = null;
            Calendar cal = Calendar.getInstance();
           /* if(record.get(9)!=null) {
                cal.setTimeInMillis(Long.parseLong(record.get(9)));
                tripDetail.setStartTime(dateTimeFormat.format(cal.getTime()));
            }*/
            if(record.get(9)!=null)
            tripDetail.setStartTime(record.get(9));

           /* if(record.get(10)!=null) {
                cal.setTimeInMillis(Long.parseLong(record.get(10)));
                tripDetail.setEndTime(dateTimeFormat.format(cal.getTime()));
            }*/
            if(record.get(10)!=null)
            tripDetail.setEndTime(record.get(10));
            tripDetail.setConsignerName(record.get(11));
            tripDetail.setName_of_material(record.get(12));
            tripDetail.setDriver_name(record.get(13));
            tripDetail.setConsigner_image_url(record.get(14));
            tripDetail.setTripid(Long.parseLong(record.get(15)));
            tripDetail.setOwnerid(Long.parseLong(record.get(16)));
            tripDetail.setVehicle_resistration_num(record.get(17));
            if(record.get(18) != null)
            {
                tripDetail.setBillingid(Long.parseLong(record.get(18)));
            }
            if(record.get(19) != null)
            {
                tripDetail.setWork_done(Double.parseDouble(record.get(19)));
            }
            if(StringUtils.isNotEmpty(record.get(20)))
            tripDetail.setExpected_fuel_consumed(decimalFormat.format(Double.parseDouble(record.get(20))));
            tripDetails.add(tripDetail);
            tripDetail.setRowid(Long.parseLong(record.get(21)));

        }
        return tripDetails;



      //  return get(whereClause.get());

    }



    @GET
    @Path("/trips/{tripid}")
    @Timed
    public List<Object> getTripFromId() {

        String whereClause = " tripid = "+tripId.get();
        return get(whereClause);

    }


}
