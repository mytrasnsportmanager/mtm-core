package com.mtm.dao;

import com.mtm.beans.dto.Record;
import com.mtm.beans.dto.Vehicle;
import com.mtm.beans.dto.VehicleFuelConsumption;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public List<Record> getConvertedRecords(String whereClause)  {
        List<List<String>> records = get(whereClause);
        List<Record> vehicleFuelConsumptionRecords = new ArrayList<Record>();

        for(List<String> record : records)
        {
            Record vehicleFuelConsumption = new VehicleFuelConsumption();


                ((VehicleFuelConsumption)vehicleFuelConsumption).setVehicleid(Long.parseLong(record.get(0)));
                try {
                    ((VehicleFuelConsumption)vehicleFuelConsumption).setConsumption_date(dateTimeFormat.parse(record.get(1)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                ((VehicleFuelConsumption)vehicleFuelConsumption).setConsumption_amt(Double.parseDouble(record.get(2)));
                ((VehicleFuelConsumption)vehicleFuelConsumption).setTripid(Long.parseLong(record.get(3)));
                ((VehicleFuelConsumption)vehicleFuelConsumption).setRemarks(record.get(4));


            vehicleFuelConsumptionRecords.add(vehicleFuelConsumption);
        }
        return vehicleFuelConsumptionRecords;
    }
}
