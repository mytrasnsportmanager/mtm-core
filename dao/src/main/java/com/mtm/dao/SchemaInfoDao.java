package com.mtm.dao;

import java.util.List;

/**
 * Created by Admin on 3/4/2019.
 */
public interface SchemaInfoDao {

 public abstract List<Column> getSchema(String tableName);


}
