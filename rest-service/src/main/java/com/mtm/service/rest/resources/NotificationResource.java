package com.mtm.service.rest.resources;

import com.mtm.beans.Status;
import com.mtm.beans.dto.Notification;
import com.mtm.beans.dto.NotificationDetail;
import com.mtm.beans.dto.TripDetail;
import com.mtm.beans.dto.User;
import com.mtm.dao.Dao;
import com.mtm.dao.NotificationDao;
import com.mtm.dao.TripDao;
import com.mtm.service.rest.RestResourceType;
import com.mtm.service.rest.auth.TripAuthorizationHandler;
import io.dropwizard.jersey.PATCH;
import org.apache.commons.codec.digest.DigestUtils;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 4/11/2020.
 */
public class NotificationResource extends AbstractRestResource {
    private static NotificationDao dao = new NotificationDao();
    private static final String PAGINATION_VIEW_NAME = "notification";
    private static final String PAGINATION_VIEW_ID_COLUMN="messageid";
    private static final String PAGINATION_VIEW_ENTITY_ID_COLUMN="concat(usertype,userid)";
    private static final String PAGINATION_VIEW_ORDER_COLUMN="notificationtime";
    private static final List<String> PAGINATION_SELECT_COLUMNS = new ArrayList<String>();
    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public NotificationResource(Dao dao) {
        super(dao);
    }
    static
    {
        PAGINATION_SELECT_COLUMNS.add("user_device_id"        );
        PAGINATION_SELECT_COLUMNS.add("messagetitle"          );
        PAGINATION_SELECT_COLUMNS.add("messagetext"         );
        PAGINATION_SELECT_COLUMNS.add("notificationtime"      );
        PAGINATION_SELECT_COLUMNS.add("messageid"           );
        PAGINATION_SELECT_COLUMNS.add("messageread"           );
        PAGINATION_SELECT_COLUMNS.add("image_url"           );
        PAGINATION_SELECT_COLUMNS.add("icon_image_url"           );
        PAGINATION_SELECT_COLUMNS.add("notification_type"           );
        PAGINATION_SELECT_COLUMNS.add("file_url"           );

    }
    public NotificationResource() {
        //super(dao, new TripAuthorizationHandler());
        super(dao);
        restResourceType = RestResourceType.NOTIFICATION;
        super.setPaginationSelectColumns(PAGINATION_SELECT_COLUMNS);
        super.setPaginationViewName(PAGINATION_VIEW_NAME);
        super.setPaginationViewIdColumn(PAGINATION_VIEW_ID_COLUMN);
        super.setPaginationViewEntityIdColumn(PAGINATION_VIEW_ENTITY_ID_COLUMN);
        super.setPaginationViewOrderColumn(PAGINATION_VIEW_ORDER_COLUMN);

    }

    @GET
    @Path("/notifications/getpaginated")
    public List<NotificationDetail> getPaginatedRecords() {


        List<List<String>> records = super.getPaginated(whereClause,min,max,recordsPerPage);

        List<NotificationDetail> notifications = new ArrayList<NotificationDetail>();

        if(records.size()==0)
            return notifications;

        if(records==null || records.size()==0)
            return notifications;
        for(List<String> record : records) {

            NotificationDetail notification = new NotificationDetail();
            notification.setUser_device_id(record.get(0));
            notification.setMessagetitle(record.get(1));
            notification.setMessagetext(record.get(2));
            notification.setNotificationtime(record.get(3));
            notification.setMessageid(record.get(4));
            notification.setMessageread(record.get(5));
            notification.setImage_url(record.get(6));
            notification.setIcon_image_url(record.get(7));
            notification.setNotification_type(record.get(8));
            notification.setFile_url(record.get(9));
            notification.setRowid(record.get(10));
            notifications.add(notification);

        }
        return notifications;
        }

    @PATCH
    @Path("/notifications")
    public Object updateUser(Notification record)

    {

        if(dao.patch(record)==1)
            return new Status("SUCCESS",0,0);
        else
            return new Status("FAILED",1,0);
    }

}
