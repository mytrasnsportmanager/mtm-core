package com.mtm.beans.dto;

import java.util.ArrayList;

/**
 * Created by Admin on 3/4/2019.
 */
public class TripRecord extends Record{

    public long getTripid() {
        return tripid;
    }

    public void setTripid(long tripid) {
        this.tripid = tripid;
    }

    public long getVehicleid() {
        return vehicleid;
    }

    public void setVehicleid(long vehicleid) {
        this.vehicleid = vehicleid;
    }

    public long getRouteid() {
        return routeid;
    }

    public void setRouteid(long routeid) {
        this.routeid = routeid;
    }

    public long getDriverid() {
        return driverid;
    }

    public void setDriverid(long driverid) {
        this.driverid = driverid;
    }

    public long getRateid() {
        return rateid;
    }

    public void setRateid(long rateid) {
        this.rateid = rateid;
    }

    public double getWeight_of_material() {
        return weight_of_material;
    }

    public void setWeight_of_material(double weight_of_material) {
        this.weight_of_material = weight_of_material;
    }

    private long tripid               ;
    private long vehicleid            ;

    public TripRecord(long tripid, long vehicleid, long routeid, long driverid, long rateid, double weight_of_material) {
        this.tripid = tripid;
        this.vehicleid = vehicleid;
        this.routeid = routeid;
        this.driverid = driverid;
        this.rateid = rateid;
        this.weight_of_material = weight_of_material;
        record = new ArrayList();
        record.add(null);
        record.add(vehicleid);
        record.add(routeid);
        record.add(driverid);
        record.add(rateid);
        record.add(weight_of_material);
    }

    private long routeid              ;
    private long driverid             ;
    private long rateid               ;
    private double weight_of_material ;
}
