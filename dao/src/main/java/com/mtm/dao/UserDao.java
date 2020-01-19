package com.mtm.dao;

import com.mtm.beans.Status;
import com.mtm.beans.UserType;
import com.mtm.beans.dto.*;

import java.util.List;

/**
 * Created by Admin on 9/15/2019.
 */
public class UserDao extends AbstractDao{

    private static final String TABLE_NAME = "user";
    private static final  Class RECORD_CLASS = User.class;

    private OwnerDao ownerDao = new OwnerDao();
    private ConsignerDao consignerDao = new ConsignerDao();
    private DriverDao driverDao = new DriverDao();


    public UserDao()
    {
        super(TABLE_NAME, RECORD_CLASS);
    }

    public List<Record> getConvertedRecords(String whereClause) {
        return null;
    }

    public Status addUser(User user)
    {
        long insertedId = 0;
        Status status = new Status();

        if(user.getUsertype().equalsIgnoreCase(UserType.OWNER.toString()))
        {
            Owner owner = new Owner();
            owner.setName(user.getName());
            owner.setAddress(user.getAddress());
            owner.setContact(user.getContact());
            insertedId =  ownerDao.insert(owner);
        }
        else if (user.getUsertype().equalsIgnoreCase(UserType.DRIVER.toString()))
        {
            VehicleDriver driver = new VehicleDriver();
            driver.setName(user.getName());
            driver.setAddress(user.getAddress());
            driver.setContact(user.getContact());
            insertedId =  driverDao.insert(driver);
        }
        else if (user.getUsertype().equalsIgnoreCase(UserType.CONSIGNER.toString()))
        {
            Consigner consigner = new Consigner();
            consigner.setName(user.getName());
            consigner.setAddress(user.getAddress());
            consigner.setContact(user.getContact());
            insertedId = consignerDao.insert(consigner);


        }
        user.setUserid(insertedId);
        insert(user);
        status.setReturnCode(0);
        status.setInsertedId(insertedId);
        status.setMessage("SUCCESS");
        return status;


    }



}
