package com.mtm.dao;

import com.mtm.beans.dto.Rate;
import com.mtm.beans.dto.Record;

import java.util.List;

/**
 * Created by Admin on 3/4/2019.
 */
public class RateDao extends AbstractDao {

    private static final String TABLE_NAME = "rate";
    private static final  Class RECORD_CLASS = Rate.class;
    public RateDao()
    {
        super(TABLE_NAME, RECORD_CLASS);
    }
    public List<Record> getConvertedRecords(String whereClause) {
        return null;
    }
}
