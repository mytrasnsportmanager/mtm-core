package com.mtm.notification;

import com.mtm.beans.NotificationType;
import com.mtm.beans.UserType;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Admin on 8/22/2020.
 */
public class TripNotificatSender extends NotificationBuilder {

    public void notifyConsigner(long tripid, UserType toUserType )
    {

        this.tripid = tripid;
        this.eventToUserType = toUserType;
        super.build();
        if(StringUtils.isNotEmpty(toUser.getDevice_id()))
            return;

        send();



    }

    private String getRateTypeWithoutPrefix(String rateType)
    {
        return rateType.replace("per ","");
    }

    public void notifyOwnerAndConsigner(long tripid )
    {

        notifyConsigner(tripid);
        notifyOwner(tripid);


    }

    private void notifyConsigner(long tripid)
    {
        this.tripid = tripid;
        this.eventToUserType = UserType.CONSIGNER;
        this.notificationType = NotificationType.TRIP_ADDED;
        super.build();

        if(StringUtils.isEmpty(toUser.getDevice_id()))
            return;
        setTripAddedMessage(false);
        send();
    }

    private void notifyOwner(long tripid)
    {


        this.tripid = tripid;
        this.eventToUserType = UserType.OWNER;
        this.notificationType = NotificationType.TRIP_ADDED;
        super.build();
        if(accountant==null)
            return;

        if(StringUtils.isEmpty(toUser.getDevice_id()))
            return;
        setTripAddedMessage(true);
        send();
    }




    private void setTripAddedMessage(boolean forOwnerOnly)
    {
        double amount = route.getRate()  * trip.getWork_done();
        StringBuffer message = new StringBuffer();
        if(forOwnerOnly)
        message.append("अकाउंटेंट  "+accountant.getName()+" ने  गाडी नंबर "+vehicle.getRegistration_num());
       else
           message.append("ओनर "+owner.getName()+" ने  गाडी नंबर "+vehicle.getRegistration_num());

        message.append(" के लिए  "+route.getSource()+" से  "+route.getDestination()+" का ट्रिप  ऐड किया है");
        message.append("\n किया गया काम "+trip.getWork_done()+" "+getRateTypeWithoutPrefix(route.getRate_type())+" है ");
        message.append(" \n इस रूट का तय रेट "+route.getRate()+" "+route.getRate_type()+" है ");
        message.append(" \n ----------------------------------------- ");
        message.append(" \n इस रूट का तय रेट "+route.getRate()+" "+route.getRate_type()+" है ");

        message.append("\n इस ट्रिप का किराया "+amount+" rs गाडी ओनर को देय है ");

        if(forOwnerOnly)
        message.append("* * किसी भी आपत्ति के लिए ट्रांसपोर्टर से संपर्क करें  ");
        else
            message.append("* किसी भी आपत्ति के लिए गाडी ओनर से संपर्क करें ");

        notification.setMessagetext(message.toString());
        notification.setMessagetitle(" गाडी "+vehicle.getRegistration_num()+" के लिए ट्रिप ऐड  किया गया है ");
    }


}
