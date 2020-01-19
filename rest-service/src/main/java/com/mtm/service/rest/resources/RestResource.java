package com.mtm.service.rest.resources;

import com.google.common.base.Optional;
import com.mtm.beans.dto.Record;
import com.mtm.beans.dto.Vehicle;

import java.util.List;

/**
 * Created by Admin on 3/4/2019.
 */
public interface RestResource {
    public Object create(Record record);
    public List<Object> get(String whereClause);
    public Object update(Record record);
    public Object delete(Record record);
}
