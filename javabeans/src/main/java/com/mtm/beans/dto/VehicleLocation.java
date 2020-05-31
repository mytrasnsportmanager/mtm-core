package com.mtm.beans.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Admin on 10/12/2019.
 */
public class VehicleLocation extends Record {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String PRIMARY_KEY_COLUMN_NAME = "vehicleid";
    private static Date defaultDate;
    static {
        try {
            defaultDate = dateFormat.parse("1970-01-01 00:01:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    @JsonProperty
    public long getVehicleid() {
        return vehicleid;
    }

    public void setVehicleid(long vehicleid) {
        this.vehicleid = vehicleid;
    }



    private long vehicleid;

    public double getLast_longitude() {
        return last_longitude;
    }

    public void setLast_longitude(double last_longitude) {
        this.last_longitude = last_longitude;
    }

    public double getLast_latitude() {
        return last_latitude;
    }

    public void setLast_latitude(double last_latitude) {
        this.last_latitude = last_latitude;
    }

    private double last_longitude;
    private double last_latitude;

    @JsonProperty
    public double getLast_speed() {
        return last_speed;
    }

    public void setLast_speed(double last_speed) {
        this.last_speed = last_speed;
    }

    @JsonProperty
    public double getLast_speed_accuracy() {
        return last_speed_accuracy;
    }

    public void setLast_speed_accuracy(double last_speed_accuracy) {
        this.last_speed_accuracy = last_speed_accuracy;
    }

    @JsonProperty
    public double getLast_bearing() {
        return last_bearing;
    }

    public void setLast_bearing(double last_bearing) {
        this.last_bearing = last_bearing;
    }

    @JsonProperty

    public double getLast_bearing_accuracy_degree() {
        return last_bearing_accuracy_degree;
    }

    public void setLast_bearing_accuracy_degree(double last_bearing_accuracy_degree) {
        this.last_bearing_accuracy_degree = last_bearing_accuracy_degree;
    }

    private double last_speed;
    private double last_speed_accuracy;
    private double last_bearing;
    private double last_bearing_accuracy_degree;


    @JsonProperty
    public double getLast_altitude() {
        return last_altitude;
    }

    public void setLast_altitude(double last_altitude) {
        this.last_altitude = last_altitude;
    }

    private double last_altitude;



    @JsonProperty
    public Date getLast_seen_at() {
        return last_seen_at;
    }

    public void setLast_seen_at(Date last_seen_at) {
        if(last_seen_at!=null )
        this.last_seen_at = last_seen_at;
    }

    private Date last_seen_at = defaultDate;
    public String getPrimaryKeyColumn()
    {
        return PRIMARY_KEY_COLUMN_NAME;
    }
}
