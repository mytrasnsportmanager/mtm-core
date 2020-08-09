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
    private double expected_mileage;

    @JsonProperty
    public double getExpected_mileage() {
        return expected_mileage;
    }

    public void setExpected_mileage(double expected_mileage) {
        this.expected_mileage = expected_mileage;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public long getDriver_contact() {
        return driver_contact;
    }

    public void setDriver_contact(long driver_contact) {
        this.driver_contact = driver_contact;
    }

    private String driver_name;
    private long driver_contact;

    private double kms_without_trip;

    @JsonProperty
    public String getKms_without_trip_remarks() {
        return kms_without_trip_remarks;
    }

    public void setKms_without_trip_remarks(String kms_without_trip_remarks) {
        this.kms_without_trip_remarks = kms_without_trip_remarks;
    }

    private String kms_without_trip_remarks;



    @JsonProperty
    public double getKms_without_trip() {
        return kms_without_trip;
    }

    public void setKms_without_trip(double kms_without_trip) {
        this.kms_without_trip = kms_without_trip;
    }

    private double current_fuel_level;

    @JsonProperty
    public double getCurrent_fuel_level() {
        return current_fuel_level;
    }

    public void setCurrent_fuel_level(double current_fuel_level) {
        this.current_fuel_level = current_fuel_level;
    }

    public boolean isColumnExcludedForPersistence(String columnName)
    {
        if(columnName.equalsIgnoreCase("kms_without_trip") || columnName.equalsIgnoreCase("kms_without_trip_remarks") )
            return true;
        else
            return false;
    }
}
