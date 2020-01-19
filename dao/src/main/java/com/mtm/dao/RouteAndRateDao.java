package com.mtm.dao;

import com.mtm.beans.dto.Record;
import com.mtm.beans.dto.Route;
import com.mtm.beans.dto.RouteAndRate;

import java.util.List;

/**
 * Created by Admin on 3/10/2019.
 */
public class RouteAndRateDao extends AbstractDao {
    private static final String TABLE_NAME = "route_rate";
    private static final  Class RECORD_CLASS = RouteAndRate.class;
    public RouteAndRateDao()
    {
        super(TABLE_NAME, RECORD_CLASS);
    }
    public List<Record> getConvertedRecords(String whereClause) {
        return null;
    }
}
