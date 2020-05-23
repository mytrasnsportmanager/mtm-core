package com.mtm.scheduled;

import com.google.common.util.concurrent.AbstractScheduledService;
import com.mtm.beans.dto.OwnerConsigner;
import com.mtm.dao.BillingDao;
import com.mtm.dao.OwnerConsignerDao;
import com.mtm.dao.TripDao;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Admin on 4/11/2020.
 */
public class BillingService extends AbstractScheduledService {
    OwnerConsignerDao ownerConsignerDao = new OwnerConsignerDao();
    BillingDao billingDao = new BillingDao();
    TripDao tripDao = new TripDao();
    boolean currentIterationInProgress = false;

    @Override
    protected synchronized  void runOneIteration() throws Exception {

        if(currentIterationInProgress)
            return;
        currentIterationInProgress = true;
        String minMaxQuery = "select min(ownerid), max(ownerid) from owner_consigner";
        List<List<String>> minMaxRecords = ownerConsignerDao.executeQuery(minMaxQuery);
        int batchSize = 2;
        long  minOwnerId = Long.parseLong(minMaxRecords.get(0).get(0));
        long  maxOwnerId = Long.parseLong(minMaxRecords.get(0).get(1));
        long currentMaxId = 0;


        if((maxOwnerId - minOwnerId) > batchSize)
            currentMaxId = minOwnerId + batchSize;
        else
            currentMaxId = maxOwnerId;

        while(currentMaxId <= maxOwnerId)
        {
            System.out.println("Executing for "+minOwnerId+" and "+currentMaxId);
            List<Object> currentBatchRecords = getRecords(minOwnerId,currentMaxId);
            performBilling(currentBatchRecords);
            minOwnerId = currentMaxId;
            if(maxOwnerId - currentMaxId > batchSize)
            currentMaxId+=batchSize;
            else
            {
                currentBatchRecords = getRecords(minOwnerId,maxOwnerId + 1);
                performBilling(currentBatchRecords);
                System.out.println("Executing for "+minOwnerId+" and "+maxOwnerId + 1);
               // Post this step, while loop terminates
                currentMaxId = maxOwnerId + 1;
            }


        }


        currentIterationInProgress = false;



    }



    private List<Object> getRecords (long minOwnerid, long maxOwnerid)
    {
       return ownerConsignerDao.getRecords(" ownerid >= "+minOwnerid+" and ownerid < "+maxOwnerid);
    }

    private void performBilling(List<Object> currentBatchOwnerConsignerRecords)
    {
        for (Object ownerConsignerRecord : currentBatchOwnerConsignerRecords)
        {
            System.out.println("Performing billing for "+ownerConsignerRecord);
            OwnerConsigner ownerConsigner = (OwnerConsigner)ownerConsignerRecord;
            int billingDay = ownerConsigner.getBillingday();
            DateTimeZone timeZoneIndia = DateTimeZone.forID( "Asia/Kolkata" );
            DateTime nowIndia = DateTime.now(timeZoneIndia);
            int currentDayOfMonth = nowIndia.getDayOfMonth();

            // Handle Feb case
            int currentMonthOfYear = nowIndia.getMonthOfYear();
            if(currentMonthOfYear==2 && (billingDay==29 || billingDay==30)) {
                billingDay = 28;
            }


            if(currentDayOfMonth >= billingDay)
            {
                String vehicleListQuery = " select distinct vehicleid from trip where routeid in (select routeid from route where ownerid = "+ownerConsigner.getOwnerid()+" and consignerid = "+ownerConsigner.getConsignerid() +")";
                List<List<String>> vehicleListRecords = tripDao.executeQuery(vehicleListQuery);
                if(vehicleListRecords.size() !=0)
                    billingDao.performMonthlyBilling(ownerConsigner.getConsignerid(),Long.parseLong(vehicleListRecords.get(0).get(0)));
            }




            //billingDao.performMonthlyBilling(ownerConsigner.);

        }
    }




    @Override
    protected Scheduler scheduler() {
        return Scheduler.newFixedDelaySchedule(120, 10, TimeUnit.MINUTES);
    }
}
