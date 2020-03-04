package com.mtm.beans.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by Admin on 3/4/2019.
 */
public class Route extends Record{

    @JsonProperty
    public long getRouteid() {
        return routeid;
    }

    public void setRouteid(long routeid) {
        this.routeid = routeid;
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
    public long getConsignerid() {
        return consignerid;
    }

    public void setConsignerid(long consignerid) {
        this.consignerid = consignerid;
    }

    @JsonProperty
    public String getSource_district() {
        return source_district;
    }

    public void setSource_district(String source_district) {
        this.source_district = source_district;
    }

    @JsonProperty
    public String getSource_state() {
        return source_state;
    }

    public void setSource_state(String source_state) {
        this.source_state = source_state;
    }

    @JsonProperty
    public String getDestination_district() {
        return destination_district;
    }

    public void setDestination_district(String destination_district) {
        this.destination_district = destination_district;
    }

    @JsonProperty
    public String getDestination_state() {
        return destination_state;
    }

    public void setDestination_state(String destination_state) {
        this.destination_state = destination_state;
    }

    @JsonProperty
    public double getSource_longitude() {
        return source_longitude;
    }

    public void setSource_longitude(double source_longitude) {
        this.source_longitude = source_longitude;
    }

    @JsonProperty
    public double getSource_latitude() {
        return source_latitude;
    }

    public void setSource_latitude(double source_latitude) {
        this.source_latitude = source_latitude;
    }

    @JsonProperty
    public double getDestination_longitude() {
        return destination_longitude;
    }

    public void setDestination_longitude(double destination_longitude) {
        this.destination_longitude = destination_longitude;
    }

    @JsonProperty
    public double getDestination_latitude() {
        return destination_latitude;
    }

    public void setDestination_latitude(double destination_latitude) {
        this.destination_latitude = destination_latitude;
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
    private long ownerid;
    private double rate;
    private String rate_type ;
    private String consigner_name;
    private String consigner_contact;
    private String consigner_image;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    private double distance;


    @JsonProperty
    public String getConsigner_name() {
        return consigner_name;
    }

    public void setConsigner_name(String consigner_name) {
        this.consigner_name = consigner_name;
    }

    @JsonProperty
    public String getConsigner_contact() {
        return consigner_contact;
    }

    public void setConsigner_contact(String consigner_contact) {
        this.consigner_contact = consigner_contact;
    }

    @JsonProperty
    public String getConsigner_image() {
        return consigner_image;
    }

    public void setConsigner_image(String consigner_image) {
        this.consigner_image = consigner_image;
    }

    @JsonProperty
    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @JsonProperty
    public String getRate_type() {
        return rate_type;
    }

    public void setRate_type(String rate_type) {
        this.rate_type = rate_type;
    }


    @JsonProperty
    public long getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(long ownerid) {
        this.ownerid = ownerid;
    }


}
