package com.mtm.beans.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by Admin on 4/10/2020.
 */
public class Notification extends Record {


    @JsonProperty
    public long getMessageid() {
        return messageid;
    }

    public void setMessageid(long messageid) {
        this.messageid = messageid;
    }
    @JsonProperty
    public Date getNotificationtime() {
        return notificationtime;
    }

    public void setNotificationtime(Date notificationtime) {
        this.notificationtime = notificationtime;
    }
    @JsonProperty
    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }
    @JsonProperty
    public String getNotificationread() {
        return notificationread;
    }

    public void setNotificationread(String notificationread) {
        this.notificationread = notificationread;
    }
    @JsonProperty
    public String getFcmrsponsemessageid() {
        return fcmrsponsemessageid;
    }

    public void setFcmrsponsemessageid(String fcmrsponsemessageid) {
        this.fcmrsponsemessageid = fcmrsponsemessageid;
    }

    @JsonProperty
    public String getUser_device_id() {
        return user_device_id;
    }

    public void setUser_device_id(String user_device_id) {
        this.user_device_id = user_device_id;
    }

    @JsonProperty
    public String getMessagetitle() {
        return messagetitle;
    }

    public void setMessagetitle(String messagetitle) {
        this.messagetitle = messagetitle;
    }



    private String user_device_id;
    private String messagetitle;

    @JsonProperty
    public String getMessagetext() {
        return messagetext;
    }

    public void setMessagetext(String messagetext) {
        this.messagetext = messagetext;
    }

    private String messagetext;

    @JsonProperty
    public String getMessageread() {
        return messageread;
    }

    public void setMessageread(String messageread) {
        this.messageread = messageread;
    }

    private String messageread;
    private long messageid;
    private Date notificationtime;
    private String online;
    private String notificationread;
    private String fcmrsponsemessageid;
    @JsonProperty
    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    @JsonProperty
    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    private long userid;
    private String usertype;
    private String image_url;


    @JsonProperty
    public String getNotification_type() {
        return notification_type;
    }

    public void setNotification_type(String notification_type) {
        this.notification_type = notification_type;
    }

    @JsonProperty
    public String getFile_url() {
        return file_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    private String notification_type;

    private String file_url;

    public String getIcon_image_url() {
        return icon_image_url;
    }

    public void setIcon_image_url(String icon_image_url) {
        this.icon_image_url = icon_image_url;
    }

    private String icon_image_url;

    @JsonProperty
    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
