package com.mtm.beans.dto;

import java.util.ArrayList;

/**
 * Created by Admin on 3/4/2019.
 */
public class RateRecord extends Record{
    public long getRateid() {
        return rateid;
    }

    public void setRateid(long rateid) {
        this.rateid = rateid;
    }

    public long getConsignerid() {
        return consignerid;
    }

    public void setConsignerid(long consignerid) {
        this.consignerid = consignerid;
    }

    public double getRate_per_ton() {
        return rate_per_ton;
    }

    public void setRate_per_ton(double rate_per_ton) {
        this.rate_per_ton = rate_per_ton;
    }

    public String getMaterial_name() {
        return material_name;
    }

    public void setMaterial_name(String material_name) {
        this.material_name = material_name;
    }

    private long rateid        ;

    public RateRecord(long rateid, long consignerid, double rate_per_ton, String material_name) {
        this.rateid = rateid;
        this.consignerid = consignerid;
        this.rate_per_ton = rate_per_ton;
        this.material_name = material_name;
        record = new ArrayList();
        record.add(null);
        record.add(consignerid);
        record.add(rate_per_ton);
        record.add(material_name);
    }

    private long consignerid   ;
    private double rate_per_ton  ;
    private String material_name ;
}
