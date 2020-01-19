package com.mtm.dao;

import com.mtm.beans.dto.Record;
import com.mtm.beans.dto.Vehicle;
import com.mtm.beans.dto.VehicleLocation;

import java.util.List;

/**
 * Created by Admin on 10/12/2019.
 */
public class VehicleLocationDao extends AbstractDao {

    private static final String TABLE_NAME = "vehicle_location";
    private static final  Class RECORD_CLASS = VehicleLocation.class;
    public VehicleLocationDao()
    {
        super(TABLE_NAME, RECORD_CLASS);
    }

    @Override
    public List<Record> getConvertedRecords(String whereClause) {
        return null;
    }
}
