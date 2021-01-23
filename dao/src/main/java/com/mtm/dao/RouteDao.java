package com.mtm.dao;

import com.mtm.beans.dto.Record;
import com.mtm.beans.dto.Route;
import com.mtm.beans.dto.Trip;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 3/4/2019.
 */
public class RouteDao extends AbstractDao{
    private static final String TABLE_NAME = "route";
    private static final  Class RECORD_CLASS = Route.class;
    public RouteDao()
    {
        super(TABLE_NAME, RECORD_CLASS);
    }
    public List<Record> getConvertedRecords(String whereClause) {

       String query = "select routeid, source, destination, consignerid, ownerid, rate, rate_type, distance from route where "+whereClause;

        List<List<String>> records = executeQuery(query);
        List<Record> routeRecords = new ArrayList<Record>();

        for(List<String> record : records)
        {
            Route routeRecord = new Route();

            ((Route)routeRecord).setRouteid(Long.parseLong(record.get(0)));
            ((Route)routeRecord).setSource(record.get(1));
            ((Route)routeRecord).setDestination(record.get(2));
            ((Route)routeRecord).setConsignerid(Long.parseLong(record.get(3)));
            ((Route)routeRecord).setOwnerid(Long.parseLong(record.get(4)));
            ((Route)routeRecord).setRate(Double.parseDouble(record.get(5)));
            ((Route)routeRecord).setRate_type(record.get(6));
            ((Route)routeRecord).setDistance(Double.parseDouble(record.get(7)));
            routeRecords.add(routeRecord);


        }


        return routeRecords;
    }
}
