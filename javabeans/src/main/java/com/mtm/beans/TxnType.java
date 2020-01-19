package com.mtm.beans;

/**
 * Created by Admin on 10/8/2019.
 */
public enum TxnType {

    TRIP("Trip",0),PAYMENT("Payment",1),MAINTENANCE("Maintenance",2), DRIVER("Driver",3),FUEL("Fuel",4), TOLL("Toll",5),TAX_AND_PENALTIES("Tax/Penalties",6),
    OTHERS("Others",7);

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    private TxnType(String key, int value) {
        this.key = key;
        this.value = value;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String key;
    private int value;
    public static String getFromValue(int value)
    {
        for( TxnType txnType : TxnType.values())
        {
            if(txnType.getValue()==value)
                return txnType.getKey();
        }
        return null;
    }
}
