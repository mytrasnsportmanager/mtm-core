package com.mtm.dao;

import com.mtm.beans.dto.Record;
import com.mtm.beans.dto.Vehicle;
import com.mtm.beans.dto.VehicleFuelConsumption;

import java.util.List;

/**
 * Created by Admin on 8/8/2020.
 */
public class VehicleFuelConsumptionDao extends AbstractDao {


    private static final String TABLE_NAME = "vehicle_fuel_consumption";
    private static final  Class RECORD_CLASS = VehicleFuelConsumption.class;
    public VehicleFuelConsumptionDao()
    {
        super(TABLE_NAME, RECORD_CLASS);
    }



    @Override
    public List<Record> getConvertedRecords(String whereClause) {
        return null;
    }
}
