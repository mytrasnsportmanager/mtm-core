package com.mtm.dao;

import com.mtm.beans.dto.Consigner;
import com.mtm.beans.dto.OwnerConsigner;
import com.mtm.beans.dto.Record;

import java.util.List;

/**
 * Created by Admin on 3/4/2019.
 */
public class ConsignerDao extends AbstractDao {
    private static final String TABLE_NAME = "consigner";
    private static final  Class RECORD_CLASS = Consigner.class;

    public ConsignerDao()
    {
        super(TABLE_NAME, RECORD_CLASS);
    }
    public List<Record> getConvertedRecords(String whereClause) {
        return null;
    }
}
