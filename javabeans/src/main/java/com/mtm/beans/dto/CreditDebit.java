package com.mtm.beans.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Admin on 7/16/2019.
 */
@XmlRootElement
public class CreditDebit extends Record implements Serializable{
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
    @XmlElement
    public String getDate() {


        return dateFormat.format(date);
    }

    @XmlElement
    public void setDate(Date date) {
        if(date!=null )
            this.date = date;

    }



    @JsonProperty
    public double getAmount() {
        return amount;
    }

    @XmlElement
    public void setAmount(double amount) {
        this.amount = amount;
    }

    @JsonProperty
    public String getType() {
        return type;
    }

    @XmlElement
    public void setType(String type) {
        this.type = type;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTxn_type() {
        return txn_type;
    }

    public void setTxn_type(String txn_type) {
        this.txn_type = txn_type;
    }

    private String id;
    private String txn_type;
    private long vehicleid;
    private long consignerid;
    private Date date;
    private double amount;
    private String type;


    public long getVehicleid() {
        return vehicleid;
    }

    public void setVehicleid(long vehicleid) {
        this.vehicleid = vehicleid;
    }

    public long getConsignerid() {
        return consignerid;
    }

    public void setConsignerid(long consignerid) {
        this.consignerid = consignerid;
    }


    public long getRowid() {
        return rowid;
    }

    public void setRowid(long rowid) {
        this.rowid = rowid;
    }
    @XmlElement
    public String getChallanImageURL() {
        return challanImageURL;
    }

    public void setChallanImageURL(String challanImageURL) {
        this.challanImageURL = challanImageURL;
    }

    private long rowid;
    private String challanImageURL;
    private long tripid;

    public long getTripid() {
        return tripid;
    }

    public void setTripid(long tripid) {
        this.tripid = tripid;
    }
}
