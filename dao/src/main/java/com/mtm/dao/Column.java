package com.mtm.dao;

import com.mtm.dao.beans.DataType;

/**
 * Created by Admin on 3/4/2019.
 */
public class Column {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataType getType() {
        return type;
    }

    public void setType(DataType type) {
        this.type = type;
    }

    public boolean isNullable() {
        return isNullable;
    }

    public void setNullable(boolean nullable) {
        isNullable = nullable;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }

    public boolean isAutoIncrement() {
        return isAutoIncrement;
    }

    public void setAutoIncrement(boolean autoIncrement) {
        isAutoIncrement = autoIncrement;
    }

    @Override
    public String toString() {
        return name;
    }

    private boolean isAutoIncrement;
    private String name;
    private DataType type;
    private boolean isNullable;
    private int index;
    private boolean isPrimaryKey;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Column column = (Column) o;

        if (isAutoIncrement != column.isAutoIncrement) return false;
        if (isNullable != column.isNullable) return false;
        if (index != column.index) return false;
        if (isPrimaryKey != column.isPrimaryKey) return false;
        if (!name.equals(column.name)) return false;
        if (type != column.type) return false;
        return tableName.equals(column.tableName);
    }

    @Override
    public int hashCode() {
        int result = (isAutoIncrement ? 1 : 0);
        result = 31 * result + name.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + (isNullable ? 1 : 0);
        result = 31 * result + index;
        result = 31 * result + (isPrimaryKey ? 1 : 0);
        result = 31 * result + tableName.hashCode();
        return result;
    }

    public String getTableName() {
        return tableName;
}

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    private String tableName;

}
