package com.mtm.beans.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Admin on 3/10/2019.
 */
public class RouteAndRate extends Record {
    private long routeid;
    private long rateid;

    @JsonProperty
    public long getRateid() {
        return rateid;
    }

    public void setRateid(long rateid) {
        this.rateid = rateid;
    }

    @JsonProperty
    public long getRouteid() {

        return routeid;
    }

    public void setRouteid(long routeid) {
        this.routeid = routeid;
    }
}
