package com.mtm.dao;

import com.mtm.beans.dto.Owner;
import com.mtm.beans.dto.Record;
import com.mtm.beans.dto.VehicleDriver;

import java.util.List;

/**
 * Created by Admin on 9/15/2019.
 */
public class DriverDao extends AbstractDao {
    private static final String TABLE_NAME = "vehicledriver";
    private static final  Class RECORD_CLASS = VehicleDriver.class;
    public DriverDao()
    {
        super(TABLE_NAME, RECORD_CLASS);
    }
    public List<Record> getConvertedRecords(String whereClause) {
        return null;
    }
}
