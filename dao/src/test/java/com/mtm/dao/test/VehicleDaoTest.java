package com.mtm.dao.test;

import com.mtm.beans.dto.VehicleRecord;
import com.mtm.dao.VehicleDao;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
/**
 * Created by Admin on 2/24/2019.
 */
public class VehicleDaoTest {

    @Test
    public void testInsert()
    {
        VehicleDao ownerDao = new VehicleDao();
        VehicleRecord ownerRecord = new VehicleRecord(2018,"12 Tyres Hyva", 0,"JH032968",1);
        int numrecord = ownerDao.insert(ownerRecord);
        assertEquals(1,numrecord);
    }


}
