package com.mtm.beans.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * Created by Admin on 2/24/2019.
 */


public class Vehicle extends Record {

    @JsonProperty
    public long getDriverid() {
        return driverid;
    }

    public void setDriverid(long driverId) {
        this.driverid = driverId;
    }

    @JsonProperty
    public long getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(long ownerId) {
        this.ownerid = ownerId;
    }

    @JsonProperty
    public int getModel_num() {
        return model_num;
    }

    public void setModel_num(int model_num) {
        this.model_num = model_num;
    }

    @JsonProperty
    public long getVehicleid() {
        return vehicleid;
    }

    public void setVehicleid(long vehicleid) {
        this.vehicleid = vehicleid;
    }

    @JsonProperty
    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    @JsonProperty
    public String getRegistration_num() {
        return registration_num;
    }

    public void setRegistration_num(String registration_num) {
        this.registration_num = registration_num;

    }
    public Vehicle(int model, String type, int driverId, String registrationNumber, long ownerId) {
        this.model_num = model;
        this.vehicle_type = type;
        this.driverid = driverId;
        this.registration_num = registrationNumber;
        this.ownerid = ownerId;
    }
    public Vehicle()
    {

    }

    @JsonProperty
    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {

        if(image_url!=null)
        this.image_url = image_url;
    }
    private String image_url ="default.jpg";
    private int model_num;


    private String vehicle_type;
    private long driverid;
    private String registration_num;
    private long ownerid;
    private long vehicleid;

}
