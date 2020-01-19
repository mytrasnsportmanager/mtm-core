package com.mtm.dao;

import com.mtm.beans.dto.Owner;
import com.mtm.beans.dto.OwnerConsigner;
import com.mtm.beans.dto.Record;

import java.util.List;

/**
 * Created by Admin on 3/13/2019.
 */
public class OwnerConsignerDao extends AbstractDao {

    private static final String TABLE_NAME = "owner_consigner";
    private static final  Class RECORD_CLASS = OwnerConsigner.class;

    static
    {

    }
    public OwnerConsignerDao()
    {
        super(TABLE_NAME, RECORD_CLASS);
    }
    public List<Record> getConvertedRecords(String whereClause) {
        return null;
    }

}
