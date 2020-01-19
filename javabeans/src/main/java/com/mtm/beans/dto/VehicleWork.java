package com.mtm.beans.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Admin on 7/16/2019.
 */
@XmlRootElement
public class VehicleWork implements Serializable {

    @JsonProperty
    public long getVehicleid() {
        return vehicleid;
    }

    @XmlElement
    public void setVehicleid(long vehicleid) {
        this.vehicleid = vehicleid;
    }

    @JsonProperty
    public long getConsignerid() {
        return consignerid;
    }

    @XmlElement
    public void setConsignerid(long consignerid) {
        this.consignerid = consignerid;
    }

    @JsonProperty
    public String getRegistration_num() {
        return registration_num;
    }

    @XmlElement
    public void setRegistration_num(String registration_num) {
        this.registration_num = registration_num;
    }

    public List<CreditDebit> getBusinessDetails() {
        return businessDetails;
    }

    @XmlElement
    public void setBusinessDetails(List<CreditDebit> businessDetails) {
        this.businessDetails = businessDetails;
    }

    long vehicleid;
    long consignerid;
    String registration_num;
    List<CreditDebit> businessDetails;

    public String getConsignername() {
        return consignername;
    }

    @XmlElement
    public void setConsignername(String consignername) {
        this.consignername = consignername;
    }

    public String getVehicletype() {
        return vehicletype;
    }

    @XmlElement
    public void setVehicletype(String vehicletype) {
        this.vehicletype = vehicletype;
    }

    String consignername;
    String vehicletype;
    String reportdate;

    public String getReportdate() {
        return reportdate;
    }

    public void setReportdate(String reportdate) {
        this.reportdate = reportdate;
    }

    @JsonProperty
    public String getOwnername() {
        return ownername;
    }

    @XmlElement
    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    String ownername;





    @JsonProperty
    public String getPreviousBilled() {
        return previousBilled;
    }

    @XmlElement
    public void setPreviousBilled(String previousBilled) {
        this.previousBilled = previousBilled;
    }


    @JsonProperty
    public String getTotalReceivables() {
        return totalReceivables;
    }

    @XmlElement
    public void setTotalReceivables(String totalReceivables) {
        this.totalReceivables = totalReceivables;
    }



    @JsonProperty
    public String getCurrentUnbilled() {
        return currentUnbilled;
    }

    @XmlElement
    public void setCurrentUnbilled(String currentUnbilled) {
        this.currentUnbilled = currentUnbilled;
    }

    private String totalReceivables;
    private String currentUnbilled;
    private String previousBilled;



}
