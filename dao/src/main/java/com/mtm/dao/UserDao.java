package com.mtm.dao;

import com.google.firebase.auth.UserRecord;
import com.mtm.auth.FirebaseUserUtil;
import com.mtm.beans.Accountant;
import com.mtm.beans.Status;
import com.mtm.beans.UserType;
import com.mtm.beans.dto.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
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
    private AccountantDao accountantDao = new AccountantDao();


    public UserDao()
    {
        super(TABLE_NAME, RECORD_CLASS);
    }

    public List<Record> getConvertedRecords(String whereClause) {
        List<List<String>> records = get(whereClause);
        List<Record> userRecords = new ArrayList<>();
        User user = new User();
        for(List<String> record: records)
        {
            user.setUserid(Long.parseLong(record.get(0)));
            user.setName(record.get(1));
            user.setAddress(record.get(2));
            user.setContact(Long.parseLong(record.get(3)));
            user.setUsertype(record.get(4));
           // user.setPassphrase(record.get(4));
            user.setDevice_id(record.get(7));
            userRecords.add(user);

        }


        return userRecords;
    }

    public Status addUser(User user)
    {
        Status status = new Status();
        boolean wasProvisionallyRegisteredByOwner = false;
        List<Object> userDBRecords = this.getRecords("contact = "+user.getContact());
        if(userDBRecords.size()!=0)
        {
            if(userDBRecords.size()==1)
            {
                User userDBRecord = (User)userDBRecords.get(0);
                if(userDBRecord.getRegistered_by()==null || !userDBRecord.getRegistered_by().equalsIgnoreCase("OWNER"))
                {
                    status.setReturnCode(1);
                    status.setMessage("USER_EXISTS");
                    System.out.println("User already exists");
                    return status;
                }
                else if(userDBRecord.getRegistered_by().equalsIgnoreCase("OWNER"))
                {
                    if(userDBRecord.getUsertype().equalsIgnoreCase(user.getUsertype())) {
                        user.setRegistered_by("SELF");
                        user.setUserid(userDBRecord.getUserid());
                        wasProvisionallyRegisteredByOwner = true;
                    }
                    else
                    {
                        /* The user wants to register as a different type from what someone has provisionally registered him
                        If the provisionally registered user in not involved in any trip, txn , allow the user to do so
                        else throw error
                        */
                        if(userDBRecord.getUsertype().equalsIgnoreCase("CONSIGNER"))
                        {
                            long consignerid = userDBRecord.getUserid();
                            BillingDao billingDao = new BillingDao();
                            String pendingSettlementMessage = billingDao.getPendingSettlementMeessage(consignerid);
                            if(StringUtils.isNotEmpty(pendingSettlementMessage))
                            {
                                status.setReturnCode(99);
                                status.setMessage(pendingSettlementMessage);
                                return status;
                            }
                            else
                            {
                                // This consignerid didn't have any accountabiliy, allow him to switch role
                                OwnerConsignerDao ownerConsignerDao = new OwnerConsignerDao();
                                ownerConsignerDao.delete(" consignerid "+consignerid);
                                consignerDao.delete(" consignerid "+consignerid);
                                delete(" userid = "+consignerid+" and usertype = 'CONSIGNER'");
                                user.setRegistered_by("SELF");
                            }


                        }



                    }
                }
            }

        }
        else if(userDBRecords.size()==0 && user.getRegistered_by()==null)
        {
            user.setRegistered_by("SELF");
        }

        if((user.getRegistered_by()==null || !user.getRegistered_by().equalsIgnoreCase("OWNER")) && !isValidFirebaseUser(user))
        {
            status.setReturnCode(1);
            status.setMessage("User not found");
            return status;
        }

        long insertedId = 0;


        if(user.getUsertype().equalsIgnoreCase(UserType.OWNER.toString()))
        {
            Owner owner = new Owner();
            owner.setName(user.getName());
            owner.setAddress(user.getAddress());
            owner.setContact(user.getContact());
            insertedId =  ownerDao.insert(owner);
        }
        else if (user.getUsertype().equalsIgnoreCase(UserType.DRIVER.toString()) && !wasProvisionallyRegisteredByOwner)
        {
            VehicleDriver driver = new VehicleDriver();
            driver.setName(user.getName());
            driver.setAddress(user.getAddress());
            driver.setContact(user.getContact());
            insertedId =  driverDao.insert(driver);
        }
        else if (user.getUsertype().equalsIgnoreCase(UserType.CONSIGNER.toString()) && !wasProvisionallyRegisteredByOwner)
        {
            Consigner consigner = new Consigner();
            consigner.setName(user.getName());
            consigner.setAddress(user.getAddress());
            consigner.setContact(user.getContact());
            insertedId = consignerDao.insert(consigner);


        }
        else if (user.getUsertype().equalsIgnoreCase(UserType.ACCOUNTANT.toString()) && !wasProvisionallyRegisteredByOwner)
        {

            Accountant accountant = new Accountant();
            accountant.setContact(user.getContact());
            accountant.setName(user.getName());
            insertedId = accountantDao.insert(accountant);


        }
        if(user.getPassphrase()!=null) {
            String hashedPhrase = DigestUtils.sha1Hex(user.getPassphrase());
            user.setPassphrase(hashedPhrase);
        }

        if(wasProvisionallyRegisteredByOwner)
        {
            patch(user, "usertype='"+user.getUsertype()+"'");
            status.setReturnCode(0);
            status.setInsertedId(user.getUserid());
            status.setMessage("SUCCESS");
            return status;
        }
        else {
            user.setUserid(insertedId);
            insert(user);
            status.setReturnCode(0);
            status.setInsertedId(insertedId);
            status.setMessage("SUCCESS");
            return status;
        }


    }

    private boolean isValidFirebaseUser(User user)
    {
        UserRecord userRecord = FirebaseUserUtil.getUserByPhone("+91"+user.getContact());
        if(userRecord==null)
            return false;
        if(user.getExternaluid().equals(userRecord.getUid()))
            return true;
        return false;

    }



}
