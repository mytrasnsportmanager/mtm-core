package com.mtm.dao;

import com.mtm.beans.dto.Notification;
import com.mtm.beans.dto.Owner;
import com.mtm.beans.dto.Record;

import java.util.List;

/**
 * Created by Admin on 4/10/2020.
 */
public class NotificationDao extends AbstractDao {
    private static final String TABLE_NAME = "notification";
    private static final  Class RECORD_CLASS = Notification.class;
    public NotificationDao()
    {
        super(TABLE_NAME, RECORD_CLASS);
    }

    @Override
    public List<Record> getConvertedRecords(String whereClause) {
        return null;
    }
}
