package com.mtm.dao;

import com.mtm.dao.beans.DataType;
import com.mtm.dao.connection.Database;
import com.mtm.dao.connection.MySqlDatabase;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 3/4/2019.
 */
public class MySqlSchemaInfoDao implements SchemaInfoDao {

    private static final String MTM_SCHEMA = "mtm";

    public List<Column> getSchema(String tableName) {

        Database database = new MySqlDatabase();
        try {
            List<Column> columns = new ArrayList<Column>();
            Connection connection = database.getConnection();
            Statement statement = connection.createStatement();
            String schemaInfoQuery = "SELECT COLUMN_NAME, DATA_TYPE, ORDINAL_POSITION,COLUMN_KEY, EXTRA  FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA='" + MTM_SCHEMA + "' AND TABLE_NAME='" + tableName + "'";
            ResultSet rs = statement.executeQuery(schemaInfoQuery);
            while (rs.next()) {
                Column column = new Column();
                column.setTableName(tableName);
                column.setName(rs.getString(1));
                String mysqlColumnType = rs.getString(2);
                column.setType(getDataType(mysqlColumnType));
                column.setIndex(rs.getInt(3));
                if(rs.getString(4).equalsIgnoreCase("PRI"))
                {
                    column.setPrimaryKey(true);
                }
                if(rs.getString(5).equalsIgnoreCase("AUTO_INCREMENT"))
                {
                    column.setAutoIncrement(true);
                }

                columns.add(column);


            }
            return  columns;


        } catch (Exception e) {
            e.printStackTrace();
        }

 return null;

    }

    private DataType getDataType (String mysqlDataType)
    {
        if (mysqlDataType.equalsIgnoreCase("bigint")) {
            return DataType.valueOf("LONG");
        }
        if (mysqlDataType.equalsIgnoreCase("varchar")) {
            return DataType.valueOf("STRING");
        }
        if (mysqlDataType.equalsIgnoreCase("int")) {
            return DataType.valueOf("INT");
        }
        if (mysqlDataType.equalsIgnoreCase("date")) {
            return DataType.valueOf("DATE");
        }
        if (mysqlDataType.equalsIgnoreCase("timestamp")) {
            return DataType.valueOf("DATE");
        }
        if (mysqlDataType.equalsIgnoreCase("decimal")) {
            return DataType.valueOf("DOUBLE");
        }
        if (mysqlDataType.equalsIgnoreCase("double")) {
            return DataType.valueOf("DOUBLE");
        }
        if (mysqlDataType.equalsIgnoreCase("float")) {
            return DataType.valueOf("FLOAT");
        }
        if (mysqlDataType.equalsIgnoreCase("char")) {
            return DataType.valueOf("CHAR");
        }
        if (mysqlDataType.equalsIgnoreCase("char")) {
            return DataType.valueOf("CHAR");
        }
        if (mysqlDataType.equalsIgnoreCase("datetime")) {
            return DataType.valueOf("DATE");
        }
        return null;
    }


}
