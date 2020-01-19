package com.mtm.dao;

import com.mtm.beans.AmountAndDate;
import com.mtm.beans.TxnType;
import com.mtm.beans.dto.CreditDebit;
import com.mtm.beans.dto.Record;
import com.mtm.beans.dto.Vehicle;
import com.mtm.beans.dto.VehicleWork;
import com.mtm.dao.connection.DatabaseAccessDao;

import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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

    public double getTotalReceivableAmount(long consignerid, long vehicleid)
    {

        double previousTotalBilled = getPreviousBilledAmount(consignerid,vehicleid).getAmount();
        double unbilledEarnings = getUnbilledEarnings(consignerid,vehicleid);
        double unbilledReceived = getUnibilledReceived(consignerid,vehicleid);
        return previousTotalBilled + unbilledEarnings - unbilledReceived;

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

    public void performMonthlyBilling(long consignerid, long vehicleid)
    {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String billDate = dateFormat.format(date);
        Calendar calendar =  Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        long ownerid ;



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
                    "e.registration_num AS registration_num,"+ billingid +" AS billingid,b.work_done AS work_done from ((((route a join trip b on((a.routeid = b.routeid))) join \n" +
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
            //statement.executeUpdate(updateTripsBillingIdQuery);
            //statement.executeUpdate(updateTxnsBillingIdQuery);

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


    public VehicleWork downloadStatement(long vehicleid, long consignerid, boolean includeBilledRecords) throws ParseException {
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

        String earningsAndReceivedQuery =  null;
        if(!includeBilledRecords)
        earningsAndReceivedQuery = "select  v.registration_num, (t.work_done * r.rate) as amount , t.starttime, 'earned' as type, t.challanid, '0' as work from trip t inner join route r on t.routeid = r.routeid  inner join vehicle v on t.vehicleid = v.vehicleid where (t.billingid is null or t.billingid =0 ) and t.vehicleid = "+vehicleid+" and r.consignerid = "+consignerid+" union all select v.registration_num , tx.amount , tx.txn_Date, 'received' as type , tx.txnid , tx.txn_type from txn tx inner join vehicle v on tx.vehicleid=v.vehicleid where (tx.billingid is null or tx.billingid =0 ) and  tx.vehicleid ="+vehicleid+" and tx.consignerid = "+consignerid;
        else
            earningsAndReceivedQuery = "select  v.registration_num, (t.work_done * r.rate) as amount , t.starttime, 'earned' as type, t.challanid, '0' as work from trip t inner join route r on t.routeid = r.routeid  inner join vehicle v on t.vehicleid = v.vehicleid where and t.vehicleid = "+vehicleid+" and r.consignerid = "+consignerid+" union all select v.registration_num , tx.amount , tx.txn_Date, 'received' as type , tx.txnid , tx.txn_type from txn tx inner join vehicle v on tx.vehicleid=v.vehicleid where tx.vehicleid ="+vehicleid+" and tx.consignerid = "+consignerid;
        results = dao.executeQuery(earningsAndReceivedQuery);

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

           creditDebits.add(creditDebit);
        }
        vehicleWork.setBusinessDetails(creditDebits);
        return vehicleWork;
    }


    public List<Record> getConvertedRecords(String whereClause) {
        return null;
    }
}
