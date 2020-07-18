package com.mtm.auth;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Admin on 1/19/2020.
 */
public class FirebaseUserUtil {

    private static  FirebaseApp firebaseApp;
    private static GoogleCredentials googleCredentials;

    static
    {
        FileInputStream refreshToken = null;
        try {
           refreshToken = new FileInputStream("/home/mtmuser/proj/deployed/mtm/proserviceaccoutkey.json");
           //refreshToken = new FileInputStream("C:/prj/mtm/proserviceaccoutkey.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        FirebaseOptions options = null;
        try {
            googleCredentials= GoogleCredentials.fromStream(refreshToken).createScoped("https://www.googleapis.com/auth/firebase.messaging");
            options = new FirebaseOptions.Builder()
                    .setCredentials(googleCredentials).build();
                    //.setDatabaseUrl("https://<DATABASE_NAME>.firebaseio.com/")
                    //.build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        firebaseApp = FirebaseApp.initializeApp(options);
    }

    public synchronized static FirebaseApp getFirebaseApp()
    {
        return firebaseApp;
    }

    public static synchronized String getAccessToken()
    {
        if(googleCredentials!=null)
            try {
                googleCredentials.refresh();
            } catch (IOException e) {
                e.printStackTrace();
            }
        return googleCredentials.getAccessToken().getTokenValue();
    }


    public static void main(String[] args)
    {
        try {
          //  createUser();
           checkUser("dummy","dummy");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static boolean checkUser(String uid, String password) throws Exception {


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance(firebaseApp);
        UserRecord userRecord = firebaseAuth.getUserByPhoneNumber("+919386509325");
        System.out.println(userRecord.getDisplayName());

        return true;
    }

    public static UserRecord getUserByPhone(String phoneNumber)  {


        try {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance(firebaseApp);
            UserRecord userRecord = firebaseAuth.getUserByPhoneNumber(phoneNumber);
            return userRecord;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("Error getting detailes for "+phoneNumber);
            return null;
        }
        //System.out.println(userRecord.getDisplayName());


    }



    public static boolean createUser( UserRecord.CreateRequest request)
    {
        /*UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail("user@mtm.com")
                .setEmailVerified(false)
                .setPassword("dummyp")
                .setPhoneNumber("+919386509325")
                .setDisplayName("Test Representative")
                .setPhotoUrl("http://www.example.com/12345678/photo.png")
                .setDisabled(false);*/

        UserRecord userRecord = null;
        try {
            userRecord = FirebaseAuth.getInstance(firebaseApp).createUser(request);
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            return false;
        }
        if(userRecord.getUid()!=null)
            return true;
        else
            return false;

    }


}
