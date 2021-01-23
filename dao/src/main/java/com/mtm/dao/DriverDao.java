package com.mtm.dao;

import com.mtm.beans.dto.Owner;
import com.mtm.beans.dto.Record;
import com.mtm.beans.dto.VehicleDriver;

import java.util.ArrayList;
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
        List<List<String>> records = get(whereClause);
        List<Record> driverRecords = new ArrayList<>();
        VehicleDriver vehicleDriver = new VehicleDriver();
        for(List<String> record: records)
        {
            vehicleDriver.setDriverid(Long.parseLong(record.get(0)));
            vehicleDriver.setName(record.get(1));
            vehicleDriver.setAddress(record.get(2));
            vehicleDriver.setLicense_num(record.get(3));
            vehicleDriver.setVehicleid(Long.parseLong(record.get(4)));

            vehicleDriver.setContact(Long.parseLong(record.get(7)));
            vehicleDriver.setImage_url(record.get(8));
            driverRecords.add(vehicleDriver);



        }

        return driverRecords;
    }
}
