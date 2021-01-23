package com.mtm.beans;

/**
 * Created by Admin on 8/16/2020.
 */
public enum DisputeType {

TRIP("TRIP["), TXN("TXN");

    private DisputeType(String codeArg)
    {
        code = codeArg;
    }


    private String code;

    public static DisputeType getFrom(String codeArg)
    {
        for(DisputeType disputeType : DisputeType.values())
        {
            if(disputeType.code.equalsIgnoreCase(codeArg))
                return disputeType;
        }

        return null;
    }

}
