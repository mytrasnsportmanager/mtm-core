package com.mtm.beans;

/**
 * Created by Admin on 5/29/2020.
 */
public class AccountantChangeRequest {

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

    public long getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(long ownerid) {
        this.ownerid = ownerid;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    private String name;
    private String passphrase;
    private long contact;
    private long ownerid;
    private String image_url;

    public long getVehicleid() {
        return vehicleid;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public void setPassphrase(String passphrase) {
        this.passphrase = passphrase;
    }

    public void setVehicleid(long vehicleid) {

        this.vehicleid = vehicleid;
    }

    private long vehicleid;
}
