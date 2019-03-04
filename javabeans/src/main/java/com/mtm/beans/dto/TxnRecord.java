package com.mtm.beans.dto;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Admin on 3/4/2019.
 */
public class TxnRecord extends Record {
    public long getTxnid() {
        return txnid;
    }

    public void setTxnid(long txnid) {
        this.txnid = txnid;
    }

    public long getDriverid() {
        return driverid;
    }

    public void setDriverid(long driverid) {
        this.driverid = driverid;
    }

    public long getVehicleid() {
        return vehicleid;
    }

    public void setVehicleid(long vehicleid) {
        this.vehicleid = vehicleid;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTxn_type() {
        return txn_type;
    }

    public void setTxn_type(String txn_type) {
        this.txn_type = txn_type;
    }

    public Date getTxn_date() {
        return txn_date;
    }

    public void setTxn_date(Date txn_date) {
        this.txn_date = txn_date;
    }

    public long getConsignerid() {
        return consignerid;
    }

    public void setConsignerid(long consignerid) {
        this.consignerid = consignerid;
    }

    public String getTxn_mode() {
        return txn_mode;
    }

    public void setTxn_mode(String txn_mode) {
        this.txn_mode = txn_mode;
    }

    public TxnRecord(long txnid, long driverid, long vehicleid, double amount, String txn_type, Date txn_date, long consignerid, String txn_mode) {
        this.txnid = txnid;
        this.driverid = driverid;
        this.vehicleid = vehicleid;
        this.amount = amount;
        this.txn_type = txn_type;
        this.txn_date = txn_date;
        this.consignerid = consignerid;
        this.txn_mode = txn_mode;
        record = new ArrayList();
        record.add(null);
        record.add(driverid);
        record.add(vehicleid);
        record.add(amount);
        record.add(txn_type);
        record.add(txn_date);
        record.add(consignerid);
        record.add(txn_mode);

    }

    private long txnid;
    private long driverid;
    private long vehicleid;
    private double amount;
    private String txn_type;
    private Date txn_date;
    private long consignerid;
    private String txn_mode;
}
