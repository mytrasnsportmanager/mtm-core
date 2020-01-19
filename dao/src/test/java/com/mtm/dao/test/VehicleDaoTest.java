package com.mtm.dao.test;

import com.mtm.beans.dto.Vehicle;
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
        Vehicle ownerRecord = new Vehicle(2018,"10 Tyres Hyva", 1,"JH03A2768",1);
        long insertId = ownerDao.insert(ownerRecord);
        //assertEquals(1,numrecord);
    }


}
