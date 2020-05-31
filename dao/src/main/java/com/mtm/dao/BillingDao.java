package com.mtm.dao;

import com.google.api.client.util.Joiner;
import com.mtm.beans.AmountAndDate;
import com.mtm.beans.TxnType;
import com.mtm.beans.dto.CreditDebit;
import com.mtm.beans.dto.Record;
import com.mtm.beans.dto.Vehicle;
import com.mtm.beans.dto.VehicleWork;
import com.mtm.dao.connection.DatabaseAccessDao;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Admin on 7/14/2019.
 */
public class BillingDao extends AbstractDao {
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

private Dao dao = new DatabaseAccessDao();
private Dao ownerDao = new OwnerDao();
private Dao vehicleDao = new VehicleDao();
private DecimalFormat decimalFormat = new DecimalFormat("#,###,##0.00");

    public String getPendingSettlementMeessage(long consignerid)
    {

        HashMap<Long, AmountAndDate> previousTotalBilledMap = getPreviousBilledAmounts(consignerid);
        HashMap<Long, AmountAndDate> unbilledEarningsMap = getUnbilledEarnings(consignerid);
        HashMap<Long, AmountAndDate> unibilledReceivedMap = getUnibilledReceived(consignerid);

        // Combine all the keys
        Set<Long> vehicleIds = new HashSet<>();
        vehicleIds.addAll(previousTotalBilledMap.keySet());
        vehicleIds.addAll(unbilledEarningsMap.keySet());
        vehicleIds.addAll(unibilledReceivedMap.keySet());

        Map<Long,AmountAndDate> vehicleIdsHavingPendingSettlement = new HashMap<>();
        Map<Long, AmountAndDate> ownersNeedingSettlement = new HashMap<>();
        Map<Long, String> ownerSettlementMessage = new HashMap<>();


        for(Long vehicleid : vehicleIds)
        {
            double previousTotalBilled = 0.0;
            double unbilledEarnings = 0.0;
            double unbilledReceived = 0.0;
            // Calculate total
            if(previousTotalBilledMap.get(vehicleid)!=null)
             previousTotalBilled = previousTotalBilledMap.get(vehicleid).getAmount();
            if(unbilledEarningsMap.get(vehicleid)!=null)
                unbilledEarnings = unbilledEarningsMap.get(vehicleid).getAmount();
            if(unibilledReceivedMap.get(vehicleid)!=null)
                unbilledReceived = unibilledReceivedMap.get(vehicleid).getAmount();
            double totalPending = previousTotalBilled + unbilledEarnings - unbilledReceived;
            if(totalPending > 0) {
                AmountAndDate amountAndDate = new AmountAndDate();
                if(unbilledReceived > 0)

                    amountAndDate.setDate(unibilledReceivedMap.get(vehicleid).getDate());
                else
                    amountAndDate.setDate(previousTotalBilledMap.get(vehicleid).getDate());
                amountAndDate.setAmount(totalPending);
                vehicleIdsHavingPendingSettlement.put(vehicleid, amountAndDate);
            }

        }

        if(vehicleIds.size() ==0)
            return null;

        for(Map.Entry<Long, AmountAndDate> entry : vehicleIdsHavingPendingSettlement.entrySet())
        {
            long vehicleid = entry.getKey();
            AmountAndDate vehiclePendingAmount = entry.getValue();
            String ownerIdQuery = "select ownerid, name , contact from owner where ownerid in (select ownerid from vehicle where vehicleid = "+vehicleid+" )";
            List<List<String>> results = ownerDao.executeQuery(ownerIdQuery);
            long ownerid = Long.parseLong(results.get(0).get(0));
            String ownerName = results.get(0).get(1);
            String ownerContact = results.get(0).get(2);

            if(ownersNeedingSettlement.get(ownerid)==null)
            {
                ownersNeedingSettlement.put(ownerid,vehiclePendingAmount);
                ownerSettlementMessage.put(ownerid, ownerName +" has pending "+decimalFormat.format(vehiclePendingAmount.getAmount())+ " on you till date "+vehiclePendingAmount.getDate());
            }
            else
            {
                double total = ownersNeedingSettlement.get(ownerid).getAmount() + vehiclePendingAmount.getAmount();
                Date date = ownersNeedingSettlement.get(ownerid).getDate().compareTo(vehiclePendingAmount.getDate()) > 0 ?ownersNeedingSettlement.get(ownerid).getDate():vehiclePendingAmount.getDate();
                AmountAndDate amountAndDate = new AmountAndDate();
                amountAndDate.setAmount(total);
                amountAndDate.setDate(date);
                ownersNeedingSettlement.put(ownerid,amountAndDate);
                ownerSettlementMessage.put(ownerid, ownerName +" (contact no "+ownerContact+") has pending "+decimalFormat.format(total)+ " on you till date "+date);
            }
        }

        return Joiner.on('\n').join(ownerSettlementMessage.values());

    }



    public double getTotalReceivableAmount(long consignerid, long vehicleid)
    {

        double previousTotalBilled = getPreviousBilledAmount(consignerid,vehicleid).getAmount();
        double unbilledEarnings = getUnbilledEarnings(consignerid,vehicleid);
        double unbilledReceived = getUnibilledReceived(consignerid,vehicleid);
        return previousTotalBilled + unbilledEarnings - unbilledReceived;

    }

    public HashMap<Long,AmountAndDate> getUnibilledReceived(long consignerid)
    {
        double unbilledReceived = 0;
        HashMap<Long,AmountAndDate> vehicleAmountAndDateMap = new HashMap<>();
        String unbilledReceivedQuery = "select sum(amount) as amount_received, max(txn_date), vehicleid  from txn  where (billingid is null or billingid =0 ) and  consignerid ="+consignerid+" and upper(txn_type) in (1,2,3,4,5,6,7,8,9) group by vehicleid" ;
        List<List<String>> results = dao.executeQuery(unbilledReceivedQuery);
        if(results==null || results.size()==0 || results.get(0)==null)
            unbilledReceived = 0;
        else     if (results.get(0).get(0) != null)
        {
            for(List<String> result : results)
            {
                AmountAndDate amountAndDate = new AmountAndDate();
                long vehicleid = Long.parseLong(result.get(2));
                amountAndDate.setAmount(amountAndDate.getAmount()+Double.parseDouble(result.get(0)));
                try {
                    amountAndDate.setDate(dateFormat.parse(result.get(1)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                amountAndDate.setVehicleid(vehicleid);
                vehicleAmountAndDateMap.put(vehicleid,amountAndDate);
            }
        }

        return vehicleAmountAndDateMap;
    }


    public double getUnibilledReceived(long consignerid, long vehicleid)
    {
        double unbilledReceived = 0;
        String unbilledReceivedQuery = "select sum(amount) as amount_received from txn  where (billingid is null or billingid =0 ) and  vehicleid="+vehicleid+" and consignerid ="+consignerid+" and upper(txn_type) in (1,2,3,4,5,6,7,8,9)" ;
        List<List<String>> results = dao.executeQuery(unbilledReceivedQuery);
        if(results==null || results.size()==0 || results.get(0)==null)
            unbilledReceived = 0;
        else     if (results.get(0).get(0) != null)
            unbilledReceived = Double.parseDouble(results.get(0).get(0));

        return unbilledReceived;
    }

    public double getUnbilledEarnings(long consignerid, long vehicleid)
    {
        String unbilledEarningsQuery = "select sum((a.work_done*b.rate)) as amount_receivable from trip a inner join route b on a.routeid = b.routeid where (a.billingid is null or a.billingid =0 ) and  a.vehicleid="+vehicleid+" and b.consignerid ="+consignerid ;
        double unbilledPayable = 0;
        List<List<String>> results = dao.executeQuery(unbilledEarningsQuery);
        if(results==null || results.size()==0 || results.get(0)==null)
            unbilledPayable = 0;
        else if(results.get(0).get(0)!=null)
            unbilledPayable = Double.parseDouble(results.get(0).get(0));

        return unbilledPayable;

    }

    public HashMap<Long,AmountAndDate> getUnbilledEarnings(long consignerid)
    {
        HashMap<Long,AmountAndDate> vehicleAmountAndDateMap = new HashMap<>();
        String unbilledEarningsQuery = "select  sum((a.work_done*b.rate)) as amount_receivable, max(a.starttime) , a.vehicleid from trip a inner join route b on a.routeid = b.routeid where (a.billingid is null or a.billingid =0 ) and b.consignerid ="+consignerid+" group by a.vehicleid" ;
        double unbilledPayable = 0;
        List<List<String>> results = dao.executeQuery(unbilledEarningsQuery);
        if(results==null || results.size()==0 || results.get(0)==null)
            unbilledPayable = 0;
        else {
            for(List<String> result : results)
            {
                AmountAndDate amountAndDate = new AmountAndDate();
                long vehicleid = Long.parseLong(result.get(2));
                amountAndDate.setAmount(amountAndDate.getAmount()+Double.parseDouble(result.get(0)));
                try {
                    amountAndDate.setDate(dateFormat.parse(result.get(1)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                amountAndDate.setVehicleid(vehicleid);
                vehicleAmountAndDateMap.put(vehicleid,amountAndDate);
            }
        }

        return vehicleAmountAndDateMap;

    }

    public AmountAndDate getPreviousBilledAmount(long consignerid, long vehicleid)
    {
        AmountAndDate amountAndDate = new AmountAndDate();
        double previousTotalBilled = 0;
        String previousTotalBilledQuery = "select a.amount, a.billingdate from monthly_billing a where a.billingdate = (select max(billingdate) from monthly_billing where consignerid="+consignerid+" and vehicleid ="+vehicleid+")";
        List<List<String>> results = dao.executeQuery(previousTotalBilledQuery);
        if(results==null || results.size()==0 || results.get(0)==null || results.get(0).get(0)==null) {
            amountAndDate.setAmount(0.0d);
        }
        else {
            amountAndDate.setAmount(Double.parseDouble(results.get(0).get(0)));
            try {
                amountAndDate.setDate(dateFormat.parse(results.get(0).get(1)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return  amountAndDate;
    }

    public HashMap<Long,AmountAndDate> getPreviousBilledAmounts(long consignerid)
    {
        HashMap<Long,AmountAndDate> vehicleAmountAndDateMap = new HashMap<>();
        String previousTotalBilledQuery = "select a.amount, a.billingdate, a.vehicleid from " +
                "(select consignerid, amount, vehicleid, billingdate, row_number() over(partition by consignerid,vehicleid order by year, month desc) as row_rank from " +
                "monthly_billing where consignerid =  " +consignerid + " "+
                ") a  where a.row_rank = 1";
        List<List<String>> results = dao.executeQuery(previousTotalBilledQuery);
        if(results==null || results.size()==0 || results.get(0)==null || results.get(0).get(0)==null) {
            //amountAndDate.setAmount(0.0d);
        }
        else {
            for(List<String> result : results)
            {
                AmountAndDate amountAndDate = new AmountAndDate();
                long vehicleid = Long.parseLong(result.get(2));
                amountAndDate.setAmount(amountAndDate.getAmount()+Double.parseDouble(result.get(0)));
                try {
                    amountAndDate.setDate(dateFormat.parse(result.get(1)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                amountAndDate.setVehicleid(vehicleid);
                vehicleAmountAndDateMap.put(vehicleid,amountAndDate);
            }

        }
        return  vehicleAmountAndDateMap;
    }

    public void performMonthlyBilling(long consignerid, long vehicleid)
    {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String billDate = dateFormat.format(date);
        Calendar calendar =  Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1 ; // calender's month is 0 indexed
        long ownerid ;


        String hasBillingCompleted = "select count(*) from monthly_billing where consignerid = "+consignerid+" and vehicleid = "+vehicleid+" and year = "+year+" and month = "+month;
        List<List<String>> records = vehicleDao.executeQuery(hasBillingCompleted);
        if(records.size() > 0)
        {
            if(Integer.parseInt(records.get(0).get(0)) > 0)
            { System.out.println(" Billing has already been done for "+consignerid+" "+vehicleid+" "+year+" "+month);
                return;}
        }

        String monthlyBillQuery = "insert into  monthly_billing (vehicleid, consignerid, billingdate, year, month) values("+vehicleid+","+consignerid+",'"+billDate+"',"+year+","+month+")";

        Connection connection = null;
        long billingid;


        System.out.println("Insert Query >> "+monthlyBillQuery);
        try {
            connection =  dao.getConnection();
            Statement statement = connection.createStatement();

            ownerid = ((Vehicle)(vehicleDao.getRecords(" vehicleid = "+vehicleid).get(0))).getOwnerid();
            statement.executeUpdate(monthlyBillQuery);
            String query = "select LAST_INSERT_ID()";
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            billingid = rs.getLong(1);
            String tripHistoryInsertQuery ="insert into  trip_detailed_hist \n" +
                    "select b.vehicleid AS vehicleid,b.routeid AS routeid,b.driverid AS driverid,c.consignerid AS consignerid,a.source AS source,a.destination AS destination,\n" +
                    "a.rate AS rate,a.rate_type AS rate_type,(case when (a.rate_type = 'per\\n\\n\\ntonne') then concat(b.work_done,' tonnes, ',a.rate,' per tonne') \n" +
                    "when (a.rate_type = 'per km') then concat(b.work_done,' KMs, ',a.rate,' \\nper \\nKM') when (a.rate_type = 'per month') \n" +
                    "then concat(b.work_done,' Months, ',a.rate,'per Month') else concat(b.work_done) end) AS work_done_detail,b.starttime AS starttime,b.endtime AS endtime,\n" +
                    "c.consigner_name AS consigner_name,b.material_name AS material_name,d.name AS drivername,c.image_url AS image_url,b.tripid AS tripid,e.ownerid AS ownerid,\n" +
                    "e.registration_num AS registration_num,"+ billingid +" AS billingid,b.work_done AS work_done, b.expected_fuel_consumed as expected_fuel_consumed from ((((route a join trip b on((a.routeid = b.routeid))) join \n" +
                    "owner_consigner c on(((a.consignerid = c.consignerid) and (a.ownerid = c.ownerid)))) join vehicle e on((e.vehicleid = b.vehicleid))) join \n" +
                    "vehicledriver d on((e.driverid = d.driverid)))  where b.vehicleid = " + vehicleid +" and a.consignerid = "+ consignerid;

            String earnedReceivedHistoryInsertQuery = "insert into earned_received_hist \n" +
                    "select v.registration_num AS registration_num,(t.work_done * r.rate) AS amount,t.starttime AS eventtime,'earned' \n" +
                    "AS type,t.challanid AS referenceid,0 AS work,concat('E',t.tripid) AS erid,t.vehicleid AS vehicleid,r.consignerid AS consignerid, "+billingid+" as billingid from \n" +
                    "((trip t join route r on((t.routeid = r.routeid))) join vehicle v on((t.vehicleid = v.vehicleid))) where t.vehicleid = "+vehicleid+" and r.consignerid = "+consignerid+" union all select v2.registration_num AS \n" +
                    "registration_num,tx.amount AS amount,tx.txn_date AS txn_Date,'received' AS type,tx.txnid AS txnid,tx.txn_type AS txn_type,concat('T',tx.txnid)\n" +
                    " AS erid,tx.vehicleid AS vehicleid,tx.consignerid AS consignerid, "+billingid+" as billingid from (txn tx join vehicle v2 on((tx.vehicleid = v2.vehicleid)))\n" +
                    " where tx.vehicleid = "+vehicleid + " and tx.consignerid = "+consignerid;

            double totalReceivableAmount = getTotalReceivableAmount(consignerid,vehicleid);
            String updateBillDetails = "update monthly_billing set amount = "+totalReceivableAmount +" where billingid = "+billingid;
            String updateTripsBillingIdQuery = "delete from trip  where vehicleid ="+vehicleid+" and routeid in (select routeid from route where consignerid = "+consignerid+" and ownerid = "+ownerid+") ";
            String updateTxnsBillingIdQuery = "delete from txn where  vehicleid ="+vehicleid+" and consignerid ="+consignerid;
            statement.executeUpdate(tripHistoryInsertQuery);
            statement.executeUpdate(earnedReceivedHistoryInsertQuery);
            statement.executeUpdate(updateBillDetails);
            statement.executeUpdate(updateTripsBillingIdQuery);
            statement.executeUpdate(updateTxnsBillingIdQuery);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void populateTotals(VehicleWork vehicleWork, long consignerid, long vehicleid)
    {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        double previousTotalBilled = getPreviousBilledAmount(consignerid,vehicleid).getAmount();
        double unbilledEarnings = getUnbilledEarnings(consignerid,vehicleid);
        double unbilledReceived = getUnibilledReceived(consignerid,vehicleid);
        double totalReceivables =  previousTotalBilled + unbilledEarnings - unbilledReceived;
        double currentUnbilled = unbilledEarnings - unbilledReceived;
        vehicleWork.setTotalReceivables(numberFormat.format(totalReceivables));
        vehicleWork.setCurrentUnbilled(numberFormat.format(currentUnbilled));
        vehicleWork.setPreviousBilled(numberFormat.format(previousTotalBilled));


    }


    public VehicleWork downloadStatement(long vehicleid, long consignerid, boolean includeBilledRecords, String fromDate, String toDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String consignerNameQuery = "select consigner_name from owner_consigner where consignerid = "+consignerid;
        String ownerNameQuery = "select name from owner where ownerid in (select ownerid from vehicle where vehicleid = "+vehicleid+")";
        String vehcileDetailQuery = "select registration_num, vehicle_type from vehicle where vehicleid = "+vehicleid;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String reportDate = simpleDateFormat.format(new Date());

        List<List<String>> results = dao.executeQuery(consignerNameQuery);
        List<List<String>> ownerNameresult = dao.executeQuery(ownerNameQuery);
        String ownerName = ownerNameresult.get(0).get(0);
        String consignerName = results.get(0).get(0);
        results = dao.executeQuery(vehcileDetailQuery);
        String registration_num = results.get(0).get(0);
        String vehicle_type = results.get(0).get(1);

        String currentDateFilterClause= "";
        String currentTxnFilterClause= "";
        String  billedDateFilterClause = "";

        if(StringUtils.isNotEmpty(fromDate))
        {
            if(StringUtils.isNotEmpty(toDate)) {
                currentDateFilterClause = " and t.starttime >= '" + fromDate + "' and t.starttime <= '" + toDate + "' ";
                currentTxnFilterClause = " and tx.txn_date >= '" + fromDate + "' and tx.txn_date <= '" + toDate + "' ";
                billedDateFilterClause = " and eventtime >= '" + fromDate + "' and eventtime <= '" + toDate + "' ";
            }
            else
            {
                currentDateFilterClause = " and t.starttime >= '" + fromDate+"' ";
                currentTxnFilterClause = " and tx.txn_date >= '" + fromDate + "' " ;
                billedDateFilterClause = " and eventtime >= '" + fromDate + "' ";
            }

        }

        String earningsAndReceivedQuery =  null;
        earningsAndReceivedQuery = "select  v.registration_num, (t.work_done * r.rate) as amount , t.starttime, 'earned' as type, t.challanid, '0' as work, t.image_url, t.tripid  from trip t inner join route r on t.routeid = r.routeid  inner join vehicle v on t.vehicleid = v.vehicleid where (t.billingid is null or t.billingid =0 ) and t.vehicleid = " + vehicleid + " and r.consignerid = " + consignerid + currentDateFilterClause+ " union all select v.registration_num , tx.amount , tx.txn_Date, 'received' as type , tx.txnid , tx.txn_type, '' as image_url, tx.tripid  from txn tx inner join vehicle v on tx.vehicleid=v.vehicleid where (tx.billingid is null or tx.billingid =0 ) and  tx.vehicleid =" + vehicleid + " and tx.consignerid = " + consignerid+currentTxnFilterClause;
        results = dao.executeQuery(earningsAndReceivedQuery);

        if(includeBilledRecords) {
            String billedRecordsQuery = "select registration_num, amount, eventtime, type,referenceid, work,'' as image_url,erid from earned_received_hist where vehicleid =" + vehicleid + " and consignerid = " + consignerid+billedDateFilterClause;
            results.addAll(dao.executeQuery(billedRecordsQuery));
        }


        List<CreditDebit> creditDebits = new ArrayList<CreditDebit>();
        VehicleWork vehicleWork = new VehicleWork();
        vehicleWork.setVehicleid(vehicleid);
        vehicleWork.setConsignerid(consignerid);
        vehicleWork.setConsignername(consignerName);
        vehicleWork.setRegistration_num(registration_num);
        vehicleWork.setVehicletype(vehicle_type);
        vehicleWork.setBusinessDetails(creditDebits);
        vehicleWork.setOwnername(ownerName);
        vehicleWork.setReportdate(reportDate);
        populateTotals(vehicleWork,consignerid,vehicleid);

        if(results==null || results.size()==0 || results.get(0)==null)
            return vehicleWork;

        for(List<String> record: results)
        {

           CreditDebit creditDebit = new CreditDebit();
           creditDebit.setAmount(Double.parseDouble(record.get(1)));
           creditDebit.setDate(dateFormat.parse(record.get(2)));
           creditDebit.setType(record.get(3));
           creditDebit.setId(record.get(4));
           creditDebit.setTxn_type(TxnType.getFromValue(Integer.parseInt(record.get(5))));
           creditDebit.setChallanImageURL(record.get(6));
           creditDebit.setTripid(Long.parseLong(record.get(7)));

            creditDebit.setRegistration_num(registration_num);


           creditDebits.add(creditDebit);
        }
        vehicleWork.setBusinessDetails(creditDebits);
        return vehicleWork;
    }


    public List<Record> getConvertedRecords(String whereClause) {
        return null;
    }
}
