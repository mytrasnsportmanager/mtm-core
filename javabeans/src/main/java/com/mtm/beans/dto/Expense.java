package com.mtm.beans.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Admin on 4/20/2019.
 */
public class Expense extends Record {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @JsonProperty
    public long getExpenseid() {
        return expenseid;
    }

    public void setExpenseid(long expenseid) {
        this.expenseid = expenseid;
    }

    @JsonProperty
    public long getVehicleid() {
        return vehicleid;
    }

    public void setVehicleid(long vehicleid) {
        this.vehicleid = vehicleid;
    }

    @JsonProperty
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty
    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @JsonProperty
    public String getExpensedate() {
        return dateFormat.format(expensedate);
    }

    public void setExpensedate(Date expensedate) {
        this.expensedate = expensedate;
    }

    @JsonProperty
    public long getPayerid() {
        return payerid;
    }

    public void setPayerid(long payerid) {
        this.payerid = payerid;
    }

    @JsonProperty
    public String getPayertype() {
        return payertype;
    }

    public void setPayertype(String payerType) {
        this.payertype = payerType;
    }

    @JsonProperty
    public String getReceiptid() {
        return receiptid;
    }

    public void setReceiptid(String receiptid) {
        this.receiptid = receiptid;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    private long expenseid;
    private long vehicleid;
    private String type;
    private float value;
    private Date expensedate;
    private long payerid;
    private String payertype;
    private String receiptid;
    private float rate;
    private float quantity;
}
