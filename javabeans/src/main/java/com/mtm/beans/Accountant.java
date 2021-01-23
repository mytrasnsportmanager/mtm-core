package com.mtm.beans;

import com.mtm.beans.dto.Record;
import com.mtm.beans.dto.User;

/**
 * Created by Admin on 5/29/2020.
 */
public class Accountant extends User {
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


    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    private String name;
    private long contact;
    private String image_url;

    public long getAccountantid() {
        return accountantid;
    }

    public void setAccountantid(long accountantid) {
        this.accountantid = accountantid;
    }

    private long accountantid;
    private long vehicleid;

    public long getVehicleid() {
        return vehicleid;
    }

    public void setVehicleid(long vehicleid) {
        this.vehicleid = vehicleid;
    }
}
