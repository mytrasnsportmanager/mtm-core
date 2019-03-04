package com.mtm.dao;

import com.mtm.beans.dto.Record;
import com.mtm.dao.connection.Database;
import com.mtm.dao.connection.MySqlDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 2/24/2019.
 */
public abstract class AbstractDao implements  Dao {

    public Database getDatabase() {
        return database;
    }

    private Database database = new MySqlDatabase();
    private String tableName ;


    protected AbstractDao(String tableName)
    {
        this.tableName = tableName;
    }

    public List<List<String>> get(String whereClause) {
        Connection connection =null;
        try {
            connection = database.getConnection();
            Statement statement = connection.createStatement();
            String selectQuery = "select * from "+tableName+" where "+whereClause;
            System.out.println("Select query >> "+selectQuery);
            ResultSet rs = statement.executeQuery(selectQuery);
            ResultSetMetaData rsmd = rs.getMetaData();
            List<List<String>> records = new ArrayList<List<String>>();
            while(rs.next())
            {
                List<String> record = new ArrayList<String>();
                for(int i=1;i<= rsmd.getColumnCount();i++)
                {
                    record.add(rs.getString(i));
                }
                records.add(record);
            }
            return records;




        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;

    }

    public int insert(Record record) {
        StringBuffer insertQuery = new StringBuffer("insert into "+tableName+" values(");
        Connection connection = null;
        int numRecordsInserted  = 0;
        boolean first = true;
        for(Object columnValue : record.get())
        {
            if(first)
            {
                first = false;
            }
            else
            {
                insertQuery.append(",");
            }
          if(columnValue == null)
              insertQuery.append("null");
          else
          {
              if(columnValue instanceof String)
              {
                  insertQuery.append("'"+columnValue+"'");
              }
              else
              {
                  insertQuery.append(columnValue);
              }
          }


        }
        insertQuery.append(")");
        System.out.println("Insert Query >> "+insertQuery);
        try {
            connection =  database.getConnection();
            Statement statement = connection.createStatement();
            numRecordsInserted = statement.executeUpdate(insertQuery.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return numRecordsInserted;

    }
}
