package com.mtm.beans.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Admin on 3/4/2019.
 */
public class Txn extends Record {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static Date defaultDate;
    static {
        try {
            defaultDate = dateFormat.parse("1970-01-01 00:01:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @JsonProperty
    public long getTxnid() {
        return txnid;
    }

    public void setTxnid(long txnid) {
        this.txnid = txnid;
    }

    @JsonProperty
    public long getDriverid() {
        return driverid;
    }

    public void setDriverid(long driverid) {
        this.driverid = driverid;
    }

    @JsonProperty
    public long getVehicleid() {
        return vehicleid;
    }

    public void setVehicleid(long vehicleid) {
        this.vehicleid = vehicleid;
    }

    @JsonProperty
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @JsonProperty
    public int getTxn_type() {
        return txn_type;
    }

    public void setTxn_type(int txn_type) {
        this.txn_type = txn_type;
    }

    @JsonProperty
    public Date getTxn_date() {
        return txn_date;
    }

    public void setTxn_date(Date txn_date) {
        if(txn_date!=null )
        this.txn_date = txn_date;
    }

    @JsonProperty
    public long getConsignerid() {
        return consignerid;
    }

    public void setConsignerid(long consignerid) {
        this.consignerid = consignerid;
    }

    @JsonProperty
    public String getTxn_mode() {
        return txn_mode;
    }

    public void setTxn_mode(String txn_mode) {
        this.txn_mode = txn_mode;
    }



    private long txnid;
    private long tripid;

    @JsonProperty
    public long getTripid() {
        return tripid;
    }

    public void setTripid(long tripid) {
        this.tripid = tripid;
    }

    private long driverid;
    private long vehicleid;
    private double amount;
    private int txn_type;
    private Date txn_date = defaultDate;
    private long consignerid;
    private String txn_mode;
    private long billingid;

    public long getRowid() {
        return rowid;
    }

    public void setRowid(long rowid) {
        this.rowid = rowid;
    }

    private long rowid;

    public long getBillingid() {
        return billingid;
    }

    public void setBillingid(long billingid) {
        this.billingid = billingid;
    }

    public boolean isColumnExcludedForPersistence(String columnName)
    {
        if(columnName.equalsIgnoreCase("rowid"))
        return true;
        else
            return false;
    }


}
