package com.mtm.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 4/20/2019.
 */
public enum UploadFileType {
    OWNER_PROFILE_PIC("owner_profile_pic","owners"), CHALLAN_PIC("challan_pic","challans"), CONSIGNER_PROFILE_PIC
            ("consigner_profile_pic","consigners"), VEHICLE_PIC("vehicle_pic", "vehicles");


    UploadFileType(String param, String directoryName)
    {
        this.name = param;
        this.directoryName = directoryName;
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
    public static UploadFileType getFromValue(String value)
    {
        for(UploadFileType uploadFileType : UploadFileType.values())
        {
            if(uploadFileType.name.equalsIgnoreCase(value))
                return uploadFileType;
        }
        return null;
    }

    public String getDirectoryName()
    {
        return this.directoryName;
    }
    String name ;
    String directoryName;
}
