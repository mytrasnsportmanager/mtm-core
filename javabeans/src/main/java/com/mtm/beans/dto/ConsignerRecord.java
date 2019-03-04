package com.mtm.beans.dto;

import java.util.ArrayList;

/**
 * Created by Admin on 3/4/2019.
 */
public class ConsignerRecord extends Record{

    public long getConsignerid() {
        return consignerid;
    }

    public void setConsignerid(long consignerid) {
        this.consignerid = consignerid;
    }

    public String getConsigner_name() {
        return consigner_name;
    }

    public void setConsigner_name(String consigner_name) {
        this.consigner_name = consigner_name;
    }

    public char getIs_company() {
        return is_company;
    }

    public void setIs_company(char is_company) {
        this.is_company = is_company;
    }

    public String getConsigner_contact() {
        return consigner_contact;
    }

    public void setConsigner_contact(String consigner_contact) {
        this.consigner_contact = consigner_contact;
    }

    public ConsignerRecord(long consignerid, String consigner_name, char is_company, String consigner_contact) {
        this.consignerid = consignerid;
        this.consigner_name = consigner_name;
        this.is_company = is_company;
        this.consigner_contact = consigner_contact;
        record = new ArrayList();
        record.add(null);
        record.add(consigner_name);
        record.add(is_company);
        record.add(consigner_contact);

    }

    private long consignerid        ;
    private String consigner_name    ;
    private char is_company          ;
    private String consigner_contact ;
}
