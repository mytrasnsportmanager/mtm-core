package com.mtm.beans.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by Admin on 8/8/2020.
 */
public class VehicleFuelConsumption extends Record {

    @JsonProperty
    public long getVehicleid() {
        return vehicleid;
    }

    public void setVehicleid(long vehicleid) {
        this.vehicleid = vehicleid;
    }

    @JsonProperty
    public Date getConsumption_date() {
        return consumption_date;
    }

    public void setConsumption_date(Date consumption_date) {
        this.consumption_date = consumption_date;
    }

    @JsonProperty
    public double getConsumption_amt() {
        return consumption_amt;
    }

    public void setConsumption_amt(double consumption_amt) {
        this.consumption_amt = consumption_amt;
    }

    @JsonProperty
    public long getTripid() {
        return tripid;
    }

    public void setTripid(long tripid) {
        this.tripid = tripid;
    }

    @JsonProperty
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    long vehicleid;
    Date consumption_date;
    double consumption_amt;
    long tripid;
    String remarks;



}
