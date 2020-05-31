package com.mtm.dao;

import com.mtm.beans.Accountant;
import com.mtm.beans.AccountantVehicle;
import com.mtm.beans.dto.Record;

import java.util.List;

/**
 * Created by Admin on 5/29/2020.
 */
public class AccountantVehicleDao extends AbstractDao {
    @Override
    public List<Record> getConvertedRecords(String whereClause) {
        return null;
    }
    private static final String TABLE_NAME = "accountant_vehicle";
    private static final  Class RECORD_CLASS = AccountantVehicle.class;
    public AccountantVehicleDao()
    {
        super(TABLE_NAME, RECORD_CLASS);
    }
}
