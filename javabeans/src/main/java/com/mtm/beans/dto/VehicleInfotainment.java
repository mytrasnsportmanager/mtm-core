package com.mtm.beans.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Admin on 8/11/2020.
 */
public class VehicleInfotainment {
    @JsonProperty
    public long getVehicleid() {
        return vehicleid;
    }

    public void setVehicleid(long vehicleid) {
        this.vehicleid = vehicleid;
    }

    @JsonProperty
    public double getCurrent_profit_standing() {
        return current_profit_standing;
    }

    public void setCurrent_profit_standing(double current_profit_standing) {
        this.current_profit_standing = current_profit_standing;
    }

    private long vehicleid;
    private double current_profit_standing;

    public double getAverageMonthlyRevenue() {
        return averageMonthlyRevenue;
    }

    public void setAverageMonthlyRevenue(double averageMonthlyRevenue) {
        this.averageMonthlyRevenue = averageMonthlyRevenue;
    }

    public double getAverageMonthlyExpense() {
        return averageMonthlyExpense;
    }

    public void setAverageMonthlyExpense(double averageMonthlyExpense) {
        this.averageMonthlyExpense = averageMonthlyExpense;
    }

    public double getAverageMonthlyIncome() {
        return averageMonthlyIncome;
    }

    public void setAverageMonthlyIncome(double averageMonthlyIncome) {
        this.averageMonthlyIncome = averageMonthlyIncome;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    private double averageMonthlyRevenue;
    private double averageMonthlyExpense;
    private double averageMonthlyIncome;
    private String ownerName ;
    private double ownerTotalExpense;

    @JsonProperty
    public double getOwnerTotalExpense() {
        return ownerTotalExpense;
    }

    public void setOwnerTotalExpense(double ownerTotalExpense) {
        this.ownerTotalExpense = ownerTotalExpense;
    }
}
