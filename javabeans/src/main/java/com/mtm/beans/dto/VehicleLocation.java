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
