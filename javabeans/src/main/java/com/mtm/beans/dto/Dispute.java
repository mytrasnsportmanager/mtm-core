package com.mtm.beans.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by Admin on 8/16/2020.
 */
public class Dispute extends Record {

    @JsonProperty
    public long getDisputeid() {
        return disputeid;
    }

    public void setDisputeid(long disputeid) {
        this.disputeid = disputeid;
    }

    @JsonProperty
    public long getTripid() {
        return tripid;
    }

    public void setTripid(long tripid) {
        this.tripid = tripid;
    }

    @JsonProperty
    public long getTxnid() {
        return txnid;
    }

    public void setTxnid(long txnid) {
        this.txnid = txnid;
    }

    @JsonProperty
    public String getDispute_status() {
        return dispute_status;
    }

    public void setDispute_status(String dispute_status) {
        this.dispute_status = dispute_status;
    }

    @JsonProperty
    public Date getEvent_date() {
        return event_date;
    }

    public void setEvent_date(Date event_date) {
        this.event_date = event_date;
    }

    private long disputeid;
    private long tripid;
    private long txnid;
    private String dispute_status;
    private Date event_date;

    public long getRaised_by() {
        return raised_by;
    }

    public void setRaised_by(long raised_by) {
        this.raised_by = raised_by;
    }

    public String getRaised_by_user_type() {
        return raised_by_user_type;
    }

    public void setRaised_by_user_type(String raised_by_user_type) {
        this.raised_by_user_type = raised_by_user_type;
    }

    public String getDispute_type() {
        return dispute_type;
    }

    public void setDispute_type(String dispute_type) {
        this.dispute_type = dispute_type;
    }

    private long raised_by;
    private String raised_by_user_type;
    private String dispute_type;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    private String reason;

}
