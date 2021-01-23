package com.mtm.dao;

import com.mtm.beans.dto.Record;
import com.mtm.beans.dto.Trip;
import com.mtm.beans.dto.VehicleFuelConsumption;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public List<Record> getConvertedRecords(String whereClause)  {
        List<List<String>> records = get(whereClause);
        List<Record> tripRecords = new ArrayList<Record>();

        for(List<String> record : records)
        {
            Record tripRecord = new Trip();

                ((Trip)tripRecord).setTripid(Long.parseLong(record.get(0)));
            ((Trip)tripRecord).setVehicleid(Long.parseLong(record.get(1)));
            ((Trip)tripRecord).setRouteid(Long.parseLong(record.get(2)));
            ((Trip)tripRecord).setDriverid(Long.parseLong(record.get(3)));
            ((Trip)tripRecord).setRateid(Long.parseLong(record.get(4)));
            ((Trip)tripRecord).setWork_done(Float.parseFloat(record.get(5)));

                try {
                    ((Trip)tripRecord).setStarttime(dateTimeFormat.parse(record.get(6)));
                    ((Trip)tripRecord).setEndtime(dateTimeFormat.parse(record.get(7)));
                } catch (ParseException e) {
            e.printStackTrace();
        }
                ((Trip)tripRecord).setMaterial_name(record.get(8));
            ((Trip)tripRecord).setImage_url(record.get(9));
            ((Trip)tripRecord).setBillingid(Long.parseLong(record.get(10)));
            ((Trip)tripRecord).setChallanid(Long.parseLong(record.get(11)));
            ((Trip)tripRecord).setReturned_with_trip(record.get(12));
            ((Trip)tripRecord).setExpected_fuel_consumed(Double.parseDouble(record.get(13)));
            tripRecords.add(tripRecord);

            }


        return tripRecords;
    }
}
