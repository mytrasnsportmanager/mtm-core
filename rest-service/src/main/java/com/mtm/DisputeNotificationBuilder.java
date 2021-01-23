package com.mtm;

import com.mtm.beans.DisputeType;
import com.mtm.beans.UserType;
import com.mtm.beans.dto.*;
import com.mtm.dao.*;

import java.util.List;

/**
 * Created by Admin on 8/16/2020.
 */
public abstract class DisputeNotificationBuilder {

    private  OwnerDao ownerDao = new OwnerDao();
    private  ConsignerDao consignerDao= new ConsignerDao();
    private  TripDao tripDao = new TripDao();
    private  TxnDao txnDao = new TxnDao();

    private long ownerid;
    private long consignerid;
    private String ownerName;
    private String consignerName;
    private String tripDescription;




    private StringBuffer notificationMessage ;


    protected StringBuffer build(Dispute dispute)
    {
        DisputeType disputeType = DisputeType.getFrom(dispute.getDispute_type());

       switch (disputeType)
       {
           case TRIP:
               UserType userType = UserType.valueOf(dispute.getRaised_by_user_type());
               switch (userType)
               {
                   case CONSIGNER:





                       break;
               }



               break;

           case TXN:

               break;

       }

       return notificationMessage;
    }


private void populateOwnerAndConsignerDetails(Dispute dispute)
{
    Trip trip = (Trip)tripDao.getConvertedRecords("tripid = "+dispute.getTripid());

    String ownerDetailsQuery = "select userid, device_id from user where usertype = 'OWNER' and userid in ( select ownerid from vehicle where vehicleid ="+trip.getVehicleid()+" )";
    String consignerDetailsQuery = "select userid, name from user where usertype ='CONSIGNER' and userid in (select consignerid from route where routeid ="+trip.getRouteid()+") ";

    List<List<String>> consignerDetailsRecord = consignerDao.executeQuery(consignerDetailsQuery);
    consignerid = Long.parseLong(consignerDetailsRecord.get(0).get(0));
    consignerName = consignerDetailsRecord.get(0).get(1);
    List<List<String>> ownerDetailsRecord = ownerDao.executeQuery(ownerDetailsQuery);
    ownerid = Long.parseLong(ownerDetailsRecord.get(0).get(0));
    ownerName = ownerDetailsRecord.get(0).get(1);


}

private void setTripDescription(Trip trip)
{
    RouteDao routeDao = new RouteDao();
    Route route = (Route)routeDao.getConvertedRecords("routeid = "+trip.getRouteid());
    tripDescription = route.getSource()+" to "+route.getDestination()+" "+trip.getWork_done()+"* "+route.getRate()+" "+route.getRate_type();
}

//private void set


}
