package com.mtm.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Admin on 3/4/2019.
 */
public class Status {
    @JsonProperty
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private  String message;

    public Status()
    {

    }
    public Status(String message, int returnCode, long insertedId) {
        this.message = message;
        this.returnCode = returnCode;
        this.insertedId = insertedId;
    }

    @JsonProperty
    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    private int returnCode;

    @JsonProperty
    public long getInsertedId() {
        return insertedId;
    }

    public void setInsertedId(long insertedId) {
        this.insertedId = insertedId;
    }

    private long insertedId;
}
