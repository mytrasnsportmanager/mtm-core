package com.mtm.dao;

import com.mtm.beans.dto.AndroidAppVersion;
import com.mtm.beans.dto.Record;
import com.mtm.beans.dto.VehicleDriver;

import java.util.List;

/**
 * Created by Admin on 7/9/2020.
 */
public class AndroidVersionDao extends AbstractDao {

    private static final String TABLE_NAME = "android_app_version";
    private static final  Class RECORD_CLASS = AndroidAppVersion.class;
    public AndroidVersionDao()
    {
        super(TABLE_NAME, RECORD_CLASS);
    }
    public List<Record> getConvertedRecords(String whereClause) {
        return null;
    }
}
