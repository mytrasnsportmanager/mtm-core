package com.mtm.beans.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by Admin on 7/26/2020.
 */
public class OwnerExpense extends Record {

    @JsonProperty
    public long getVehicleid() {
        return vehicleid;
    }

    public void setVehicleid(long vehicleid) {
        this.vehicleid = vehicleid;
    }

    @JsonProperty

    public Date getExpense_date() {
        return expense_date;
    }

    public void setExpense_date(Date expense_date) {
        this.expense_date = expense_date;
    }

    @JsonProperty
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @JsonProperty
    public int getExpense_type() {
        return expense_type;
    }

    public void setExpense_type(int expense_type) {
        this.expense_type = expense_type;
    }

    private long vehicleid;
    private Date expense_date;
    private double amount;
    private int  expense_type;

    public long getExpenseid() {
        return expenseid;
    }

    public void setExpenseid(long expenseid) {
        this.expenseid = expenseid;
    }

    private long expenseid;



}
