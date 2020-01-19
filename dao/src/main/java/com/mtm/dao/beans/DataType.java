package com.mtm.dao.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Admin on 3/4/2019.
 */
public enum DataType {

    INT("INT", int.class), CHAR("CHAR", char.class), LONG("LONG", long.class), STRING("STRING", String.class),
    DOUBLE("DOUBLE", double.class), FLOAT("FLOAT",float.class),DATE("DATE", java.util.Date.class);

    private String value;
    private Class dataTypeClass;

     DataType(String value, Class javaTypeClass)
    {
        this.value = value;
        this.dataTypeClass = javaTypeClass;
    }

    public DataType getFromValue(String value)
    {
        for(DataType dataType : this.values() )
        {
            if(dataType.value.equalsIgnoreCase(value))
                return dataType;
        }
        return null;
    }
    public Class getDataTypeClass()
    {
        return this.dataTypeClass;
    }

    private Object returnValueorDefault(Object value, Object defaultValue)
    {
        if(value != null)
            return value;
        else
            return defaultValue;

    }
    public Object parse(String value)
    {

        switch(this)
        {
            case INT:
                return value!=null?Integer.parseInt(value):0;
            case LONG:
                return value!=null?Long.parseLong(value):0;
            case DOUBLE:
                return value!=null?Double.parseDouble(value):0.0;
            case CHAR:
                return value!=null?Character.valueOf(value.charAt(0)):0;
            case FLOAT:
                return value!=null?Float.parseFloat(value):0.0f;
            case DATE:
            {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    if(value==null)
                        return value;
                    return dateFormat.parse(value);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
            default:
                return value;
        }
    }


}
