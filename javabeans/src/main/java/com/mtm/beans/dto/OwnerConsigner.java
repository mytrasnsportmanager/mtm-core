package com.mtm.beans.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Admin on 3/13/2019.
 */
public class OwnerConsigner extends Record {

    @JsonProperty
    public long getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(long ownerid) {
        this.ownerid = ownerid;
    }

    @JsonProperty
    public long getConsignerid() {
        return consignerid;
    }

    public void setConsignerid(long consignerid) {
        this.consignerid = consignerid;
    }

    @JsonProperty
    public String getConsigner_name() {
        return consigner_name;
    }

    public void setConsigner_name(String consigner_name) {
        this.consigner_name = consigner_name;
    }

    @JsonProperty
    public char getIs_company() {
        return is_company;
    }

    public void setIs_company(char is_company) {
        this.is_company = is_company;
    }

    @JsonProperty
    public long getConsigner_contact() {
        return consigner_contact;
    }

    public void setConsigner_contact(long consigner_contact) {
        this.consigner_contact = consigner_contact;
    }

    @JsonProperty
    public int getBillingday() {
        return billingday;
    }

    public void setBillingday(int billingday) {
        this.billingday = billingday;
    }

    public OwnerConsigner()
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
    private String consigner_name    ;
    private char is_company          ;
    private long consigner_contact ;
    private int billingday;
    private long ownerid;
    private long consignerid;


}
