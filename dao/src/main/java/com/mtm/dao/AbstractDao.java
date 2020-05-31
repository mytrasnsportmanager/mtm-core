package com.mtm.dao;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.*;
import com.mtm.beans.Status;
import com.mtm.beans.dto.Record;
import com.mtm.dao.beans.DataType;
import com.mtm.dao.connection.Database;
import com.mtm.dao.connection.MySqlDatabase;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Created by Admin on 2/24/2019.
 */
public abstract class AbstractDao implements  Dao {

    public Database getDatabase() {
        return database;
    }
    private Database database = new MySqlDatabase();
    private String tableName ;
    private  List<Column> columns = null;
    private static Map<Column, Method> recordBeanSetterMethods = new HashMap<Column, Method>();
    private static Map<Column, Method> recordBeanGetterMethods = new HashMap<Column, Method>();
    private Class recordClass;
    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Date defaultDate ;
    private static final String IMAGE_COLUMN_NAME = "image";
    private static final String DEFAULT_IMAGE_URL = "default.jpg";

    protected AbstractDao()
    {

    }


    protected AbstractDao(String tableName, Class recordClass)
    {
        this.tableName = tableName;
        this.recordClass = recordClass;
        SchemaInfoDao schemaInfoDao = new MySqlSchemaInfoDao();
        columns = schemaInfoDao.getSchema(tableName);
        Collections.sort(columns,new ColumnComparator());
        try {
            defaultDate = dateTimeFormat.parse("1970-01-01 00:01:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for(Column column : columns)
        {
            String setterMethodName = "set"+ column.getName().substring(0, 1).toUpperCase() + column.getName().substring(1);
            String getterMethodName = "get"+ column.getName().substring(0, 1).toUpperCase() + column.getName().substring(1);
            /*System.out.println("Searching for "+setterMethodName + " and "+getterMethodName
            +" column type"+column.getType());*/

            try {
                Method setterMethod = recordClass.getMethod(setterMethodName,column.getType().getDataTypeClass());
                recordBeanSetterMethods.put(column,setterMethod);
                Method getterMethod = recordClass.getMethod(getterMethodName);
                recordBeanGetterMethods.put(column,getterMethod);

            } catch (Exception e) {
                System.out.println("Issue with "+setterMethodName);
                e.printStackTrace();
            }
        }


    }




    public boolean update(Map<Column, String> columnMap, String whereClause)
    {
        Connection connection =null;
        StringBuffer updateQuery = new StringBuffer("update "+tableName+" set ");
        try {
            connection = database.getConnection();
            Statement statement = connection.createStatement();
            for (Map.Entry<Column, String> columnEntry : columnMap.entrySet()) {
                if (!updateQuery.toString().equalsIgnoreCase("update " + tableName + " set "))
                    updateQuery.append(",");


                Column column = columnEntry.getKey();
                String columnValue = columnEntry.getValue();
                updateQuery.append(column.getName() + "=");
                switch (column.getType()) {
                    case STRING: {
                        if(StringUtils.isNotEmpty(columnValue))
                        updateQuery.append("'" + columnValue + "'");
                        else
                            updateQuery.append("null");
                        break;
                    }
                    case DATE:
                        Date dateValue = dateTimeFormat.parse(columnValue);
                        updateQuery.append("'" + dateTimeFormat.format(columnValue) + "'");
                        break;
                    case CHAR:
                        updateQuery.append("'" + columnValue + "'");
                        break;
                    default:
                        updateQuery.append(columnValue);


                }
            }
            updateQuery.append(" where "+whereClause);
            int updateCount = statement.executeUpdate(updateQuery.toString());
            if(updateCount > 0)
                return true;
            else
                return false;
        } catch (Exception e)
        {
            return false;
        }
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

    public List<Object> getRecords(String whereClause)
    {
        Connection connection =null;
        try {
            connection = database.getConnection();
            Statement statement = connection.createStatement();
            String selectQuery = "select * from " + tableName + " where " + whereClause;
            System.out.println("Select query >> "+selectQuery);
            ResultSet rs = statement.executeQuery(selectQuery);
            List<Object> records = new ArrayList();

            while(rs.next())
            {
                Object obj = recordClass.newInstance();
                for(Column column: columns)
                {

                   // System.out.println("Settin for "+column.getName() +" "+column.getType());
                    DataType columnDataType = column.getType();
                    Method setterMethod = recordBeanSetterMethods.get(column);
                    setterMethod.invoke(obj,columnDataType.parse(rs.getString(column.getName())));


                }
                records.add(obj);
            }

            return records;

        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
        return null;

    }

    public long patch(Record record)
    {
        return patch(record, null);
    }

    public long patch(Record record, String whereClause) {
        StringBuffer patchQuery = new StringBuffer("update  "+tableName+" set ");

        List<String> updateCoulmnValueList = new ArrayList<>();
        Connection connection = null;
        int numRecordsPatched  = 0;
        boolean first = true;
        String idColumn = null;
        Object idColumnValue = null;

        //String columnNames = Joiner.on(",").join(columns);
       // insertQuery.append(columnNames).append(") values(");
        idColumn = record.getPrimaryKeyColumn();

        for(Column column : columns)
        {
            Method getterMethod = recordBeanGetterMethods.get(column);
            Object columnValue = null;

            try {
                columnValue = getterMethod.invoke(record);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

            if((idColumn!=null && column.getName().equalsIgnoreCase(idColumn)) || column.isAutoIncrement())
            {
                // insertQuery.append("null");
                idColumn = column.getName();
                idColumnValue = columnValue;
                continue;
            }

            if(columnValue==null)
                continue;


            switch (column.getType())
            {
                case STRING:
                    if(columnValue!=null && StringUtils.isNotEmpty(columnValue.toString())) {
                        if(!(column.getName().contains(IMAGE_COLUMN_NAME) && columnValue.equals(DEFAULT_IMAGE_URL)))
                            updateCoulmnValueList.add(column.getName() + " = '" + columnValue + "'");
                    }
                    break;

                case DATE:
                    if(!columnValue.equals(defaultDate))
                        updateCoulmnValueList.add(column.getName()+" = '"+dateTimeFormat.format(columnValue)+"'");
                    break;
                case CHAR:
                    if((char)(columnValue)!=Character.MIN_VALUE)
                        updateCoulmnValueList.add(column.getName()+" = '"+columnValue+"'");
                    break;
                case INT:
                    if((int)(columnValue) > 0)
                        updateCoulmnValueList.add(column.getName()+" = "+columnValue+"");
                    break;
                case LONG:
                    if((long)(columnValue) > 0)
                        updateCoulmnValueList.add(column.getName()+" = "+columnValue+"");
                    break;
                case DOUBLE:
                    if((double)(columnValue) > 0d)
                        updateCoulmnValueList.add(column.getName()+" = "+columnValue+"");
                    break;
                case FLOAT:
                    if((float)(columnValue) > 0)
                        updateCoulmnValueList.add(column.getName()+" = "+columnValue+"");
                    break;
                default:
                    updateCoulmnValueList.add(column.getName()+" = "+columnValue);

            }
        }

        patchQuery.append(Joiner.on(",").join(updateCoulmnValueList));
        patchQuery.append(" where "+idColumn + "="+idColumnValue);
        if(StringUtils.isNotEmpty(whereClause))
            patchQuery.append(" and "+whereClause);
        System.out.println("Patch Query >> "+patchQuery);
        try {
            connection =  database.getConnection();
            Statement statement = connection.createStatement();
            numRecordsPatched = statement.executeUpdate(patchQuery.toString());

            System.out.println("updated count "+numRecordsPatched);
             return numRecordsPatched;


        } catch (Exception e) {
            e.printStackTrace();
        }

        return numRecordsPatched;

    }




    public long insert(Record record) {
        StringBuffer insertQuery = new StringBuffer("insert into "+tableName+" (");
        Connection connection = null;
        int numRecordsInserted  = 0;
        boolean first = true;
        String columnNames = Joiner.on(",").join(columns);
        insertQuery.append(columnNames).append(") values(");

        for(Column column : columns)
        {
            if(record.isColumnExcludedForPersistence(column.getName()))
                continue;
            if(first)
            {
                first = false;
            }
            else
            {
                insertQuery.append(",");
            }

            Method getterMethod = recordBeanGetterMethods.get(column);
            Object columnValue = null;

            try {
                columnValue = getterMethod.invoke(record);
                if((columnValue==null) || column.isAutoIncrement())
                             {
                    insertQuery.append("null");
                    continue;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            switch (column.getType())
            {
                case STRING: {
                    insertQuery.append("'" +columnValue+ "'");
                    break;
                }
                case DATE:

                    insertQuery.append("'"+dateTimeFormat.format(columnValue)+"'");
                    break;
                case CHAR:
                    insertQuery.append("'"+columnValue+"'");
                    break;
                default:
                    insertQuery.append(columnValue);

            }
        }





        insertQuery.append(")");
        System.out.println("Insert Query >> "+insertQuery);
        try {
            connection =  database.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(insertQuery.toString());
            String query = "select LAST_INSERT_ID()";
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            return rs.getLong(1);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return numRecordsInserted;

    }







    static class ColumnComparator implements Comparator<Column>
    {

        public int compare(Column o1, Column o2) {
            return o1.getIndex() - o2.getIndex();
        }
    }

    public List<List<String>> executeQuery(String selectQuery) {
        Connection connection =null;
        try {
            connection = database.getConnection();
            Statement statement = connection.createStatement();
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

    public Connection getConnection()
    {
        try {
            return database.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int delete(String wherClause)
    {
        String deleteQuery = "delete from "+tableName+" where "+wherClause;
        Connection connection = null;
        try {
            connection =  database.getConnection();
            Statement statement = connection.createStatement();
            return statement.executeUpdate(deleteQuery.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    public boolean isRecordPresentInRelatedTable(String relatedTable, String baseTableIdColumn, Object value)
    {
        // Check if route is being used currently, if yes, then send error else delete
        String query = "select count(*) from "+relatedTable+" where "+baseTableIdColumn +" = "+value;
        List<List<String>> records = executeQuery(query);
        Status status = new Status();
        if(records.size()==0 || Long.parseLong(records.get(0).get(0)) == 0)
        {
           /* String whereClause = baseTableIdColumn+ " = "+value;
            delete(whereClause);
            status.setReturnCode(0);
            status.setMessage("SUCCESS");*/
           return true;
        }

        return false;


    }

}
