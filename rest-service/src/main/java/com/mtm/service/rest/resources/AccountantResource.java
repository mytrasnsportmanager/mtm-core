package com.mtm.service.rest.resources;

import com.google.firebase.auth.UserRecord;
import com.mtm.auth.FirebaseUserUtil;
import com.mtm.beans.*;
import com.mtm.beans.dto.Owner;
import com.mtm.beans.dto.Record;
import com.mtm.beans.dto.User;
import com.mtm.dao.AccountantDao;
import com.mtm.dao.AccountantVehicleDao;
import com.mtm.dao.OwnerDao;
import com.mtm.dao.UserDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import java.util.List;

/**
 * Created by Admin on 5/29/2020.
 */
public class AccountantResource extends AbstractRestResource {

    @Context
    HttpServletRequest req;
    @Context
    HttpServletResponse res;
    private static final AccountantDao dao = new AccountantDao();
    private static final AccountantVehicleDao accountantVehicleDao = new AccountantVehicleDao();
    private static final UserDao userDao = new UserDao();
    public AccountantResource() {
        super(dao);
    }

    @POST
    @Path("/owneraccountant")
    public Object addAccountant(AccountantChangeRequest accountantChangeRequest)

    {
        Status status = new Status();
        long accountantid = 0;
        List<Record> records = dao.getConvertedRecords(" contact = "+accountantChangeRequest.getContact());

        // Create a firebase user first if it doesn't exist

        if(records!=null && records.size() == 0) {

            UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                    .setEmail(accountantChangeRequest.getContact()+"@mtm.com")
                    .setEmailVerified(false)
                    .setPassword("dummyp")
                    .setPhoneNumber("+91" + accountantChangeRequest.getContact())
                    .setDisplayName(accountantChangeRequest.getName())
                    .setPhotoUrl("http://www.example.com/12345678/photo.png")
                    .setDisabled(false);

            boolean firebaseUserCreated = FirebaseUserUtil.createUser(request);
            if (!firebaseUserCreated) {
                status.setReturnCode(1);
                status.setMessage("User could not be created");
                return status;
            }

            User user = new User();
            user.setContact(accountantChangeRequest.getContact());
            user.setPassphrase(accountantChangeRequest.getPassphrase());
            user.setName(accountantChangeRequest.getName());
            user.setRegistered_by("OWNER");
            user.setUsertype(UserType.ACCOUNTANT.toString());
            accountantid = userDao.addUser(user).getInsertedId();
        }
        else
        {
            accountantid = ((Accountant) records.get(0)).getAccountantid();
        }
        //delete existing accountant for this vehicle

        accountantVehicleDao.delete(" vehicleid = "+accountantChangeRequest.getVehicleid());

        AccountantVehicle accountantVehicle = new AccountantVehicle();
        accountantVehicle.setAccountantid(accountantid);
        accountantVehicle.setVehicleid(accountantChangeRequest.getVehicleid());
        accountantVehicleDao.insert(accountantVehicle);
        pushVehicleToSession(accountantVehicle.getVehicleid());

        status.setMessage("SUCCESS");
        status.setReturnCode(0);
        return status;

        //return create(owner);
    }

    private void pushVehicleToSession(long vehicleid)
    {
        HttpSession session= req.getSession();
        UserSession userSession = (UserSession) session.getAttribute("user_session");
        userSession.getVehicleIdList().add(vehicleid);
        session.setAttribute("user_session",userSession);
    }


}
