package com.mtm.beans.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by Admin on 2/24/2019.
 */
public class VehicleRecord extends Record {
    @JsonProperty
    public int getDriverId() {
        return driverid;
    }

    public void setDriverId(int driverId) {
        this.driverid = driverId;
    }

    @JsonProperty
    public long getOwnerId() {
        return ownerid;
    }

    public void setOwnerId(long ownerId) {
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
    public int getVehicleid() {
        return vehicleid;
    }

    public void setVehicleid(int vehicleid) {
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
    public VehicleRecord( int model, String type, int driverId, String registrationNumber, long ownerId) {
        this.model_num = model;
        this.vehicle_type = type;
        this.driverid = driverId;
        this.registration_num = registrationNumber;
        this.ownerid = ownerId;
        record = new ArrayList();
        record.add(null);
        record.add(registrationNumber);
        record.add(ownerId);
        record.add(driverId);
        record.add(model);
        record.add(type);


    }
    public VehicleRecord()
    {

    }

    private int model_num;


    private String vehicle_type;
    private int driverid;
    private String registration_num;
    private long ownerid;
    private int vehicleid;
}
