package com.mtm.beans.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by Admin on 2/24/2019.
 */
public class Owner extends Record{

    @JsonProperty
    public long getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(long ownerId) {
        this.ownerid = ownerId;
    }

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

    private long ownerid;
    private String name;
    private String address;
    private long contact;
    private String image_url="default.jpg";




}
