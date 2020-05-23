package com.mtm.beans.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *Created by Admin on 5/9/2020.
 */

@XmlRootElement
public class OwnerLevelVehiclesWork implements Serializable {

    @JsonProperty
    public List<CreditDebit> getCreditDebits() {
        return creditDebits;
    }

    @XmlElement
    public void setCreditDebits(List<CreditDebit> creditDebits) {
        this.creditDebits = creditDebits;
    }

    List<CreditDebit> creditDebits = new ArrayList<>();

    public String getConsignername() {
        return consignername;
    }

    @XmlElement
    public void setConsignername(String consignername) {
        this.consignername = consignername;
    }

    public String getReportdate() {
        return reportdate;
    }

    @XmlElement
    public void setReportdate(String reportdate) {
        this.reportdate = reportdate;
    }

    public String getOwnername() {
        return ownername;
    }

    @XmlElement
    public void setOwnername(String ownername) {
        this.ownername = ownername;
    }

    public String getTotalReceivables() {
        return totalReceivables;
    }

    @XmlElement
    public void setTotalReceivables(String totalReceivables) {
        this.totalReceivables = totalReceivables;
    }

    public String getCurrentUnbilled() {
        return currentUnbilled;
    }

    @XmlElement
    public void setCurrentUnbilled(String currentUnbilled) {
        this.currentUnbilled = currentUnbilled;
    }

    public String getPreviousBilled() {
        return previousBilled;
    }

    @XmlElement
    public void setPreviousBilled(String previousBilled) {
        this.previousBilled = previousBilled;
    }

    String consignername;
    String reportdate;
    String ownername;
    private String totalReceivables;
    private String currentUnbilled;
    private String previousBilled;

}
