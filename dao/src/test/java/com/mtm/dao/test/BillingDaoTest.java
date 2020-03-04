package com.mtm.dao.test;

import com.mtm.beans.UserType;
import com.mtm.beans.dto.User;
import com.mtm.dao.BillingDao;
import com.mtm.dao.UserDao;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by Admin on 1/19/2020.
 */
public class BillingDaoTest {

    public static void main(String[] args)
    {
       // BillingDao billingDao = new BillingDao();
      // billingDao.performMonthlyBilling(1,2);

        String password = "247896";

       /* User user = new User();
        user.setContact(9673831235l);
        user.setUsertype(UserType.OWNER.toString());
        user.setName("Abhishek Kunal");
        user.setAddress("H No 101, Block Road , Chhattarpur");
        user.setPassphrase("StrongPwd1$");
        user.setExternaluid("uZJOgGv4YkcQxR8hzU01RRa2BE73");
        UserDao userDao = new UserDao();
        userDao.addUser(user);
        */

       System.out.println(DigestUtils.sha1Hex(password));

    }
}
