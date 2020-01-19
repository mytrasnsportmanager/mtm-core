package com.mtm.service.rest.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.mtm.beans.Status;
import com.mtm.beans.dto.OwnerConsigner;
import com.mtm.beans.dto.TripDetail;
import com.mtm.beans.dto.VehicleDriver;
import com.mtm.dao.Dao;
import com.mtm.dao.DriverDao;
import com.mtm.dao.UserDao;
import io.dropwizard.jersey.PATCH;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.util.StringUtil;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 9/18/2019.
 */
public class VehicleDriverResource extends AbstractRestResource{
    private static DriverDao dao = new DriverDao();

    public VehicleDriverResource() {
        super(dao);

    }
    @POST
    @Path("/drivers")
    public Object addDriver(VehicleDriver vehicleDriver)

    {
        return create(vehicleDriver);
    }

    @PATCH
    @Path("/drivers")
    public Object patchDriverRecord(VehicleDriver record)

    {
        if(patch(record)==1)
            return new Status("SUCCESS",0,0);
        else
            return new Status("FAILED",1,0);
    }

    @GET
    @Path("/drivers")
    public Object getDrivers()
    {
        return dao.getRecords("1=1");
    }

    @GET
    @Path("/drivers/search")
    public List<Object> search(@QueryParam("where") Optional<String> whereClause) {


        return get(whereClause.get());

    }

    @GET
    @Path("/drivers/unassigned")
    public List<Object> searchUnassigned(@QueryParam("where") Optional<String> whereClause) {

        String query = "select driverid, name, address, license_num, contact from vehicledriver where driverid not in (select driverid from vehicle) ";
        String whereClauseStr = null;
        if(whereClause.isPresent() )
            whereClauseStr = whereClause.get();
        if(StringUtils.isNotEmpty(whereClauseStr))
        {
            query = query + " and "+whereClauseStr;
        }
        List<List<String>> availableDrivers = dao.executeQuery(query);
        List<Object> vehicleDrivers = new ArrayList<>();

        for(List<String> record: availableDrivers)
        {
            VehicleDriver vehicleDriver = new VehicleDriver();
            vehicleDriver.setDriverid(Long.parseLong(record.get(0)));
            vehicleDriver.setName(record.get(1));
            vehicleDriver.setAddress(record.get(2));
            vehicleDriver.setLicense_num(record.get(3));
            vehicleDriver.setContact(Long.parseLong(record.get(4)));
            vehicleDrivers.add(vehicleDriver);

        }

        return vehicleDrivers;

    }

    @GET
    @Path("/drivers/{driverid}")
    @Timed
    public List<Object> getConsigner(@PathParam("driverid") Optional<String> driverId) {

        String whereClause = " driverid = "+driverId.get();
        return get(whereClause);

    }


    public List<VehicleDriver> getPaginatedRecords(@QueryParam("where") Optional<String> whereClause, @QueryParam("min") Optional<String> min, @QueryParam("max") Optional<String> max, @QueryParam("recordsPerPage") Optional<String> recordsPerPage) {
        return null;
    }
}
