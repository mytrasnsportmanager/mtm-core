package com.mtm.beans;

import java.util.Date;

/**
 * Created by Admin on 9/30/2019.
 */
public class AmountAndDate {
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


    public long getVehicleid() {
        return vehicleid;
    }

    public void setVehicleid(long vehicleid) {
        this.vehicleid = vehicleid;
    }

    private Date date ;
    private double amount;
    private long vehicleid;
}
