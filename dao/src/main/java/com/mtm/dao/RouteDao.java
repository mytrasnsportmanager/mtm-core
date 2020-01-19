package com.mtm.dao;

import com.mtm.beans.dto.Record;
import com.mtm.beans.dto.Route;

import java.util.List;

/**
 * Created by Admin on 3/4/2019.
 */
public class RouteDao extends AbstractDao{
    private static final String TABLE_NAME = "route";
    private static final  Class RECORD_CLASS = Route.class;
    public RouteDao()
    {
        super(TABLE_NAME, RECORD_CLASS);
    }
    public List<Record> getConvertedRecords(String whereClause) {
        return null;
    }
}
