package com.mtm.beans.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Admin on 3/4/2019.
 */
public class Trip extends Record implements Serializable{

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
    public long getTripid() {
        return tripid;
    }

    public void setTripid(long tripid) {
        this.tripid = tripid;
    }

    @JsonProperty
    public long getVehicleid() {
        return vehicleid;
    }

    public void setVehicleid(long vehicleid) {
        this.vehicleid = vehicleid;
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
    public long getRateid() {
        return rateid;
    }

    public void setRateid(long rateid) {
        this.rateid = rateid;
    }

    @JsonProperty
    public Date getStarttime() {
        return startTime;
    }

    public void setStarttime(Date startTime) {

        if(startTime!=null ) this.startTime = startTime;
    }

    @JsonProperty
    public Date getEndtime() {
        return endTime;
    }

    public void setEndtime(Date endTime) {
        if(endTime!=null ) this.endTime = endTime;
    }

    private long tripid               ;
    private long vehicleid            ;



    private long routeid              ;
    private long driverid             ;
    private long rateid               ;

    public long getChallanid() {
        return challanid;
    }

    public void setChallanid(long challanid) {
        this.challanid = challanid;
    }

    private long challanid;

    @JsonProperty
    public long getBillingid() {
        return billingid;
    }

    public void setBillingid(long billingid) {
        this.billingid = billingid;
    }

    private long billingid;

    public double getExpected_fuel_consumed() {
        return expected_fuel_consumed;
    }

    public void setExpected_fuel_consumed(double expected_fuel_consumed) {
        this.expected_fuel_consumed = expected_fuel_consumed;
    }

    private double expected_fuel_consumed;

    @JsonProperty
    public float getWork_done() {
        return work_done;
    }

    public void setWork_done(float work_done) {
        this.work_done = work_done;
    }

    private float work_done ;
    private String returned_with_trip;

    @JsonProperty
    public String getReturned_with_trip() {
        return returned_with_trip;
    }

    public void setReturned_with_trip(String returned_with_trip) {
        this.returned_with_trip = returned_with_trip;
    }

    private Date startTime = defaultDate;
    private Date endTime = defaultDate;


    @JsonProperty
    public String getMaterial_name() {
        return material_name;
    }

    public void setMaterial_name(String material_name) {
        this.material_name = material_name;
    }

    private String material_name;

    @JsonProperty
    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        if(image_url != null)
        this.image_url = image_url;
    }

    private String image_url="default.jpg";
}
