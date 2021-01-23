package com.mtm.service.rest.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.mtm.beans.Status;
import com.mtm.beans.UserType;
import com.mtm.beans.dto.DataNotification;
import com.mtm.beans.dto.Dispute;
import com.mtm.beans.dto.Notification;
import com.mtm.beans.dto.Txn;
import com.mtm.dao.Dao;
import com.mtm.dao.DisputeDao;
import com.mtm.dao.TxnDao;
import com.mtm.notification.FCMNotificationSender;
import com.mtm.service.rest.auth.AuthorizationHandler;
import io.dropwizard.jersey.PATCH;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import javax.ws.rs.*;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 8/16/2020.
 */
public class DisputeResource extends AbstractRestResource {

    private static Dao dao = new DisputeDao();
    public DisputeResource(Dao dao) {
        super(dao);
    }

    public DisputeResource(Dao dao, AuthorizationHandler authorizationHandlerParam) {
        super(dao, authorizationHandlerParam);
    }

    public DisputeResource()
    {
        super(dao);
    }

    @POST
    @Path("/disputes")
    public Object createDisputes(Dispute dispute)

    {
        // Add startTime
        Date date = new Date();
        DateTimeZone timeUTC = DateTimeZone.UTC;
        DateTime nowUTC = DateTime.now(timeUTC);
        dispute.setEvent_date(nowUTC.toLocalDateTime().toDate());
        return create(dispute);
    }
    @PATCH
    @Path("/disputes")
    public Object patchTransaction(Dispute record)

    {
        if(patch(record)==1)
            return new Status("SUCCESS",0,0);
        else
            return new Status("FAILED",1,0);
    }


    @GET
    @Path("/disputes")
    public Object getTxns()
    {
        return dao.getRecords("1=1");
    }


    @GET
    @Path("/disputes/search")
    public List<Object> search(@QueryParam("where") Optional<String> whereClause) {


        return get(whereClause.get());

    }

    @GET
    @Path("/disputes/{disputeid}")
    @Timed
    public List<Object> getTxn(@PathParam("disputeid") Optional<String> disputeId) {

        String whereClause = " disputeid = "+disputeId.get();
        return get(whereClause);

    }

    @DELETE
    @Path("/disputes/{disputeid}")
    @Timed
    public Object deleteTxn(@PathParam("disputeid") Optional<String> disputeId)
    {

        Status status = new Status();
        if(dao.delete(" txnid = "+disputeId.get())==1) {
            status.setMessage("SUCEESS");
            status.setReturnCode(0);

        }
        else
        {
            status.setReturnCode(1);
            status.setMessage("The request could not be processed");
        }
        return status;

    }

    private void sendNotification (Dispute dispute)
    {


        Notification notification = new Notification();
        notification.setMessagetitle(" के गाडी का सेटलमेंट ");
        //notification.setMessagetext(owner.getName()+" के  गाडी नंबर " + vehicle.getRegistration_num()+" का "+consignerName+" के साथ हिसाब  हो गया है , पूरा हिसाब देखने के लिए डाउनलोड बटन दबाएं ");
        //notification.setUserid(8);
        //notification.setUsertype("OWNER");
        notification.setNotificationtime(new Date());
        // notification.setMessageid(1);
        // notification.setImage_url("http://34.66.81.100:8080/mtm/resources/images/vehiclegeneraldocument/2?rand=0.3692373930824564");
       // notification.setFile_url(downloadStatementBaseURL+"/"+vehicleId+"/"+consignerId);
        notification.setNotification_type("VEHICLE_BILLING_DONE");

        DataNotification dataNotification = new DataNotification();
        dataNotification.setData(notification);
        //dataNotification.setToken("czQ6NYKATGGuQjnpAX6MHy:APA91bGHoK_CuXx9ZqArrObJDIIr1Qj0A7U2kKx0WIoUZy7L4MIB-l9su6NFAL6z9xK33Px8uUkjgozCF3k7Z-6j2UZvTYkbQyEZ6h9L-dYrSaO57emGwa8aJMcI2HlIfsGp5b5noKx5");
        // dataNotification.setMessageType("NOTIFICATION_CHALLAN_UPLOAD");
        com.mtm.beans.dto.Message message = new com.mtm.beans.dto.Message();
        message.setMessage(dataNotification);

       // FCMNotificationSender.send(message,vehicleId,vehicle.getOwnerid(),consignerId, UserType.OWNER, true);

    }



}
