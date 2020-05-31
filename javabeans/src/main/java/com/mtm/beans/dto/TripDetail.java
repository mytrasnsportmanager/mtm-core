package com.mtm.beans.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mtm.beans.RateType;

/**
 * Created by Admin on 4/20/2019.
 */
public class TripDetail extends Record {
    @JsonProperty
    public long getVehicleid() {
        return vehicleid;
    }

    public void setVehicleid(long vehicleid) {
        this.vehicleid = vehicleid;
    }

    @JsonProperty
    public long getTripid() {
        return tripid;
    }

    public void setTripid(long tripid) {
        this.tripid = tripid;
    }

    @JsonProperty
    public long getRouteid() {
        return routeid;
    }

    public void setRouteid(long routeid) {
        this.routeid = routeid;
    }

    @JsonProperty
    public long getDriverid() {
        return driverid;
    }

    public void setDriverid(long driverid) {
        this.driverid = driverid;
    }

    @JsonProperty
    public long getConsignerid() {
        return consignerid;
    }

    public void setConsignerid(long consignerid) {
        this.consignerid = consignerid;
    }

    @JsonProperty
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @JsonProperty
    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @JsonProperty
    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    @JsonProperty
    public RateType getRateType() {
        return rateType;
    }

    public void setRateType(RateType rateType) {
        this.rateType = rateType;
    }

    @JsonProperty
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        if(startTime!=null) this.startTime = startTime;
    }

    @JsonProperty
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        if(endTime!=null) this.endTime = endTime;
    }

    @JsonProperty
    public String getConsignerName() {
        return consignerName;
    }

    public void setConsignerName(String consignerName) {
        this.consignerName = consignerName;
    }
    private long vehicleid;
    private long tripid;
    private long routeid;
    private long driverid;
    private long consignerid;
    private String source;
    private String destination;
    private float rate;
    private RateType rateType;
    private String startTime = "1970-01-01 00:01:00";
    private String endTime = "1970-01-01 00:01:00";
    private String consignerName;
    private String consigner_image_url;
    private String name_of_material;
    private String driver_name;
    private String vehicle_resistration_num;
    private long billingid;
    private String returned_with_trip;
    private String expected_fuel_consumed;

    public String getExpected_fuel_consumed() {
        return expected_fuel_consumed;
    }

    public void setExpected_fuel_consumed(String expected_fuel_consumed) {
        this.expected_fuel_consumed = expected_fuel_consumed;
    }

    public String getReturned_with_trip() {
        return returned_with_trip;
    }

    public void setReturned_with_trip(String returned_with_trip) {
        this.returned_with_trip = returned_with_trip;
    }

    @JsonProperty
    public double getWork_done() {
        return work_done;
    }

    public void setWork_done(double work_done) {
        this.work_done = work_done;
    }

    private double work_done;
    //private double wor

    @JsonProperty
    public double getRentEarned() {
        return rent_earned;
    }

    public void setRentEarned(double rent_earned) {
        this.rent_earned = rent_earned;
    }

    private double rent_earned;

    @JsonProperty
    public long getBillingid() {
        return billingid;
    }

    public void setBillingid(long billingid) {
        this.billingid = billingid;
    }



    @JsonProperty
    public String getVehicle_resistration_num() {
        return vehicle_resistration_num;
    }

    public void setVehicle_resistration_num(String vehicle_resistration_num) {
        this.vehicle_resistration_num = vehicle_resistration_num;
    }



    @JsonProperty
    public long getRowid() {
        return rowid;
    }

    public void setRowid(long rowid) {
        this.rowid = rowid;
    }

    private long rowid;

    public long getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(long ownerid) {
        this.ownerid = ownerid;
    }

    private long ownerid;

    @JsonProperty
    public String getWork_done_detail() {
        return work_done_detail;
    }

    public void setWork_done_detail(String work_done_detail) {
        this.work_done_detail = work_done_detail;
    }

    private String work_done_detail;

    @JsonProperty
    public String getConsigner_image_url() {
        return consigner_image_url;
    }

    public void setConsigner_image_url(String consigner_image_url) {
        this.consigner_image_url = consigner_image_url;
    }

    @JsonProperty
    public String getName_of_material() {
        return name_of_material;
    }

    public void setName_of_material(String name_of_material) {
        this.name_of_material = name_of_material;
    }

    @JsonProperty
    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }



}
