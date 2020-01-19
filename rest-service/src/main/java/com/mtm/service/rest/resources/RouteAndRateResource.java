package com.mtm.service.rest.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.mtm.beans.Status;
import com.mtm.beans.dto.Route;
import com.mtm.beans.dto.RouteAndRate;
import com.mtm.beans.dto.TripDetail;
import com.mtm.beans.dto.Vehicle;
import com.mtm.dao.Dao;
import com.mtm.dao.RouteAndRateDao;
import com.mtm.dao.RouteDao;
import io.dropwizard.jersey.PATCH;

import javax.ws.rs.*;
import java.util.List;

/**
 * Created by Admin on 3/10/2019.
 */
public class RouteAndRateResource extends AbstractRestResource {

        private static Dao dao = new RouteAndRateDao();

        public RouteAndRateResource() {
            super(dao);

        }

        @POST
        @Path("/routerates")
        public Object createRouteRates(RouteAndRate routeAndRate)

        {
            return create(routeAndRate);
        }

        @PATCH
        @Path("/routerates")
        public Object patchVehicleRecord(RouteAndRate record)

        {
                if(patch(record)==1)
                        return new Status("SUCCESS",0,0);
                else
                        return new Status("FAILED",1,0);
        }
        @GET
        @Path("/routerates")
        public Object getRouteRates()
        {
            return dao.getRecords("1=1");
        }


        @GET
        @Path("/routerates/search")
        public List<Object> search(@QueryParam("where") Optional<String> whereClause) {


            return get(whereClause.get());

        }


        public List<RouteAndRate> getPaginatedRecords(@QueryParam("where") Optional<String> whereClause, @QueryParam("min") Optional<String> min, @QueryParam("max") Optional<String> max, @QueryParam("recordsPerPage") Optional<String> recordsPerPage) {
                return null;
        }
}
