package com.mtm.dao;

import com.mtm.beans.dto.Record;
import com.mtm.dao.connection.Database;
import com.mtm.dao.connection.MySqlDatabase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2/24/2019.
 */
public class OwnerDao extends AbstractDao {

    private static final String TABLE_NAME = "owner";
    public OwnerDao()
    {
        super(TABLE_NAME);
    }

    public List<Record> getConvertedRecords(String whereClause) {
        return null;
    }
}
