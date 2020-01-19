package com.mtm.dao.test;

import com.mtm.dao.OwnerDao;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;

/**
 * Created by Admin on 2/24/2019.
 */
public class OwnerDaoTest {

   /* public static void main (String args[])
    {
        OwnerDao ownerDao = new OwnerDao();
        Owner ownerRecord = new Owner("Kamakhya Narayan Singh","H No 101, Block Road , Chhattarpur",938509325);
        ownerDao.insert(ownerRecord);
    }*/

    @Test
    public void testSearch()
    {
        OwnerDao ownerDao = new OwnerDao();
        List<List<String>> records = ownerDao.get(" upper(name) like '%KAMAKHYA%'");
        assertEquals(1,records.size());

    }


}
