package com.mtm.dao;

import com.mtm.beans.dto.Record;
import com.mtm.beans.dto.VehicleRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2/24/2019.
 */
public class VehicleDao extends AbstractDao{
    private static final String TABLE_NAME = "vehicle";
    public VehicleDao()
    {
        super(TABLE_NAME);
    }


    public List<Record> getConvertedRecords(String whereClause)
    {
        List<List<String>> records = get(whereClause);
        List<Record> vehicleRecords = new ArrayList<Record>();

        for(List<String> record : records)
        {
            Record vehicleRecord = new VehicleRecord();
            for(String columnValue : record)
            {
                ((VehicleRecord)vehicleRecord).setVehicleid(Integer.parseInt(record.get(0)));
                ((VehicleRecord)vehicleRecord).setRegistration_num(record.get(1));
                ((VehicleRecord)vehicleRecord).setOwnerId(Integer.parseInt(record.get(2)));
                ((VehicleRecord)vehicleRecord).setDriverId(Integer.parseInt(record.get(3)));
                ((VehicleRecord)vehicleRecord).setModel_num(Integer.parseInt(record.get(4)));
                ((VehicleRecord)vehicleRecord).setVehicle_type(record.get(5));
            }
            vehicleRecords.add(vehicleRecord);
        }
        return vehicleRecords;
    }

}
