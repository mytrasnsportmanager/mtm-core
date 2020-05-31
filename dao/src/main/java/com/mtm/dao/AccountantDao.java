package com.mtm.dao;

import com.mtm.beans.Accountant;
import com.mtm.beans.dto.Record;
import com.mtm.beans.dto.Vehicle;
import com.mtm.beans.dto.VehicleDriver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 5/29/2020.
 */
public class AccountantDao extends AbstractDao {
    @Override
    public List<Record> getConvertedRecords(String whereClause) {
        List<List<String>> records = get(whereClause);
        List<Record> accountantRecords = new ArrayList<Record>();

        for(List<String> record : records)
        {
            Record accountantRecord = new Accountant();
            for(String columnValue : record)
            {
                ((Accountant)accountantRecord).setName(record.get(0));
                ((Accountant)accountantRecord).setContact(Long.parseLong(record.get(1)));
                ((Accountant)accountantRecord).setImage_url(record.get(2));
                ((Accountant)accountantRecord).setAccountantid(Long.parseLong(record.get(3)));

            }
            accountantRecords.add(accountantRecord);
        }
        return accountantRecords;

    }

    private static final String TABLE_NAME = "accountant";
    private static final  Class RECORD_CLASS = Accountant.class;
    public AccountantDao()
    {
        super(TABLE_NAME, RECORD_CLASS);
    }

}
