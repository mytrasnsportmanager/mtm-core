package com.mtm.beans;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.Date;

/**
 * Created by Admin on 2/22/2020.
 */
public class DateAsString  {

    public static void main(String[] args)
    {
        Date date = new Date();
        DateTimeZone timeZoneIndia = DateTimeZone.forID( "Asia/Kolkata" );
        DateTime nowIndia = DateTime.now(timeZoneIndia);
        System.out.println("Date is "+date);
        System.out.println("Now in India is "+nowIndia.toDate());
        //txn.setTxn_date(nowIndia.toDate());


    }
}
