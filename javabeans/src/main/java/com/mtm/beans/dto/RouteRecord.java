package com.mtm.beans.dto;

import java.util.ArrayList;

/**
 * Created by Admin on 3/4/2019.
 */
public class RouteRecord extends Record{

    public long getRouteid() {
        return routeid;
    }

    public void setRouteid(long routeid) {
        this.routeid = routeid;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public long getConsignerid() {
        return consignerid;
    }

    public void setConsignerid(long consignerid) {
        this.consignerid = consignerid;
    }

    public String getSource_district() {
        return source_district;
    }

    public void setSource_district(String source_district) {
        this.source_district = source_district;
    }

    public String getSource_state() {
        return source_state;
    }

    public void setSource_state(String source_state) {
        this.source_state = source_state;
    }

    public String getDestination_district() {
        return destination_district;
    }

    public void setDestination_district(String destination_district) {
        this.destination_district = destination_district;
    }

    public String getDestination_state() {
        return destination_state;
    }

    public void setDestination_state(String destination_state) {
        this.destination_state = destination_state;
    }

    public double getSource_longitude() {
        return source_longitude;
    }

    public void setSource_longitude(double source_longitude) {
        this.source_longitude = source_longitude;
    }

    public double getSource_latitude() {
        return source_latitude;
    }

    public void setSource_latitude(double source_latitude) {
        this.source_latitude = source_latitude;
    }

    public double getDestination_longitude() {
        return destination_longitude;
    }

    public void setDestination_longitude(double destination_longitude) {
        this.destination_longitude = destination_longitude;
    }

    public double getDestination_latitude() {
        return destination_latitude;
    }

    public void setDestination_latitude(double destination_latitude) {
        this.destination_latitude = destination_latitude;
    }

    public RouteRecord(long routeid, String source, String destination, long consignerid, String source_district, String source_state, String destination_district, String destination_state, double source_longitude, double source_latitude, double destination_longitude, double destination_latitude) {
        this.routeid = routeid;
        this.source = source;
        this.destination = destination;
        this.consignerid = consignerid;
        this.source_district = source_district;
        this.source_state = source_state;
        this.destination_district = destination_district;
        this.destination_state = destination_state;
        this.source_longitude = source_longitude;
        this.source_latitude = source_latitude;
        this.destination_longitude = destination_longitude;
        this.destination_latitude = destination_latitude;
        record = new ArrayList();
        record.add(null);
        record.add(source);
        record.add(destination);
        record.add(consignerid);
        record.add(source_district);
        record.add(source_state);
        record.add(destination_district);
        record.add(destination_state);
        record.add(source_longitude);
        record.add(source_latitude);
        record.add(destination_longitude);
        record.add(destination_latitude);

    }

    private long routeid                 ;
    private String source                ;
    private String destination           ;
    private long consignerid           ;
    private String source_district       ;
    private String source_state          ;
    private String destination_district  ;
    private String destination_state     ;
    private double source_longitude      ;
    private double source_latitude       ;
    private double destination_longitude ;
    private double destination_latitude  ;
}
