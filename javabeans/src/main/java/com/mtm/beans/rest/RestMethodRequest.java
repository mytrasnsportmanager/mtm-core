package com.mtm.beans.rest;

import com.google.common.base.Optional;

import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

/**
 * Created by Admin on 3/14/2020.
 */
public class RestMethodRequest {

    @QueryParam("where") Optional<String> whereClause;
    @QueryParam("min") Optional<String> min;
    @QueryParam("max") Optional<String> max;
    @QueryParam("recordsPerPage") Optional<String> recordsPerPage;
    @PathParam("tripid") Optional<String> tripId;


}
