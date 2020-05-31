package com.mtm.beans;

import com.mtm.beans.dto.Record;

/**
 * Created by Admin on 5/29/2020.
 */
public class AccountantVehicle extends Record {
    public long getAccountantid() {
        return accountantid;
    }

    public void setAccountantid(long accountantid) {
        this.accountantid = accountantid;
    }

    public long getVehicleid() {
        return vehicleid;
    }

    public void setVehicleid(long vehicleid) {
        this.vehicleid = vehicleid;
    }

    private long accountantid;
    private long vehicleid;
}
