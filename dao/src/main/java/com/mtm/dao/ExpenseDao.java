package com.mtm.dao;

import com.mtm.beans.dto.Expense;
import com.mtm.beans.dto.Rate;
import com.mtm.beans.dto.Record;

import java.util.List;

/**
 * Created by Admin on 4/20/2019.
 */
public class ExpenseDao extends AbstractDao{
    private static final String TABLE_NAME = "expense";
    private static final  Class RECORD_CLASS = Expense.class;
    public ExpenseDao()
    {
        super(TABLE_NAME, RECORD_CLASS);
    }
    public List<Record> getConvertedRecords(String whereClause) {
        return null;
    }


}
