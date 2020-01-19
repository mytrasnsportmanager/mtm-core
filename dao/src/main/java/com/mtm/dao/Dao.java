package com.mtm.dao;

import com.mtm.beans.dto.Record;
import com.sun.prism.impl.Disposer;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * Created by Admin on 2/24/2019.
 */
public interface Dao {

    public List<List<String>> get(String whereClause);
    public long insert(Record record);
    public long patch(Record record);
    public List<Record> getConvertedRecords(String whereClause);
    public List<Object> getRecords(String whereClause);
    public boolean update(Map<Column, String> columnMap, String whereClause);
    public List<List<String>> executeQuery(String selectQuery);
    public Connection getConnection();
    public int delete(String whereClause);


}
