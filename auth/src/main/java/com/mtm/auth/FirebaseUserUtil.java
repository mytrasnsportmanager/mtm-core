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

    static
    {
        FileInputStream refreshToken = null;
        try {
            refreshToken = new FileInputStream("/home/mtmuser/proj/deployed/mtm/proserviceaccoutkey.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        FirebaseOptions options = null;
        try {
            options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(refreshToken))
                    //.setDatabaseUrl("https://<DATABASE_NAME>.firebaseio.com/")
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }

        firebaseApp = FirebaseApp.initializeApp(options);
    }

    public static void main(String[] args)
    {
        try {
           // createUser();
            checkUser("dummy","dummy");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static boolean checkUser(String uid, String password) throws Exception {


        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance(firebaseApp);
        UserRecord userRecord = firebaseAuth.getUserByPhoneNumber("+918830354167");
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
            System.out.println("Error getting detailes for "+phoneNumber);
            return null;
        }
        //System.out.println(userRecord.getDisplayName());


    }



    private static void createUser()
    {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail("abhi1iips2@gmail.com")
                .setEmailVerified(false)
                .setPassword("Monitoring123")
                .setPhoneNumber("+918830354167")
                .setDisplayName("John Doe")
                .setPhotoUrl("http://www.example.com/12345678/photo.png")
                .setDisabled(false);

        UserRecord userRecord = null;
        try {
            userRecord = FirebaseAuth.getInstance(firebaseApp).createUser(request);
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        }
        System.out.println("Successfully created new user: " + userRecord.getUid());
    }


}
