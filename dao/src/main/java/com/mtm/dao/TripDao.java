package com.mtm.dao;

import com.mtm.beans.dto.Record;
import com.mtm.beans.dto.Trip;

import java.util.List;

/**
 * Created by Admin on 3/4/2019.
 */
public class TripDao extends AbstractDao {
    private static final String TABLE_NAME = "trip";
    private static final  Class RECORD_CLASS = Trip.class;
    public TripDao()
    {
        super(TABLE_NAME, RECORD_CLASS);
    }
    public List<Record> getConvertedRecords(String whereClause) {
        return null;
    }
}
