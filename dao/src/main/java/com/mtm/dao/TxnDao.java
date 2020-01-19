package com.mtm.dao;

import com.mtm.beans.dto.Record;
import com.mtm.beans.dto.Txn;

import java.util.List;

/**
 * Created by Admin on 3/4/2019.
 */
public class TxnDao extends AbstractDao{
    private static final String TABLE_NAME = "txn";
    private static final  Class RECORD_CLASS = Txn.class;
    public TxnDao()
    {
        super(TABLE_NAME, RECORD_CLASS);
    }
    public List<Record> getConvertedRecords(String whereClause) {
        return null;
    }
}
