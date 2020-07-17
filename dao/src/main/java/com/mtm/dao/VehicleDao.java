package com.mtm.dao;

import com.mtm.beans.dto.Record;
import com.mtm.beans.dto.Vehicle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2/24/2019.
 */
public class VehicleDao extends AbstractDao{
    private static final String TABLE_NAME = "vehicle";
    private static final  Class RECORD_CLASS = Vehicle.class;
    public VehicleDao()
    {
        super(TABLE_NAME, RECORD_CLASS);
    }


    public List<Record> getConvertedRecords(String whereClause)
    {
        List<List<String>> records = get(whereClause);
        List<Record> vehicleRecords = new ArrayList<Record>();

        for(List<String> record : records)
        {
            Record vehicleRecord = new Vehicle();
            for(String columnValue : record)
            {
                ((Vehicle)vehicleRecord).setVehicleid(Integer.parseInt(record.get(0)));
                ((Vehicle)vehicleRecord).setRegistration_num(record.get(1));
                ((Vehicle)vehicleRecord).setOwnerid(Integer.parseInt(record.get(2)));
                ((Vehicle)vehicleRecord).setDriverid(Long.parseLong(record.get(3)));
                ((Vehicle)vehicleRecord).setModel_num(Integer.parseInt(record.get(4)));
                ((Vehicle)vehicleRecord).setVehicle_type(record.get(5));
            }
            vehicleRecords.add(vehicleRecord);
        }
        return vehicleRecords;
    }

    public List<Record> getConvertedRecordsForQuery(String query)
    {
        List<List<String>> records = executeQuery(query);
        List<Record> vehicleRecords = new ArrayList<Record>();

        for(List<String> record : records)
        {
            Record vehicleRecord = new Vehicle();
            for(String columnValue : record)
            {
                ((Vehicle)vehicleRecord).setVehicleid(Integer.parseInt(record.get(0)));
                ((Vehicle)vehicleRecord).setRegistration_num(record.get(1));
                ((Vehicle)vehicleRecord).setOwnerid(Integer.parseInt(record.get(2)));
                ((Vehicle)vehicleRecord).setDriverid(Long.parseLong(record.get(3)));
                ((Vehicle)vehicleRecord).setModel_num(Integer.parseInt(record.get(4)));
                ((Vehicle)vehicleRecord).setVehicle_type(record.get(5));
            }
            vehicleRecords.add(vehicleRecord);
        }
        return vehicleRecords;
    }



}
