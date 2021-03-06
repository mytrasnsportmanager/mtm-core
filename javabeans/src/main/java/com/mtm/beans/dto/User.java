package com.mtm.beans.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mtm.beans.UserType;

/**
 * Created by Admin on 7/15/2019.
 */
public class User extends Record {

    public User(UserType usertype) {
        this.usertype = usertype.toString();
    }

    public User()
    {

    }

    @JsonProperty
    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String userType) {
        this.usertype = userType;
    }

    private String usertype;

    @JsonProperty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @JsonProperty
    public long getContact() {
        return contact;
    }

    public void setContact(long contact) {
        this.contact = contact;
    }


    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        if(image_url!=null)
            this.image_url = image_url;
    }

    @JsonProperty
    public long getUserid() {
        return userid;
    }

    @JsonProperty
    public void setUserid(long userid) {
        this.userid = userid;
    }

    private long userid;
    private String name;
    private String address;
    private long contact;
    private String passphrase;
    private String externaluid;
    private String registered_by;

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    private String device_id;

    public String getRegistered_by() {
        return registered_by;
    }

    public void setRegistered_by(String registered_by) {
        this.registered_by = registered_by;
    }

    public String getExternaluid() {
        return externaluid;
    }

    public void setExternaluid(String externaluid) {
        this.externaluid = externaluid;
    }

    @JsonProperty
    public String getPassphrase() {
        return passphrase;
    }

    public void setPassphrase(String passphrase) {
        this.passphrase = passphrase;
    }

    private String image_url="default.jpg";

    public String getPrimaryKeyColumn()
    {
        return "userid";
    }



}
