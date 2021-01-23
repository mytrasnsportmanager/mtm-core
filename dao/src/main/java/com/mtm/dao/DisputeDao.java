package com.mtm.dao;

import com.mtm.beans.dto.Dispute;
import com.mtm.beans.dto.Record;
import com.mtm.beans.dto.Txn;

import java.util.List;

/**
 * Created by Admin on 8/16/2020.
 */
public class DisputeDao extends AbstractDao{

    private static final String TABLE_NAME = "disputes";
    private static final  Class RECORD_CLASS = Dispute.class;
    public DisputeDao()
    {
        super(TABLE_NAME, RECORD_CLASS);
    }
    @Override
    public List<Record> getConvertedRecords(String whereClause) {
        return null;
    }
}
