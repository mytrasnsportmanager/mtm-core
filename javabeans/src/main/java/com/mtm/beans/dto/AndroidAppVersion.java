package com.mtm.beans.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Admin on 7/9/2020.
 */
public class AndroidAppVersion extends Record {
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @JsonProperty
    String version;
    @JsonProperty
    int code;
}
