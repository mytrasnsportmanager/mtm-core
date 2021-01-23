package com.mtm.beans.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by Admin on 3/4/2019.
 */

public class Consigner extends User{

    @JsonProperty
    public long getConsignerid() {
        return consignerid;
    }
    public void setConsignerid(long consignerid) {
        this.consignerid = consignerid;
    }
    public Consigner()
    {

    }
    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        if(image_url!=null)
        this.image_url = image_url;
    }
    private String image_url ="default.jpg";

    private long consignerid        ;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getContact() {
        return contact;
    }

    public void setContact(long contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private long contact;
    private String address;
}
