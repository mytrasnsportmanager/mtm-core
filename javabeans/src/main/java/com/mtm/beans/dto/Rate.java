package com.mtm.beans.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

/**
 * Created by Admin on 3/4/2019.
 */
public class Rate extends Record{
    @JsonProperty
    public long getRateid() {
        return rateid;
    }

    public void setRateid(long rateid) {
        this.rateid = rateid;
    }

    @JsonProperty
    public long getConsignerid() {
        return consignerid;
    }

    public void setConsignerid(long consignerid) {
        this.consignerid = consignerid;
    }

    @JsonProperty
    public double getRate_per_ton() {
        return rate_per_ton;
    }

    public void setRate_per_ton(double rate_per_ton) {
        this.rate_per_ton = rate_per_ton;
    }

    @JsonProperty
    public String getMaterial_name() {
        return material_name;
    }

    public void setMaterial_name(String material_name) {
        this.material_name = material_name;
    }

    private long rateid        ;
    private long consignerid   ;
    private double rate_per_ton  ;
    private String material_name ;
}
