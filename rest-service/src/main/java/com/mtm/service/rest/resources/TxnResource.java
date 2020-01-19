package com.mtm.service.rest.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.mtm.beans.RateType;
import com.mtm.beans.Status;
import com.mtm.beans.dto.Route;
import com.mtm.beans.dto.TripDetail;
import com.mtm.beans.dto.Txn;
import com.mtm.beans.dto.Vehicle;
import com.mtm.dao.Dao;
import com.mtm.dao.TxnDao;
import com.mtm.dao.VehicleDao;
import io.dropwizard.jersey.PATCH;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 3/10/2019.
 */

@Produces(MediaType.APPLICATION_JSON)
public class TxnResource extends  AbstractRestResource {
    private static Dao dao = new TxnDao();
    private static final String PAGINATION_VIEW_NAME = "txn";
    private static final String PAGINATION_VIEW_ID_COLUMN="txnid";
    private static final String PAGINATION_VIEW_ENTITY_ID_COLUMN="vehicleid";
    private static final String PAGINATION_VIEW_ORDER_COLUMN="txn_date";
    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final List<String> PAGINATION_SELECT_COLUMNS = new ArrayList<String>();

    static
    {
        PAGINATION_SELECT_COLUMNS.add("txnid"       );
        PAGINATION_SELECT_COLUMNS.add("tripid"      );
        PAGINATION_SELECT_COLUMNS.add("vehicleid"   );
        PAGINATION_SELECT_COLUMNS.add("amount"      );
        PAGINATION_SELECT_COLUMNS.add("txn_type"    );
        PAGINATION_SELECT_COLUMNS.add("txn_date"    );
        PAGINATION_SELECT_COLUMNS.add("consignerid" );
        PAGINATION_SELECT_COLUMNS.add("txn_mode"    );
        PAGINATION_SELECT_COLUMNS.add("billingid"   );
    }


    public TxnResource() {
        super(dao);
        super.setPaginationSelectColumns(PAGINATION_SELECT_COLUMNS);
        super.setPaginationViewName(PAGINATION_VIEW_NAME);
        super.setPaginationViewIdColumn(PAGINATION_VIEW_ID_COLUMN);
        super.setPaginationViewEntityIdColumn(PAGINATION_VIEW_ENTITY_ID_COLUMN);
        super.setPaginationViewOrderColumn(PAGINATION_VIEW_ORDER_COLUMN);

    }

    @POST
    @Path("/txns")
    public Object createTxn(Txn txn)

    {
      // Add startTime
        Date date = new Date();
        DateTimeZone timeZoneIndia = DateTimeZone.forID( "Asia/Kolkata" );
        DateTime nowIndia = DateTime.now(timeZoneIndia);
        txn.setTxn_date(nowIndia.toDate());
        return create(txn);
    }
    @PATCH
    @Path("/txns")
    public Object patchVehicleRecord(Txn record)

    {
        if(patch(record)==1)
            return new Status("SUCCESS",0,0);
        else
            return new Status("FAILED",1,0);
    }


    @GET
    @Path("/txns")
    public Object getTxns()
    {
        return dao.getRecords("1=1");
    }


    @GET
    @Path("/txns/search")
    public List<Object> search(@QueryParam("where") Optional<String> whereClause) {


        return get(whereClause.get());

    }

    @GET
    @Path("/txns/{txnid}")
    @Timed
    public List<Object> getTxn(@PathParam("txnid") Optional<String> txnId) {

        String whereClause = " txnid = "+txnId.get();
        return get(whereClause);

    }

    @GET
    @Path("/txns/getpaginated")
    public List<Txn> getPaginatedRecords(@QueryParam("where") Optional<String> whereClause, @QueryParam("min") Optional<String> min, @QueryParam("max") Optional<String> max, @QueryParam("recordsPerPage") Optional<String> recordsPerPage) {
        List<List<String>> records = super.getPaginated(whereClause,min,max,recordsPerPage);

        List<Txn> txns = new ArrayList<Txn>();
        if(records==null || records.size()==0)
            return txns;
        for(List<String> record : records)
        {
            Txn txn = new Txn();
            txn.setTxnid(Long.parseLong(record.get(0)));
            txn.setTripid(Long.parseLong(record.get(1)));
            txn.setVehicleid(Long.parseLong(record.get(2)));
            txn.setAmount(Double.parseDouble(record.get(3)));
            txn.setTxn_type(Integer.parseInt(record.get(4)));
            if(record.get(5)!=null)
                try {
                    txn.setTxn_date(dateTimeFormat.parse(record.get(5)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            txn.setConsignerid(Long.parseLong(record.get(6)));
            txn.setTxn_mode(record.get(7));
            if(record.get(8)!=null)
            txn.setBillingid(Long.parseLong(record.get(8)));
            txn.setRowid(Long.parseLong(record.get(9)));
            txns.add(txn);


        }
        return txns;

    }
}
