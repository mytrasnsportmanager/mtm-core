package com.mtm.dao.test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Created by Admin on 2/24/2019.
 */
public class TestRunner {

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(OwnerDaoTest.class,VehicleDaoTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println("Result=="+result.wasSuccessful());
    }
}
