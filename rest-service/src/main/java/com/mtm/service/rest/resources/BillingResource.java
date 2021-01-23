package com.mtm.service.rest.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.api.client.util.Joiner;
import com.google.common.base.Optional;
import com.mtm.beans.*;
import com.mtm.beans.dto.*;
import com.mtm.dao.*;
import com.mtm.notification.FCMNotificationSender;
import com.mtm.pdfgenerator.PDFGeneratorUtil;
import org.apache.commons.io.FileUtils;
import org.joda.time.DateTime;

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
    private static BillingDao dao = new BillingDao();

    private static final String PAGINATION_VIEW_NAME = "earned_received";
    private static final String PAGINATION_VIEW_ID_COLUMN="erid";
    private static final String PAGINATION_VIEW_ENTITY_ID_COLUMN="vehicleid";
    private static final String PAGINATION_VIEW_ORDER_COLUMN="eventtime";
    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final List<String> PAGINATION_SELECT_COLUMNS = new ArrayList<String>();
    private static final String webserverAddress = System.getProperty("webserverAddress");
    private static final String downloadStatementBaseURL = "http://"+webserverAddress+"/mtm/statement/download/";

    static
    {

        PAGINATION_SELECT_COLUMNS.add("amount"      );
        PAGINATION_SELECT_COLUMNS.add("eventtime"   );
        PAGINATION_SELECT_COLUMNS.add("type"      );
        PAGINATION_SELECT_COLUMNS.add("referenceid"    );
        PAGINATION_SELECT_COLUMNS.add("work"    );
        PAGINATION_SELECT_COLUMNS.add("vehicleid"    );
        PAGINATION_SELECT_COLUMNS.add("consignerid"    );
        PAGINATION_SELECT_COLUMNS.add("registration_num"    );

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
    @Path("/billing/ownersummary/{ownerid}/{consignerid}")
    public AccountSummary getAccountSummaryForOwner(@PathParam("ownerid") Optional<String> ownerIdArg, @PathParam("consignerid") Optional<String> consignerIdArg)
    {
        long consignerId = Long.parseLong(consignerIdArg.get());
        long ownerid = Long.parseLong(ownerIdArg.get());
        List<List<String>> vehicleIdsRecords = dao.executeQuery("select distinct vehicleid from trip where routeid in (select routeid from route where" +
                " consignerid =  "+consignerId+" and ownerid = "+ownerid+")");
        AccountSummary accountSummary = new AccountSummary();
        for(List<String> columns : vehicleIdsRecords)
        {
            Optional<String> vehicleIdArg = Optional.of(columns.get(0));
            AccountSummary  currentVehicleAccountSummmary = getAccountSummary(vehicleIdArg,consignerIdArg);
            accountSummary.setConsignerId(consignerId);
            //accountSummary.setVehicleId(vehicleId);
            if(accountSummary.getLastBillDate().compareTo(currentVehicleAccountSummmary.getLastBillDate()) < 1)
                try {
                    accountSummary.setLastBillDate(dateTimeFormat.parse(currentVehicleAccountSummmary.getLastBillDate()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            accountSummary.setReportDate(new Date());
            accountSummary.setTotalBilled(accountSummary.getTotalBilled() + currentVehicleAccountSummmary.getTotalBilled());
            accountSummary.setTotalReceivables(accountSummary.getTotalReceivables() + currentVehicleAccountSummmary.getTotalReceivables());
            accountSummary.setTotalUnbilledEarned(accountSummary.getTotalUnbilledEarned() + currentVehicleAccountSummmary.getTotalUnbilledEarned());
            accountSummary.setTotalUnbilledReceived(accountSummary.getTotalUnbilledReceived() + currentVehicleAccountSummmary.getTotalUnbilledReceived());
            accountSummary.setOwnerName(currentVehicleAccountSummmary.getOwnerName());
            accountSummary.setConsignerName(currentVehicleAccountSummmary.getConsignerName());
        }

        return accountSummary;

    }

    @GET
    @Path("/billing/periodsummary/{vehicleid}/{consignerid}/{billingperiod}")
    public AccountSummary getAccountSummaryForPeriod(@PathParam("vehicleid") Optional<String> vehicleIdArg, @PathParam("consignerid") Optional<String> consignerIdArg, @PathParam("billingperiod") Optional<String> billingPeriodArg)
    {
        long consignerId = Long.parseLong(consignerIdArg.get());
        long vehicleId = Long.parseLong(vehicleIdArg.get());
        String billingPeriodDateStr = "1"+billingPeriodArg.get();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dMMMyyyy");
        try {
            Date date = simpleDateFormat.parse(billingPeriodDateStr);
            DateTime dateTime = new DateTime(date);
            int billingPeriodYear = dateTime.getYear();
            int billingPeriodMonth = dateTime.getMonthOfYear();
            return dao.getPastSummary(consignerId, vehicleId, billingPeriodYear, billingPeriodMonth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;


    }

    @GET
    @Path("/billing/billingperiods/{vehicleid}/{consignerid}")
    public List<String> getAvailableBillingPeriods(@PathParam("vehicleid") Optional<String> vehicleIdArg, @PathParam("consignerid") Optional<String> consignerIdArg)
    {
        long consignerId = Long.parseLong(consignerIdArg.get());
        long vehicleId = Long.parseLong(vehicleIdArg.get());
        return dao.getPreviousBillingPeriods(consignerId,vehicleId);

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
    @Path("/ownerstatement/getpaginated/{ownerid}/{consignerid}")
    public List<CreditDebit> getPaginatedRecordsOwner(@PathParam("ownerid") Optional<String> ownerIdArg, @PathParam("consignerid") Optional<String> consignerIdArg, @QueryParam("where") Optional<String> whereClause, @QueryParam("min") Optional<String> min, @QueryParam("max") Optional<String> max, @QueryParam("recordsPerPage") Optional<String> recordsPerPage) {

        long consignerId = Long.parseLong(consignerIdArg.get());
        long ownerid = Long.parseLong(ownerIdArg.get());
        List<List<String>> vehicleIdsRecords = dao.executeQuery("select distinct vehicleid from trip where routeid in (select routeid from route where" +
                " consignerid =  "+consignerId+" and ownerid = "+ownerid+")");

        List<String> vehicleIds = new ArrayList<>();
        for(List<String> recordColumns : vehicleIdsRecords)
        {
            vehicleIds.add(recordColumns.get(0));
        }
        String newWhereClause = "";
        if(whereClause.isPresent())

            newWhereClause = whereClause.get()+" and vehicleid in ("+ Joiner.on(',').join(vehicleIds)+")";
        else
            newWhereClause = " vehicleid in ("+ Joiner.on(',').join(vehicleIds)+")";
        return getPaginatedRecords(Optional.of(newWhereClause),min,max, recordsPerPage);



    }

    @GET
    @Path("/settlement/{vehicleid}/{consignerid}")
    public Object performSettlementAsOnDate(@PathParam("vehicleid") Optional<String> vehicleIdArg, @PathParam("consignerid") Optional<String> consignerIdArg) {
        long consignerId = Long.parseLong(consignerIdArg.get());
        long vehicleId = Long.parseLong(vehicleIdArg.get());
        Status status = new Status();

        VehicleDao vehicleDao = new VehicleDao();
        OwnerDao ownerDao = new OwnerDao();

        Vehicle vehicle = (Vehicle)vehicleDao.getRecords(" vehicleid = "+vehicleId).get(0);
        Owner owner = (Owner)ownerDao.getRecords(" ownerid = "+vehicle.getOwnerid()).get(0) ;

        List<List<String>> consignerRecords = vehicleDao.executeQuery(" select name from consigner where consignerid = "+consignerId);
        String consignerName = consignerRecords.get(0).get(0);


        try {
            dao.performBillingTillDate(consignerId, vehicleId);

            // Send notification

            Notification notification = new Notification();

            notification.setMessagetitle(owner.getName()+" के गाडी का सेटलमेंट ");
            notification.setMessagetext(owner.getName()+" के  गाडी नंबर " + vehicle.getRegistration_num()+" का "+consignerName+" के साथ हिसाब  हो गया है , पूरा हिसाब देखने के लिए डाउनलोड बटन दबाएं ");
            //notification.setUserid(8);
            //notification.setUsertype("OWNER");
            notification.setNotificationtime(new Date());
           // notification.setMessageid(1);
           // notification.setImage_url("http://34.66.81.100:8080/mtm/resources/images/vehiclegeneraldocument/2?rand=0.3692373930824564");
            notification.setFile_url(downloadStatementBaseURL+"/"+vehicleId+"/"+consignerId);
            notification.setNotification_type("VEHICLE_BILLING_DONE");

            DataNotification dataNotification = new DataNotification();
            dataNotification.setData(notification);
            //dataNotification.setToken("czQ6NYKATGGuQjnpAX6MHy:APA91bGHoK_CuXx9ZqArrObJDIIr1Qj0A7U2kKx0WIoUZy7L4MIB-l9su6NFAL6z9xK33Px8uUkjgozCF3k7Z-6j2UZvTYkbQyEZ6h9L-dYrSaO57emGwa8aJMcI2HlIfsGp5b5noKx5");
            // dataNotification.setMessageType("NOTIFICATION_CHALLAN_UPLOAD");
            com.mtm.beans.dto.Message message = new com.mtm.beans.dto.Message();
            message.setMessage(dataNotification);

            FCMNotificationSender.send(message,vehicleId,vehicle.getOwnerid(),consignerId, UserType.OWNER, true);


            status.setMessage("SUCCESS");
            status.setReturnCode(0);


        } catch (Exception e) {
            status.setReturnCode(1);
            status.setMessage("FAILURE");
            e.printStackTrace();
        }

        return status;
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
            creditDebit.setRegistration_num(record.get(7));
            creditDebit.setRowid(Long.parseLong(record.get(8)));
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
                final java.nio.file.Path generatedPDFPath = Paths.get(PDFGeneratorUtil.generate(vehicleId,consignerId, null, null));

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

    @GET
    @Produces("application/pdf")
    @Path("/statement/downloadfiltered/{vehicleid}/{consignerid}")
    @Timed
    public Response downloadStatementForDateRange(@PathParam("vehicleid") Optional<String> vehicleIdArg, @PathParam("consignerid") Optional<String> consignerIdArg , @QueryParam("fromDate") Optional<String> fromDateArg, @QueryParam("toDate") Optional<String> toDateArg)
    {
        long consignerId = Long.parseLong(consignerIdArg.get());
        long vehicleId = Long.parseLong(vehicleIdArg.get());
        String fromDate = fromDateArg.get();
        String toDate = toDateArg.get();



        try {
            final java.nio.file.Path generatedPDFPath = Paths.get(PDFGeneratorUtil.generate(vehicleId,consignerId, fromDate, toDate));

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

    @GET
    @Produces("application/pdf")
    @Path("/challans/download/{vehicleid}/{consignerid}")
    @Timed
    public Response downloadChallans(@PathParam("vehicleid") Optional<String> vehicleIdArg, @PathParam("consignerid") Optional<String> consignerIdArg)
    {
        long consignerId = Long.parseLong(consignerIdArg.get());
        long vehicleId = Long.parseLong(vehicleIdArg.get());


        try {
            final java.nio.file.Path generatedPDFPath = Paths.get(PDFGeneratorUtil.generateChallans(vehicleId,consignerId,null, null));

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

    @GET
    @Produces("application/pdf")
    @Path("/challans/downloadfiltered/{vehicleid}/{consignerid}")
    @Timed
    public Response downloadChallansForDateRange(@PathParam("vehicleid") Optional<String> vehicleIdArg, @PathParam("consignerid") Optional<String> consignerIdArg, @QueryParam("fromDate") Optional<String> fromDateArg, @QueryParam("toDate") Optional<String> toDateArg)
    {
        long consignerId = Long.parseLong(consignerIdArg.get());
        long vehicleId = Long.parseLong(vehicleIdArg.get());
        String fromDate = fromDateArg.get();
        String toDate = toDateArg.get();


        try {
            final java.nio.file.Path generatedPDFPath = Paths.get(PDFGeneratorUtil.generate(vehicleId,consignerId, fromDate, toDate));

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

    @GET
    @Path("/infotainment/{vehicleid}")
    public Object getInfotainment(@PathParam("vehicleid") Optional<String> vehicleIdArg) {

        long vehicleId = Long.parseLong(vehicleIdArg.get());
        VehicleInfotainment vehicleInfotainment = null;
        try {
            VehicleDao vehicleDao = new VehicleDao();
            OwnerDao ownerDao = new OwnerDao();

            Vehicle vehicle = (Vehicle)vehicleDao.getRecords(" vehicleid = "+vehicleId).get(0);
            Owner owner = (Owner)ownerDao.getRecords(" ownerid = "+vehicle.getOwnerid()).get(0) ;


            double current_profit_standing = dao.getCurrentProfitStanding(vehicleId);
            vehicleInfotainment = new VehicleInfotainment();
            vehicleInfotainment.setCurrent_profit_standing(current_profit_standing);
            vehicleInfotainment.setVehicleid(vehicleId);
            vehicleInfotainment.setOwnerName(owner.getName());

            String averageMonthlyEarnedReceivedQuery ="select avg(earned_total), avg(received_total) from (\n" +
                    "\n" +
                    "select period_year, period_month, sum(earned_total) as earned_total, sum(received_total) as received_total from (\n" +
                    " select  year(eventtime) as period_year, month(eventtime) as period_month, sum(case when erid like 'E%' then amount else 0 end) as earned_total , sum(case when erid like 'T%' then amount else 0 end) received_total from earned_received_hist where\n" +
                    " billingid in (select billingid from monthly_billing where  vehicleid = "+vehicleId+" )\n" +
                    " group by year(eventtime), month(eventtime)\n" +
                    " union all   select year(starttime)  as period_year, month(starttime) as period_month, sum((a.work_done*b.rate)) as earned_total, 0 as received_total from trip a inner join route b on a.routeid = b.routeid where (a.billingid is null or a.billingid =0 ) and  a.vehicleid = "+vehicleId+" group by  year(starttime), month(starttime)\n" +
                    "union all\n" +
                    "select year(txn_date) as period_year, month(txn_date) as period_month ,0 as earned_total,  sum(amount) as received_total from txn  where  (billingid is null or billingid =0 ) and  vehicleid = "+vehicleId+" and upper(txn_type) in (1,2,3,4,5,6,7,8,9)  group by  year(txn_date), month(txn_date)\n" +
                    "     )inn2 group by period_year, period_month) inn";
            List<List<String>> averageMonthlyEarnedReceivedRecords = dao.executeQuery(averageMonthlyEarnedReceivedQuery);
            if(averageMonthlyEarnedReceivedRecords.size() > 0  )
            {
                double averageMonthlyEarned = 0d;
                double averageMonthlyReceived = 0d;

                if(averageMonthlyEarnedReceivedRecords.get(0).get(0) !=null)
                    averageMonthlyEarned = Double.parseDouble(averageMonthlyEarnedReceivedRecords.get(0).get(0));
                if(averageMonthlyEarnedReceivedRecords.get(0).get(1) !=null)
                    averageMonthlyReceived = Double.parseDouble(averageMonthlyEarnedReceivedRecords.get(0).get(1));

                vehicleInfotainment.setAverageMonthlyExpense(averageMonthlyReceived);
                vehicleInfotainment.setAverageMonthlyRevenue(averageMonthlyEarned);
                vehicleInfotainment.setAverageMonthlyIncome(averageMonthlyEarned - averageMonthlyReceived);

            }

            double ownerExpenseAmount = dao.getOwnerTotalExpense(vehicleId);
            vehicleInfotainment.setOwnerTotalExpense(ownerExpenseAmount);




        } catch (Exception e) {
            e.printStackTrace();

        }

        return vehicleInfotainment;
    }


    }



