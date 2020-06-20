package com.mtm.dao.test;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;import java.util.concurrent.ThreadLocalRandom;

import com.mtm.beans.dto.Vehicle;
import com.mtm.dao.VehicleDao;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
/**
 * Created by Admin on 2/24/2019.
 */
public class VehicleDaoTest {


    public static void main(String[] args)
    {
        VehicleDao vehicleDao = new VehicleDao();

        Calendar calendar =  Calendar.getInstance();
        calendar.setTime(new Date());
        System.out.println(" YearMonth is "+calendar.get(Calendar.YEAR)+","+calendar.get(Calendar.MONTH));
        calendar.add(Calendar.MONTH, -1);
        System.out.println(" YearMonth is "+calendar.get(Calendar.YEAR)+","+calendar.get(Calendar.MONTH));
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1 ; // calender's month is 0 indexed




        for(String query : getQueries())

        try {
            int randomNum = ThreadLocalRandom.current().nextInt(3000, 5000);

            Thread.sleep(randomNum);
            System.out.println("Executing now");
           // vehicleDao.runUpdateQuery(query);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }






       /* Vehicle ownerRecord = new Vehicle(2018,"10 Tyres Hyva", 1,"JH03A2768",1);
        long insertId = ownerDao.insert(ownerRecord);*/
        //assertEquals(1,numrecord);
    }



    private static List<String> getQueries()
    {
        List<String> queryList = new ArrayList<>();
        queryList.add("update  vehicle_location set last_longitude = 84.1926765,last_latitude = 24.3728013,last_seen_at = '2020-06-02 07:37:03',last_speed = 7.315708,last_speed_accuracy = 1.3346161,last_bearing = 222.0138,last_bearing_accuracy_degree = 11.061396,last_altitude = 24.3728013 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1918374,last_latitude = 24.3720427,last_seen_at = '2020-06-02 07:37:18',last_speed = 6.1284,last_speed_accuracy = 1.130177,last_bearing = 222.1781,last_bearing_accuracy_degree = 8.462487,last_altitude = 24.3720427 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1909865,last_latitude = 24.3713012,last_seen_at = '2020-06-02 07:37:33',last_speed = 7.5603685,last_speed_accuracy = 1.2317467,last_bearing = 227.02356,last_bearing_accuracy_degree = 7.2905755,last_altitude = 24.3713012 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1900228,last_latitude = 24.3704225,last_seen_at = '2020-06-02 07:37:53',last_speed = 9.262367,last_speed_accuracy = 1.2812884,last_bearing = 227.91411,last_bearing_accuracy_degree = 6.0967817,last_altitude = 24.3704225 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1888882,last_latitude = 24.3695052,last_seen_at = '2020-06-02 07:38:09',last_speed = 7.6481853,last_speed_accuracy = 1.1130588,last_bearing = 222.23138,last_bearing_accuracy_degree = 6.725029,last_altitude = 24.3695052 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1879959,last_latitude = 24.3699051,last_seen_at = '2020-06-02 07:38:43',last_speed = 6.8846073,last_speed_accuracy = 1.2131364,last_bearing = 321.73163,last_bearing_accuracy_degree = 7.537749,last_altitude = 24.3699051 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1870881,last_latitude = 24.3710555,last_seen_at = '2020-06-02 07:38:58',last_speed = 11.329151,last_speed_accuracy = 1.6148374,last_bearing = 324.03268,last_bearing_accuracy_degree = 6.319946,last_altitude = 24.3710555 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1863278,last_latitude = 24.3720108,last_seen_at = '2020-06-02 07:39:08',last_speed = 12.686989,last_speed_accuracy = 1.1957006,last_bearing = 321.1192,last_bearing_accuracy_degree = 4.0227633,last_altitude = 24.3720108 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1856335,last_latitude = 24.3726731,last_seen_at = '2020-06-02 07:39:17',last_speed = 12.428789,last_speed_accuracy = 1.2267437,last_bearing = 299.597,last_bearing_accuracy_degree = 4.540769,last_altitude = 24.3726731 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1845067,last_latitude = 24.3732148,last_seen_at = '2020-06-02 07:39:28',last_speed = 11.800985,last_speed_accuracy = 1.2726351,last_bearing = 297.10254,last_bearing_accuracy_degree = 4.877127,last_altitude = 24.3732148 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1832115,last_latitude = 24.3737613,last_seen_at = '2020-06-02 07:39:44',last_speed = 11.560241,last_speed_accuracy = 1.1250777,last_bearing = 297.9401,last_bearing_accuracy_degree = 4.4614415,last_altitude = 24.3737613 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1820978,last_latitude = 24.3743289,last_seen_at = '2020-06-02 07:39:52',last_speed = 15.210717,last_speed_accuracy = 1.2093387,last_bearing = 300.90192,last_bearing_accuracy_degree = 3.663527,last_altitude = 24.3743289 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1811948,last_latitude = 24.3747163,last_seen_at = '2020-06-02 07:39:58',last_speed = 16.345543,last_speed_accuracy = 1.668832,last_bearing = 291.35898,last_bearing_accuracy_degree = 3.3949242,last_altitude = 24.3747163 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1798375,last_latitude = 24.3751941,last_seen_at = '2020-06-02 07:40:07',last_speed = 16.300936,last_speed_accuracy = 1.2354757,last_bearing = 287.18723,last_bearing_accuracy_degree = 3.5231664,last_altitude = 24.3751941 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1781942,last_latitude = 24.375762,last_seen_at = '2020-06-02 07:40:18',last_speed = 18.044277,last_speed_accuracy = 1.2416924,last_bearing = 295.86905,last_bearing_accuracy_degree = 3.8243675,last_altitude = 24.375762 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1767558,last_latitude = 24.3764615,last_seen_at = '2020-06-02 07:40:27',last_speed = 16.248146,last_speed_accuracy = 1.338432,last_bearing = 298.88098,last_bearing_accuracy_degree = 3.7846718,last_altitude = 24.3764615 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1754542,last_latitude = 24.377085,last_seen_at = '2020-06-02 07:40:37',last_speed = 14.506827,last_speed_accuracy = 2.071835,last_bearing = 298.80194,last_bearing_accuracy_degree = 6.4673147,last_altitude = 24.377085 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1742217,last_latitude = 24.3776807,last_seen_at = '2020-06-02 07:43:13',last_speed = 11.143364,last_speed_accuracy = 1.4358969,last_bearing = 301.90573,last_bearing_accuracy_degree = 6.0431404,last_altitude = 24.3776807 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1730118,last_latitude = 24.3784365,last_seen_at = '2020-06-02 07:43:19',last_speed = 13.65865,last_speed_accuracy = 1.2382245,last_bearing = 304.01907,last_bearing_accuracy_degree = 3.8289404,last_altitude = 24.3784365 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1719582,last_latitude = 24.3791331,last_seen_at = '2020-06-02 07:43:27',last_speed = 13.913795,last_speed_accuracy = 1.4142135,last_bearing = 304.08508,last_bearing_accuracy_degree = 4.424581,last_altitude = 24.3791331 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1708075,last_latitude = 24.3798827,last_seen_at = '2020-06-02 07:43:38',last_speed = 13.887935,last_speed_accuracy = 1.3296992,last_bearing = 304.99203,last_bearing_accuracy_degree = 4.4203176,last_altitude = 24.3798827 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1697007,last_latitude = 24.380616,last_seen_at = '2020-06-02 07:43:48',last_speed = 13.700415,last_speed_accuracy = 1.4758048,last_bearing = 305.0779,last_bearing_accuracy_degree = 4.681528,last_altitude = 24.380616 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1686488,last_latitude = 24.3812984,last_seen_at = '2020-06-02 07:43:58',last_speed = 12.976933,last_speed_accuracy = 1.4142135,last_bearing = 304.98383,last_bearing_accuracy_degree = 4.776308,last_altitude = 24.3812984 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1676507,last_latitude = 24.381931,last_seen_at = '2020-06-02 07:44:07',last_speed = 13.521283,last_speed_accuracy = 1.4142135,last_bearing = 304.99945,last_bearing_accuracy_degree = 4.5644274,last_altitude = 24.381931 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1663511,last_latitude = 24.3825138,last_seen_at = '2020-06-02 07:44:18',last_speed = 13.424953,last_speed_accuracy = 1.4045995,last_bearing = 284.34845,last_bearing_accuracy_degree = 4.7344737,last_altitude = 24.3825138 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1651824,last_latitude = 24.3826843,last_seen_at = '2020-06-02 07:44:27',last_speed = 13.348449,last_speed_accuracy = 1.6825279,last_bearing = 286.81134,last_bearing_accuracy_degree = 5.992968,last_altitude = 24.3826843 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1640063,last_latitude = 24.3831186,last_seen_at = '2020-06-02 07:44:37',last_speed = 13.028782,last_speed_accuracy = 1.2841339,last_bearing = 301.58536,last_bearing_accuracy_degree = 4.6261864,last_altitude = 24.3831186 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1629876,last_latitude = 24.3837989,last_seen_at = '2020-06-02 07:44:47',last_speed = 12.605233,last_speed_accuracy = 1.5831614,last_bearing = 304.99255,last_bearing_accuracy_degree = 5.755385,last_altitude = 24.3837989 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1618066,last_latitude = 24.3845976,last_seen_at = '2020-06-02 07:44:58',last_speed = 13.271923,last_speed_accuracy = 1.2605158,last_bearing = 303.99866,last_bearing_accuracy_degree = 3.9455044,last_altitude = 24.3845976 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1608072,last_latitude = 24.3852419,last_seen_at = '2020-06-02 07:45:07',last_speed = 12.613487,last_speed_accuracy = 1.4536161,last_bearing = 304.00513,last_bearing_accuracy_degree = 5.5337267,last_altitude = 24.3852419 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1598421,last_latitude = 24.3858321,last_seen_at = '2020-06-02 07:45:17',last_speed = 11.7414665,last_speed_accuracy = 1.507216,last_bearing = 303.93137,last_bearing_accuracy_degree = 5.773824,last_altitude = 24.3858321 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.15888,last_latitude = 24.3864978,last_seen_at = '2020-06-02 07:45:28',last_speed = 11.927951,last_speed_accuracy = 3.4532592,last_bearing = 306.6746,last_bearing_accuracy_degree = 5.484174,last_altitude = 24.3864978 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1579642,last_latitude = 24.3871672,last_seen_at = '2020-06-02 07:45:38',last_speed = 12.56088,last_speed_accuracy = 1.2657014,last_bearing = 308.99246,last_bearing_accuracy_degree = 4.1263857,last_altitude = 24.3871672 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1570136,last_latitude = 24.3878403,last_seen_at = '2020-06-02 07:45:48',last_speed = 12.292103,last_speed_accuracy = 1.6058019,last_bearing = 309.85812,last_bearing_accuracy_degree = 5.0372925,last_altitude = 24.3878403 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1562399,last_latitude = 24.3885098,last_seen_at = '2020-06-02 07:45:57',last_speed = 10.82992,last_speed_accuracy = 1.8662529,last_bearing = 311.09653,last_bearing_accuracy_degree = 6.6556396,last_altitude = 24.3885098 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.155288,last_latitude = 24.389243,last_seen_at = '2020-06-02 07:46:08',last_speed = 11.104057,last_speed_accuracy = 1.7240939,last_bearing = 312.7532,last_bearing_accuracy_degree = 5.8334603,last_altitude = 24.389243 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1545331,last_latitude = 24.3898814,last_seen_at = '2020-06-02 07:46:17',last_speed = 11.279946,last_speed_accuracy = 2.0248456,last_bearing = 312.0645,last_bearing_accuracy_degree = 10.912365,last_altitude = 24.3898814 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1535672,last_latitude = 24.390667,last_seen_at = '2020-06-02 07:46:28',last_speed = 11.954438,last_speed_accuracy = 1.5744206,last_bearing = 313.89386,last_bearing_accuracy_degree = 5.2095523,last_altitude = 24.390667 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1528068,last_latitude = 24.3913529,last_seen_at = '2020-06-02 07:46:37',last_speed = 11.539644,last_speed_accuracy = 1.5831614,last_bearing = 313.99863,last_bearing_accuracy_degree = 5.9391637,last_altitude = 24.3913529 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1518415,last_latitude = 24.3921438,last_seen_at = '2020-06-02 07:46:48',last_speed = 11.584079,last_speed_accuracy = 1.4848568,last_bearing = 310.97296,last_bearing_accuracy_degree = 5.7252913,last_altitude = 24.3921438 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1510425,last_latitude = 24.3926949,last_seen_at = '2020-06-02 07:46:58',last_speed = 10.72427,last_speed_accuracy = 1.7149343,last_bearing = 304.3259,last_bearing_accuracy_degree = 6.774694,last_altitude = 24.3926949 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1500514,last_latitude = 24.3932475,last_seen_at = '2020-06-02 07:47:08',last_speed = 12.12374,last_speed_accuracy = 0.92358,last_bearing = 295.1122,last_bearing_accuracy_degree = 4.1235757,last_altitude = 24.3932475 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1490549,last_latitude = 24.393644,last_seen_at = '2020-06-02 07:47:18',last_speed = 11.181025,last_speed_accuracy = 1.480304,last_bearing = 296.877,last_bearing_accuracy_degree = 5.0382266,last_altitude = 24.393644 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1481567,last_latitude = 24.3942258,last_seen_at = '2020-06-02 07:47:28',last_speed = 10.707994,last_speed_accuracy = 0.9013878,last_bearing = 311.86255,last_bearing_accuracy_degree = 3.714216,last_altitude = 24.3942258 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1474592,last_latitude = 24.3948906,last_seen_at = '2020-06-02 07:47:38',last_speed = 7.781675,last_speed_accuracy = 1.25032,last_bearing = 314.0913,last_bearing_accuracy_degree = 7.517189,last_altitude = 24.3948906 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1467282,last_latitude = 24.3956188,last_seen_at = '2020-06-02 07:47:53',last_speed = 6.830649,last_speed_accuracy = 0.5936329,last_bearing = 315.0796,last_altitude = 24.3956188 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1477976,last_latitude = 24.394681,last_seen_at = '2020-06-02 07:51:38',last_speed = 11.386815,last_speed_accuracy = 1.126055,last_bearing = 135.98544,last_bearing_accuracy_degree = 4.4853196,last_altitude = 24.394681 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1486185,last_latitude = 24.3939775,last_seen_at = '2020-06-02 07:51:47',last_speed = 12.739494,last_speed_accuracy = 1.3978913,last_bearing = 128.17691,last_bearing_accuracy_degree = 5.045527,last_altitude = 24.3939775 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1496822,last_latitude = 24.3933735,last_seen_at = '2020-06-02 07:51:57',last_speed = 12.303593,last_speed_accuracy = 1.2801563,last_bearing = 115.160866,last_bearing_accuracy_degree = 4.852296,last_altitude = 24.3933735 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1508231,last_latitude = 24.3928579,last_seen_at = '2020-06-02 07:52:07',last_speed = 12.755002,last_speed_accuracy = 1.7923448,last_bearing = 116.93643,last_bearing_accuracy_degree = 7.2848845,last_altitude = 24.3928579 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1518941,last_latitude = 24.3921329,last_seen_at = '2020-06-02 07:52:17',last_speed = 13.183091,last_speed_accuracy = 1.3755726,last_bearing = 129.9264,last_bearing_accuracy_degree = 4.8031745,last_altitude = 24.3921329 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1529088,last_latitude = 24.3913047,last_seen_at = '2020-06-02 07:52:28',last_speed = 12.583409,last_speed_accuracy = 1.3446189,last_bearing = 131.98268,last_bearing_accuracy_degree = 4.8845105,last_altitude = 24.3913047 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1538629,last_latitude = 24.3905064,last_seen_at = '2020-06-02 07:52:38',last_speed = 13.305339,last_speed_accuracy = 1.1125196,last_bearing = 132.02184,last_bearing_accuracy_degree = 4.430036,last_altitude = 24.3905064 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1547078,last_latitude = 24.3896933,last_seen_at = '2020-06-02 07:52:47',last_speed = 13.406149,last_speed_accuracy = 1.7831433,last_bearing = 133.07819,last_bearing_accuracy_degree = 5.311096,last_altitude = 24.3896933 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1556339,last_latitude = 24.388957,last_seen_at = '2020-06-02 07:52:57',last_speed = 13.164259,last_speed_accuracy = 1.4317821,last_bearing = 132.90047,last_bearing_accuracy_degree = 5.1649756,last_altitude = 24.388957 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1566563,last_latitude = 24.388087,last_seen_at = '2020-06-02 07:53:10',last_speed = 12.872361,last_speed_accuracy = 1.3360015,last_bearing = 130.0684,last_bearing_accuracy_degree = 4.644699,last_altitude = 24.388087 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1575633,last_latitude = 24.3874168,last_seen_at = '2020-06-02 07:53:17',last_speed = 13.329622,last_speed_accuracy = 1.3669308,last_bearing = 128.12566,last_bearing_accuracy_degree = 4.687905,last_altitude = 24.3874168 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1586203,last_latitude = 24.3866948,last_seen_at = '2020-06-02 07:53:27',last_speed = 13.256277,last_speed_accuracy = 1.3879842,last_bearing = 126.05633,last_bearing_accuracy_degree = 4.923181,last_altitude = 24.3866948 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1596737,last_latitude = 24.3860025,last_seen_at = '2020-06-02 07:53:37',last_speed = 13.240455,last_speed_accuracy = 1.6510905,last_bearing = 126.876595,last_bearing_accuracy_degree = 5.256797,last_altitude = 24.3860025 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1606784,last_latitude = 24.3853126,last_seen_at = '2020-06-02 07:53:47',last_speed = 12.971532,last_speed_accuracy = 1.8569868,last_bearing = 122.28662,last_bearing_accuracy_degree = 7.3004384,last_altitude = 24.3853126 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1617747,last_latitude = 24.3846424,last_seen_at = '2020-06-02 07:53:57',last_speed = 12.908137,last_speed_accuracy = 1.3069812,last_bearing = 123.04313,last_bearing_accuracy_degree = 4.5723863,last_altitude = 24.3846424 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1626876,last_latitude = 24.3839949,last_seen_at = '2020-06-02 07:54:07',last_speed = 11.830519,last_speed_accuracy = 1.3842326,last_bearing = 124.09852,last_bearing_accuracy_degree = 5.3481836,last_altitude = 24.3839949 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1637374,last_latitude = 24.383342,last_seen_at = '2020-06-02 07:54:17',last_speed = 12.821427,last_speed_accuracy = 1.2044085,last_bearing = 123.99293,last_bearing_accuracy_degree = 4.370618,last_altitude = 24.383342 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.164833,last_latitude = 24.3827583,last_seen_at = '2020-06-02 07:54:27',last_speed = 12.512624,last_speed_accuracy = 1.3619105,last_bearing = 113.257706,last_bearing_accuracy_degree = 4.90763,last_altitude = 24.3827583 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1659946,last_latitude = 24.3824307,last_seen_at = '2020-06-02 07:54:37',last_speed = 12.130126,last_speed_accuracy = 1.3755726,last_bearing = 106.01649,last_bearing_accuracy_degree = 5.065743,last_altitude = 24.3824307 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1672047,last_latitude = 24.382133,last_seen_at = '2020-06-02 07:54:48',last_speed = 11.594515,last_speed_accuracy = 1.185116,last_bearing = 109.83376,last_bearing_accuracy_degree = 4.5815816,last_altitude = 24.382133 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1684731,last_latitude = 24.3813327,last_seen_at = '2020-06-02 07:55:02',last_speed = 10.628188,last_speed_accuracy = 1.4534442,last_bearing = 124.39944,last_bearing_accuracy_degree = 6.194547,last_altitude = 24.3813327 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1693746,last_latitude = 24.3807775,last_seen_at = '2020-06-02 07:55:12',last_speed = 11.201379,last_speed_accuracy = 1.3705838,last_bearing = 123.07713,last_bearing_accuracy_degree = 5.6813755,last_altitude = 24.3807775 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1704263,last_latitude = 24.3801048,last_seen_at = '2020-06-02 07:55:23',last_speed = 12.913577,last_speed_accuracy = 1.3792752,last_bearing = 124.102264,last_bearing_accuracy_degree = 5.089881,last_altitude = 24.3801048 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1713156,last_latitude = 24.3795432,last_seen_at = '2020-06-02 07:55:32',last_speed = 12.390664,last_speed_accuracy = 1.2640016,last_bearing = 124.01131,last_bearing_accuracy_degree = 4.6160283,last_altitude = 24.3795432 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.172142,last_latitude = 24.3790419,last_seen_at = '2020-06-02 07:55:42',last_speed = 4.058791,last_speed_accuracy = 1.2841339,last_bearing = 130.99341,last_bearing_accuracy_degree = 19.165525,last_altitude = 24.3790419 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1731471,last_latitude = 24.3784375,last_seen_at = '2020-06-02 07:55:58',last_speed = 11.54234,last_speed_accuracy = 1.1936499,last_bearing = 123.09114,last_bearing_accuracy_degree = 4.7140994,last_altitude = 24.3784375 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1740826,last_latitude = 24.3778454,last_seen_at = '2020-06-02 07:56:07',last_speed = 13.265864,last_speed_accuracy = 1.2847179,last_bearing = 121.14216,last_bearing_accuracy_degree = 4.3806353,last_altitude = 24.3778454 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1754007,last_latitude = 24.3771728,last_seen_at = '2020-06-02 07:56:18',last_speed = 13.499643,last_speed_accuracy = 1.185116,last_bearing = 118.03208,last_bearing_accuracy_degree = 4.012858,last_altitude = 24.3771728 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1763923,last_latitude = 24.3766477,last_seen_at = '2020-06-02 07:56:28',last_speed = 9.937424,last_speed_accuracy = 1.2159358,last_bearing = 118.96079,last_bearing_accuracy_degree = 5.661415,last_altitude = 24.3766477 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1773013,last_latitude = 24.3762273,last_seen_at = '2020-06-02 07:56:38',last_speed = 11.330009,last_speed_accuracy = 1.2245,last_bearing = 116.97753,last_bearing_accuracy_degree = 4.9331026,last_altitude = 24.3762273 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1783678,last_latitude = 24.3757128,last_seen_at = '2020-06-02 07:56:49',last_speed = 11.426221,last_speed_accuracy = 1.130177,last_bearing = 115.07046,last_bearing_accuracy_degree = 4.4827104,last_altitude = 24.3757128 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1797366,last_latitude = 24.3751554,last_seen_at = '2020-06-02 07:57:02',last_speed = 11.744782,last_speed_accuracy = 1.2801563,last_bearing = 106.26474,last_bearing_accuracy_degree = 5.0502596,last_altitude = 24.3751554 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1809665,last_latitude = 24.3747532,last_seen_at = '2020-06-02 07:57:13',last_speed = 13.010866,last_speed_accuracy = 1.3102671,last_bearing = 110.05012,last_bearing_accuracy_degree = 4.402818,last_altitude = 24.3747532 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1820571,last_latitude = 24.3743551,last_seen_at = '2020-06-02 07:57:22',last_speed = 12.535734,last_speed_accuracy = 1.192015,last_bearing = 116.83082,last_bearing_accuracy_degree = 4.378667,last_altitude = 24.3743551 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1832499,last_latitude = 24.3737546,last_seen_at = '2020-06-02 07:57:33',last_speed = 12.053472,last_speed_accuracy = 1.2382245,last_bearing = 117.96911,last_bearing_accuracy_degree = 4.713573,last_altitude = 24.3737546 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1842584,last_latitude = 24.3732555,last_seen_at = '2020-06-02 07:57:43',last_speed = 11.458204,last_speed_accuracy = 1.5384408,last_bearing = 117.95206,last_bearing_accuracy_degree = 6.441732,last_altitude = 24.3732555 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1852919,last_latitude = 24.3727757,last_seen_at = '2020-06-02 07:57:53',last_speed = 11.247829,last_speed_accuracy = 1.1903781,last_bearing = 118.819695,last_bearing_accuracy_degree = 4.825324,last_altitude = 24.3727757 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1862056,last_latitude = 24.3723021,last_seen_at = '2020-06-02 07:58:02',last_speed = 10.843274,last_speed_accuracy = 1.2578156,last_bearing = 137.00774,last_bearing_accuracy_degree = 5.4062366,last_altitude = 24.3723021 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1868396,last_latitude = 24.3715347,last_seen_at = '2020-06-02 07:58:12',last_speed = 10.985265,last_speed_accuracy = 1.2022063,last_bearing = 142.02684,last_bearing_accuracy_degree = 4.966037,last_altitude = 24.3715347 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1875374,last_latitude = 24.3705479,last_seen_at = '2020-06-02 07:58:23',last_speed = 11.028807,last_speed_accuracy = 1.3755726,last_bearing = 146.02351,last_bearing_accuracy_degree = 5.481365,last_altitude = 24.3705479 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1881936,last_latitude = 24.369569,last_seen_at = '2020-06-02 07:58:37',last_speed = 3.980491,last_speed_accuracy = 1.8569868,last_bearing = 161.97229,last_bearing_accuracy_degree = 29.650991,last_altitude = 24.369569 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1893115,last_latitude = 24.3698705,last_seen_at = '2020-06-02 07:59:09',last_speed = 7.243896,last_speed_accuracy = 1.529477,last_bearing = 41.333595,last_bearing_accuracy_degree = 12.358015,last_altitude = 24.3698705 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1901129,last_latitude = 24.3704632,last_seen_at = '2020-06-02 07:59:28',last_speed = 7.2485666,last_speed_accuracy = 0.91263354,last_bearing = 46.095963,last_bearing_accuracy_degree = 5.559666,last_altitude = 24.3704632 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1908689,last_latitude = 24.3711866,last_seen_at = '2020-06-02 07:59:43',last_speed = 4.138588,last_speed_accuracy = 1.2416924,last_bearing = 42.05332,last_bearing_accuracy_degree = 16.978436,last_altitude = 24.3711866 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1916225,last_latitude = 24.3719045,last_seen_at = '2020-06-02 08:00:08',last_speed = 5.218761,last_speed_accuracy = 1.3259336,last_bearing = 43.96488,last_bearing_accuracy_degree = 14.314059,last_altitude = 24.3719045 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1925872,last_latitude = 24.3727261,last_seen_at = '2020-06-02 08:00:37',last_speed = 5.691098,last_speed_accuracy = 1.3511846,last_bearing = 44.396362,last_bearing_accuracy_degree = 13.632696,last_altitude = 24.3727261 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1927223,last_latitude = 24.3736393,last_seen_at = '2020-06-02 08:01:07',last_speed = 3.6102424,last_bearing = 358.9941,last_altitude = 24.3736393 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1969912,last_latitude = 24.3781628,last_seen_at = '2020-06-02 08:03:51',last_altitude = 24.3781628 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1929911,last_latitude = 24.3736391,last_seen_at = '2020-06-02 08:03:52',last_bearing = 37.5061,last_altitude = 24.3736391 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1969912,last_latitude = 24.3781628,last_seen_at = '2020-06-02 08:24:38',last_altitude = 24.3781628 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.193257,last_latitude = 24.3735239,last_seen_at = '2020-06-02 08:24:38',last_speed_accuracy = 0.57982755,last_altitude = 24.3735239 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1969912,last_latitude = 24.3781628,last_seen_at = '2020-06-02 08:26:23',last_altitude = 24.3781628 where vehicleid=7");

        queryList.add("update  vehicle_location set last_longitude = 84.1932828,last_latitude = 24.3735713,last_seen_at = '2020-06-02 08:26:26',last_speed_accuracy = 0.43416587,last_altitude = 24.3735713 where vehicleid=7");

        return queryList;
    }

}
