package com.mtm.service.rest.auth;

/**
 * Created by Admin on 3/8/2020.
 */
public class TripAuthorizerFactory {

    private static final String TRIPS_PATH = "/mtm/trips";
    private static final String TXNS_PATH = "/mtm/txns";
    private static final String VEHICLES_PATH = "/mtm/vehicles";
    private static final String OWNERS_PATH = "/mtm/owners";
    private static final String DRIVERS_PATH = "/mtm/drivers";
    private static final String CONSIGNERS_PATH = "/mtm/consigners";
    private static final String USERS_PATH = "/mtm/users";


   /* public synchronized static Authorizer getAuthorizer(String resourcePath)
    {
        if(resourcePath.contains(TRIPS_PATH))
            return new TripAuthorizer();
    }*/


}
