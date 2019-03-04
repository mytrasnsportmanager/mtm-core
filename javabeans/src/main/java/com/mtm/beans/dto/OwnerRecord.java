package com.mtm.beans.dto;

import java.security.acl.Owner;
import java.util.ArrayList;

/**
 * Created by Admin on 2/24/2019.
 */
public class OwnerRecord extends Record{

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getContact() {
        return contact;
    }

    public void setContact(long contact) {
        this.contact = contact;
    }

    private String ownerId;
    private String name;
    private String address;
    private long contact;


    public OwnerRecord(String name, String address, long contact)
    {
        this.ownerId = null; // Is an auto increment
        this.name = name;
        this.address = address;
        this.contact = contact;
        record = new ArrayList();
        record.add(null);
        record.add(name);
        record.add(address);
        record.add(contact);

    }

}
