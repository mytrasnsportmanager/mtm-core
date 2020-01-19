package com.mtm.dao.test;

import com.mtm.dao.BillingDao;

/**
 * Created by Admin on 1/19/2020.
 */
public class BillingDaoTest {

    public static void main(String[] args)
    {
        BillingDao billingDao = new BillingDao();
        billingDao.performMonthlyBilling(1,2);
    }
}
