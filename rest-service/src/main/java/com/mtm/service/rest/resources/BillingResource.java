package com.mtm.service.rest.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.mtm.beans.AccountSummary;
import com.mtm.beans.AmountAndDate;
import com.mtm.beans.RateType;
import com.mtm.beans.dto.CreditDebit;
import com.mtm.beans.dto.Txn;
import com.mtm.dao.BillingDao;
import com.mtm.dao.Dao;
import com.mtm.dao.RateDao;
import com.mtm.pdfgenerator.PDFGeneratorUtil;
import org.apache.commons.io.FileUtils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 9/30/2019.
 */

@Produces(MediaType.APPLICATION_JSON)
public class BillingResource extends AbstractRestResource {
    private static Dao dao = new BillingDao();

    private static final String PAGINATION_VIEW_NAME = "earned_received";
    private static final String PAGINATION_VIEW_ID_COLUMN="erid";
    private static final String PAGINATION_VIEW_ENTITY_ID_COLUMN="vehicleid";
    private static final String PAGINATION_VIEW_ORDER_COLUMN="eventtime";
    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final List<String> PAGINATION_SELECT_COLUMNS = new ArrayList<String>();

    static
    {

        PAGINATION_SELECT_COLUMNS.add("amount"      );
        PAGINATION_SELECT_COLUMNS.add("eventtime"   );
        PAGINATION_SELECT_COLUMNS.add("type"      );
        PAGINATION_SELECT_COLUMNS.add("referenceid"    );
        PAGINATION_SELECT_COLUMNS.add("work"    );
        PAGINATION_SELECT_COLUMNS.add("vehicleid"    );
        PAGINATION_SELECT_COLUMNS.add("consignerid"    );

    }


    public BillingResource() {
        super(dao);
        super.setPaginationSelectColumns(PAGINATION_SELECT_COLUMNS);
        super.setPaginationViewName(PAGINATION_VIEW_NAME);
        super.setPaginationViewIdColumn(PAGINATION_VIEW_ID_COLUMN);
        super.setPaginationViewEntityIdColumn(PAGINATION_VIEW_ENTITY_ID_COLUMN);
        super.setPaginationViewOrderColumn(PAGINATION_VIEW_ORDER_COLUMN);
    }

    public BillingResource(Dao dao) {
        super(dao);
    }
    @GET
    @Path("/billing/summary/{vehicleid}/{consignerid}")
    public AccountSummary getAccountSummary(@PathParam("vehicleid") Optional<String> vehicleIdArg, @PathParam("consignerid") Optional<String> consignerIdArg)
    {
        long consignerId = Long.parseLong(consignerIdArg.get());
        long vehicleId = Long.parseLong(vehicleIdArg.get());
        List<List<String>> records = dao.executeQuery("select name from owner where ownerid = ( select ownerid from vehicle where vehicleid = "+vehicleId+")");

        String ownerName = records.get(0).get(0);
        records = dao.executeQuery("select name from consigner where consignerid = "+consignerId+"");
        String consignerName = records.get(0).get(0);

        AmountAndDate billedAmountAndDate = ((BillingDao)dao).getPreviousBilledAmount(consignerId,vehicleId);
        double unbillledReceived = ((BillingDao)dao).getUnibilledReceived(consignerId,vehicleId);
        double unbilledEarned = ((BillingDao)dao).getUnbilledEarnings(consignerId,vehicleId);
        double totalReceivables = billedAmountAndDate.getAmount() + unbilledEarned - unbillledReceived;
        AccountSummary accountSummary = new AccountSummary();
        accountSummary.setConsignerId(consignerId);
        accountSummary.setVehicleId(vehicleId);
        accountSummary.setLastBillDate(billedAmountAndDate.getDate());
        accountSummary.setReportDate(new Date());
        accountSummary.setTotalBilled(billedAmountAndDate.getAmount());
        accountSummary.setTotalReceivables(totalReceivables);
        accountSummary.setTotalUnbilledEarned(unbilledEarned);
        accountSummary.setTotalUnbilledReceived(unbillledReceived);
        accountSummary.setOwnerName(ownerName);
        accountSummary.setConsignerName(consignerName);
        return accountSummary;
    }

    @GET
    @Path("/statement/getpaginated")
    public List<CreditDebit> getPaginatedRecords(@QueryParam("where") Optional<String> whereClause, @QueryParam("min") Optional<String> min, @QueryParam("max") Optional<String> max, @QueryParam("recordsPerPage") Optional<String> recordsPerPage) {
        List<List<String>> records = super.getPaginated(whereClause,min,max,recordsPerPage);

        List<CreditDebit> creditDebits = new ArrayList<CreditDebit>();
        if(records==null || records.size()==0)
            return creditDebits;
        for(List<String> record : records)
        {
            CreditDebit creditDebit = new CreditDebit();
            creditDebit.setAmount(Double.parseDouble(record.get(0)));
            if(record.get(1)!=null)
                try {
                    creditDebit.setDate(dateTimeFormat.parse(record.get(1)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            creditDebit.setType(record.get(2));
            creditDebit.setId(record.get(3));
            creditDebit.setTxn_type(record.get(4));
            creditDebit.setVehicleid(Long.parseLong(record.get(5)));
            creditDebit.setConsignerid(Long.parseLong(record.get(6)));
            creditDebit.setRowid(Long.parseLong(record.get(7)));
            creditDebits.add(creditDebit);



        }
        return creditDebits;

    }

    @GET
    @Produces("application/pdf")
    @Path("/statement/download/{vehicleid}/{consignerid}")
    @Timed
    public Response downloadStatement(@PathParam("vehicleid") Optional<String> vehicleIdArg, @PathParam("consignerid") Optional<String> consignerIdArg)
    {
        long consignerId = Long.parseLong(consignerIdArg.get());
        long vehicleId = Long.parseLong(vehicleIdArg.get());


            try {
                final java.nio.file.Path generatedPDFPath = Paths.get(PDFGeneratorUtil.generate(vehicleId,consignerId));

                return Response.ok().entity(new StreamingOutput() {

                    public void write(final OutputStream output) throws IOException, WebApplicationException {
                        try {
                            Files.copy(generatedPDFPath, output);
                        } finally {
                            Files.delete(generatedPDFPath);
                        }
                    }
                }).build();


            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

    }



