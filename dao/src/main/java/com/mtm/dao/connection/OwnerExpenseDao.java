package com.mtm.dao.connection;

import com.mtm.beans.dto.OwnerExpense;
import com.mtm.beans.dto.Record;
import com.mtm.beans.dto.Txn;
import com.mtm.beans.dto.VehicleDriver;
import com.mtm.dao.AbstractDao;

import java.util.List;

/**
 * Created by Admin on 7/26/2020.
 */
public class OwnerExpenseDao extends AbstractDao {

    private static final String TABLE_NAME = "owner_expense";
    private static final  Class RECORD_CLASS = Txn.class;
    public OwnerExpenseDao()
    {
        super(TABLE_NAME, RECORD_CLASS);
    }
    public List<Record> getConvertedRecords(String whereClause) {
        return null;
    }
}
