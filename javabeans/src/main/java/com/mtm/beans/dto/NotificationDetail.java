package com.mtm.beans.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by Admin on 4/26/2020.
 */
public class NotificationDetail {


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
    public String getRowid() {
        return rowid;
    }

    public void setRowid(String rowid) {
        this.rowid = rowid;
    }

    private String rowid;

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

    @JsonProperty
    public String getMessagetext() {
        return messagetext;
    }

    public void setMessagetext(String messagetext) {
        this.messagetext = messagetext;
    }

    public void setMessageid(String messageid) {
        this.messageid = messageid;
    }

    public void setNotificationtime(String notificationtime) {
        this.notificationtime = notificationtime;
    }

    private String user_device_id;
    private String messagetitle;
    private String messagetext;

    @JsonProperty
    public String getMessageread() {
        return messageread;
    }

    public void setMessageread(String messageread) {
        this.messageread = messageread;
    }

    private String messageread;

    @JsonProperty
    public String getMessageid() {
        return messageid;
    }

    @JsonProperty
    public String getNotificationtime() {
        return notificationtime;
    }

    private String messageid;
    private String notificationtime;
    private String online;
    private String notificationread;
    private String fcmrsponsemessageid;
    private String image_url;
    private String icon_image_url;

    public String getIcon_image_url() {
        return icon_image_url;
    }

    public void setIcon_image_url(String icon_image_url) {
        this.icon_image_url = icon_image_url;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    @JsonProperty
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @JsonProperty
    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    private String userid;
    private String usertype;


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
}
