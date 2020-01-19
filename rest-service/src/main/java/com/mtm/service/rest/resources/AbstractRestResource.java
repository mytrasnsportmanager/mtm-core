package com.mtm.service.rest.resources;

import com.google.common.base.Optional;
import com.mtm.beans.Status;
import com.mtm.beans.dto.Record;
import com.mtm.dao.Dao;
import com.mtm.service.rest.utils.PaginationUtil;
import org.apache.commons.lang3.StringUtils;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 3/5/2019.
 */

@Path("/mtm")
@Produces(MediaType.APPLICATION_JSON)
public abstract class AbstractRestResource  implements RestResource {
    private Dao dao = null;
    private  String paginationViewName = "trip_detailed";
    private  String paginationViewIdColumn="tripid";
    private  String paginationViewEntityIdColumn="vehicleid";
    private  String paginationViewOrderColumn="startTime";
    private  List<String> paginationSelectColumns = new ArrayList<String>();

    public String getPaginationViewName() {
        return paginationViewName;
    }

    public void setPaginationViewName(String paginationViewName) {
        this.paginationViewName = paginationViewName;
    }

    public String getPaginationViewIdColumn() {
        return paginationViewIdColumn;
    }

    public void setPaginationViewIdColumn(String paginationViewIdColumn) {
        this.paginationViewIdColumn = paginationViewIdColumn;
    }

    public String getPaginationViewEntityIdColumn() {
        return paginationViewEntityIdColumn;
    }

    public void setPaginationViewEntityIdColumn(String paginationViewEntityIdColumn) {
        this.paginationViewEntityIdColumn = paginationViewEntityIdColumn;
    }

    public String getPaginationViewOrderColumn() {
        return paginationViewOrderColumn;
    }

    public void setPaginationViewOrderColumn(String paginationViewOrderColumn) {
        this.paginationViewOrderColumn = paginationViewOrderColumn;
    }

    public List<String> getPaginationSelectColumns() {
        return paginationSelectColumns;
    }

    public void setPaginationSelectColumns(List<String> paginationSelectColumns) {
        this.paginationSelectColumns = paginationSelectColumns;
    }






    public AbstractRestResource(Dao dao)
    {
        this.dao = dao;
    }


    public Object create(Record record) {
        long insertedId = dao.insert(record);
        Status status = new Status();
        status.setReturnCode(0);
        status.setMessage("success");
        status.setInsertedId(insertedId);
        return status;
    }

    public List<Object> get(String whereClause) {

        return dao.getRecords(whereClause);
    }

    public Object update(Record record) {
        return null;
    }

    public Object delete(Record record) {
        return null;
    }

    public long patch(Record record) {
        return dao.patch(record);
    }


    public  List<List<String>> getPaginated(@QueryParam("where") Optional<String> whereClause , @QueryParam("min") Optional<String> min,
                                         @QueryParam("max") Optional<String> max,
                                         @QueryParam("recordsPerPage") Optional<String> recordsPerPage)
    {
        long minTripId = Long.parseLong(min.get());
        long maxTripId = Long.parseLong(max.get());

        List<List<String>> records = new ArrayList<List<String>>();
        int pageLimit = 10;
        if(recordsPerPage.isPresent() && StringUtils.isNotEmpty(recordsPerPage.get()))
            pageLimit =Integer.parseInt(recordsPerPage.get());

        StringBuffer whereClauseToken = new StringBuffer("");

        if(whereClause.isPresent() && StringUtils.isNotEmpty(whereClause.get())) {
            whereClauseToken.append(" where " + whereClause.get());
            // whereOrAnd = " and ";

        }
        String baseCaseMaxRowidQuery = PaginationUtil.generateBoundaryQuery(paginationViewName,paginationViewIdColumn,paginationViewEntityIdColumn,paginationViewOrderColumn,whereClauseToken.toString());
        PaginationUtil.PageInfo pageInfo = PaginationUtil.getPageInfo(minTripId,maxTripId,pageLimit, baseCaseMaxRowidQuery.toString(),dao);
        if(!pageInfo.hasRecords())
            return records;

        String query = PaginationUtil.generateDataQuery(pageInfo,paginationViewName,paginationSelectColumns,paginationViewIdColumn,paginationViewEntityIdColumn,paginationViewOrderColumn,whereClauseToken.toString());

        return dao.executeQuery(query);

    }


   /* @Path("/")
    public void authorize(
            @Context final HttpServletRequest req,
            @Context final HttpServletResponse response
    ) {
        // you can also get to the session
        // it is recommended to uncomment the "false" argument below
        // to avoid creating sessions if they don't already exist
        HttpSession session= req.getSession(true);
        Cookie[] cookies = req.getCookies();
        for(Cookie cookie : cookies)
        {
            System.out.println("Cookie Name "+cookie.getName()+", value "+cookie.getValue());
        }
        Object foo = session.getAttribute("foo");
        if (foo!=null) {
            //CookieParam cookieParam = session.get
            System.out.println(foo.toString());
        } else {
            foo = "bar";
            session.setAttribute("foo", "bar");
            System.out.println("no session found");
        }
    }*/



}
