package com.mtm.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 4/9/2019.
 */
public enum RateType {

    PER_KM("per km"), PER_TONNE("per tonne"), PER_MONTH("per month");


    RateType(String param)
    {
        this.name = param;
    }
    public String toString()
    {
        return  this.name;
    }

    public static List<String> getTypeValues()
    {
        List<String> values = new ArrayList<String>();
        for(RateType rateType : RateType.values())
        {
            values.add(rateType.name);
        }
        return values;

    }

    public static RateType fromValue(String value)

    {
        for(RateType rateType : RateType.values())
        {
            if(rateType.name.equalsIgnoreCase(value))
                return rateType;
        }
        return null;
    }



    String name ;

}
