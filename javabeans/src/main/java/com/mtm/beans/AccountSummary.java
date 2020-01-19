package com.mtm.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Admin on 9/30/2019.
 */
public class AccountSummary {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @JsonProperty
    public long getVehicleId() {
        return vehicleId;
    }

    @JsonProperty
    public void setVehicleId(long vehicleId) {
        this.vehicleId = vehicleId;
    }

    @JsonProperty
    public long getConsignerId() {
        return consignerId;
    }

    public void setConsignerId(long consignerId) {
        this.consignerId = consignerId;
    }

    @JsonProperty
    public String getReportDate() {

        if(reportDate != null)
            return dateFormat.format(reportDate);
        return "";
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    @JsonProperty
    public String getLastBillDate() {
        if(lastBillDate != null)
           return dateFormat.format(lastBillDate);
        return "";
    }

    public void setLastBillDate(Date lastBillDate) {
        this.lastBillDate = lastBillDate;
    }

    @JsonProperty
    public double getTotalReceivables() {
        return totalReceivables;
    }

    public void setTotalReceivables(double totalReceivables) {
        this.totalReceivables = totalReceivables;
    }


    private long vehicleId;
    private long consignerId;
    private Date reportDate;
    private Date lastBillDate;
    private double totalReceivables;
    private double totalBilled;
    private String ownerName;
    private String consignerName;

    @JsonProperty
    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @JsonProperty
    public String getConsignerName() {
        return consignerName;
    }

    public void setConsignerName(String consignerName) {
        this.consignerName = consignerName;
    }

    @JsonProperty
    public double getTotalBilled() {
        return totalBilled;
    }

    public void setTotalBilled(double totalBilled) {
        this.totalBilled = totalBilled;
    }

    @JsonProperty
    public double getTotalUnbilledEarned() {
        return totalUnbilledEarned;
    }

    public void setTotalUnbilledEarned(double totalUnbilledEarned) {
        this.totalUnbilledEarned = totalUnbilledEarned;
    }

    @JsonProperty
    public double getTotalUnbilledReceived() {
        return totalUnbilledReceived;
    }

    public void setTotalUnbilledReceived(double totalUnbilledReceived) {
        this.totalUnbilledReceived = totalUnbilledReceived;
    }

    private double totalUnbilledEarned;
    private double totalUnbilledReceived;



}
