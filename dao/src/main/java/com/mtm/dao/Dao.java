package com.mtm.dao;

import com.mtm.beans.dto.Record;
import com.sun.prism.impl.Disposer;

import java.util.List;

/**
 * Created by Admin on 2/24/2019.
 */
public interface Dao {

    public List<List<String>> get(String whereClause);
    public int insert(Record record);
    public List<Record> getConvertedRecords(String whereClause);

}
