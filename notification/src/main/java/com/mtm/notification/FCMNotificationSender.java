package com.mtm.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.mtm.auth.FirebaseUserUtil;
import com.mtm.beans.UserType;
import com.mtm.beans.dto.DataNotification;
import com.mtm.beans.dto.Notification;
import com.mtm.beans.dto.NotificationDetail;
import com.mtm.beans.dto.Owner;
import com.mtm.dao.NotificationDao;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by Admin on 4/10/2020.
 */
public class FCMNotificationSender {

    private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private static  NotificationDao notificationDao = new NotificationDao() ;




    public static synchronized void  send (com.mtm.beans.dto.Message message, long vehicleid,  long userid, long consignerid, UserType userType, boolean cascade)
    {
        String userDeviceId = null;
        List<List<String>> records = notificationDao.executeQuery("select device_id from user where usertype = '"+userType.toString()+"' and userid = "+userid);
        if(records!=null && records.size() > 0)
        {
            userDeviceId = records.get(0).get(0);
        }

        switch (userType)
        {

            case OWNER:
            {
                // Get the accountant id


                DataNotification dataNotification = (DataNotification)message.getMessage();
                dataNotification.setToken(userDeviceId);
                Notification notification = (Notification) dataNotification.getData();
                notification.setUserid(userid);
                notification.setUsertype("OWNER");


                send(message);
                // Send it to accountant as well
                if(cascade) {
                    String accountantDeviceId = null;
                    List<List<String>> accountantRecords = notificationDao.executeQuery("select device_id from user where usertype = 'ACCOUNTANT' and userid in ( select accountantid from accountant_vehicle where vehicleid = "+vehicleid+")");
                    if(accountantRecords!=null && accountantRecords.size() > 0)
                    {
                        accountantDeviceId = accountantRecords.get(0).get(0);

                    }
                    notification.setUserid(userid);
                    notification.setUsertype("ACCOUNTANT");
                    dataNotification.setToken(accountantDeviceId);
                    send(message);

                    // Send it to consigner as well
                    String consignerDeviceId = null;
                    List<List<String>> consignerRecords = notificationDao.executeQuery("select device_id from user where usertype = 'CONSIGNER' and userid = "+consignerid);
                    if(consignerRecords!=null && consignerRecords.size() > 0)
                    {
                        consignerDeviceId = consignerRecords.get(0).get(0);

                    }
                    notification.setUserid(consignerid);
                    notification.setUsertype("CONSIGNER");
                    dataNotification.setToken(consignerDeviceId);
                    send(message);

                }


            }
                break;
            case CONSIGNER:
                break;
            case ACCOUNTANT:

            case DRIVER:

            case NONE:

            default:




        }



    }



  public synchronized static String send (com.mtm.beans.dto.Message message)
    {
        //String registrationToken = notification.getDevice_id();

// See documentation on defining a message payload.

        Notification notification = (Notification)((DataNotification)(message.getMessage())).getData();

        if(notification.getUser_device_id()!=null)
            notification.setOnline("Y");
        else
            notification.setOnline("N");

        notificationDao.insert(notification);

        if(notification.getUser_device_id()==null)
            return null;

      /*  Message message = Message.builder()
                .setNotification(com.google.firebase.messaging.Notification.builder().setTitle(notification.getTitle()).setBody(notification.getBody()).build())
                .setToken(registrationToken)
                .build(); */



// Send a message to the device corresponding to the provided
// registration token.

        String response = null;
        String jsonString = null;
       ((DataNotification)(message.getMessage())).setData(getNotificationDetail(notification));
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            jsonString = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        try {
            String accessToken = FirebaseUserUtil.getAccessToken();
            sendDataNotification(jsonString,accessToken);

        } catch (Exception e) {
            e.printStackTrace();
        }
// Response is a message ID string.
        System.out.println("Successfully sent message: " + response);
        return response;
    }



    private  static void sendDataNotification(String dataNotificationStr , String accessToken)  {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            final HttpPost httpPost = new HttpPost("https://fcm.googleapis.com/v1/projects/mtm-firebase/messages:send");
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Authorization", "Bearer "+ accessToken);


            //System.out.println("Executing request " + httpget.getRequestLine());

                System.out.println(dataNotificationStr);

           // dataNotificationStr = "{\"message\":{\"token\":\"d66NV5d0SYWLJxXfFM-8fa:APA91bEUnpUIg3pY10AlKlUIrW8BtrGM2zs8ZdTSbULmj0drD2vWXTKXuI_7UYpMtJESkgiij7b_QqxYQd8jEaDobCfS8FS838YCo-ODzWJZB7brSmtWai_kAwMzcTX19oMsaEa1ST7L\",\"data\":{\"rowid\":null,\"user_device_id\":\"d66NV5d0SYWLJxXfFM-8fa:APA91bEUnpUIg3pY10AlKlUIrW8BtrGM2zs8ZdTSbULmj0drD2vWXTKXuI_7UYpMtJESkgiij7b_QqxYQd8jEaDobCfS8FS838YCo-ODzWJZB7brSmtWai_kAwMzcTX19oMsaEa1ST7L\",\"messagetitle\":\"Document Uploaded by Driver\",\"messagetext\":\" A document has been uploaded by null for JH09A2968, check the messge to view the document\",\"messageread\":null,\"messageid\":\"0\",\"notificationtime\":\"2020-05-28 01:59:49\",\"online\":null,\"notificationread\":null,\"fcmrsponsemessageid\":null,\"image_url\":\"http://34.66.81.100:8080/mtm/resources/images/vehiclegeneraldocument/7?rand=0.4133275245096223\",\"icon_image_url\":null,\"userid\":\"8\",\"usertype\":\"OWNER\",\"notification_type\":\"NOTIFICATION_CHALLAN_UPLOAD\",\"file_url\":null}}}";

                /*dataNotificationStr = "{\n" +
                        "\"message\": {\n" +
                        "\"token\": \"eTGOrm8BRbGEV1IilOL1CG:APA91bE9wCxt11LjbzVdXFN-Nd0VegMmK4hxdSTjA8Kv6nyNS7WLbHlOOMZ6jZhR7G7IWigpft0bQbSWwgg_mbl3Sd6yLaliUTylaVIgayPiKpl0GPAb1k1OEbjyiLOYqoNJzinvTJK7\",\n" +
                        "\"data\": {\n" +
                        "\"user_device_id\": \"eTGOrm8BRbGEV1IilOL1CG:APA91bE9wCxt11LjbzVdXFN-Nd0VegMmK4hxdSTjA8Kv6nyNS7WLbHlOOMZ6jZhR7G7IWigpft0bQbSWwgg_mbl3Sd6yLaliUTylaVIgayPiKpl0GPAb1k1OEbjyiLOYqoNJzinvTJK7" +
                        "\"messagetitle\": \"A test message1\",\n" +
                        "\"messagetext\": \"This is a message sent by mtm web server 1\",\n" +
                        "\"messageread\": null,\n" +
                        "\"messageid\": 1,\n" +
                        "\"notificationtime\": 1589622294959,\n" +

                        "\"notificationread\": null,\n" +

                        "\"usertype\": \"OWNER\",\n" +
                        "\"image_url\": null,\n" +
                        "\"icon_image_url\": null,\n" +
                        "\"primaryKeyColumn\": null\n" +
                        "}\n" +
                        "}\n" +
                        "}";

                dataNotificationStr = "{\n" +
                        "  \"message\":{\n" +
                        "    \"token\":\"eTGOrm8BRbGEV1IilOL1CG:APA91bE9wCxt11LjbzVdXFN-Nd0VegMmK4hxdSTjA8Kv6nyNS7WLbHlOOMZ6jZhR7G7IWigpft0bQbSWwgg_mbl3Sd6yLaliUTylaVIgayPiKpl0GPAb1k1OEbjyiLOYqoNJzinvTJK7\",\n" +
                        "    \"data\":{\n" +
                        "      \"user_device_id\" : \"eTGOrm8BRbGEV1IilOL1CG:APA91bE9wCxt11LjbzVdXFN-Nd0VegMmK4hxdSTjA8Kv6nyNS7WLbHlOOMZ6jZhR7G7IWigpft0bQbSWwgg_mbl3Sd6yLaliUTylaVIgayPiKpl0GPAb1k1OEbjyiLOYqoNJzinvTJK7\",\n" +
                        "\"messagetitle\": \"A test message1\",\n" +
                        "\"messagetext\": \"This is a message sent by mtm web server 1\",\n" +
                        "\"messageread\": null,\n" +
                        "\"messageid\": \"1\",\n" +

                        "    }\n" +
                        "  }\n" +
                        "}"; */
                httpPost.setEntity(new StringEntity(dataNotificationStr,"UTF-8"));


            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                @Override
                public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {

                    HttpEntity entity = null;
                        int status = response.getStatusLine().getStatusCode();
                        if (status >= 200 && status < 300) {
                             entity = response.getEntity();
                            return entity != null ? EntityUtils.toString(entity) : null;
                        } else {
                            System.out.println("response is "+response);
                            throw new ClientProtocolException("Unexpected response status: " + status);
                        }
                }



            };
            String responseBody = null;
            try {
                responseBody = httpclient.execute(httpPost, responseHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static NotificationDetail getNotificationDetail(Notification notification)
    {
        NotificationDetail notificationDetail = new NotificationDetail();
        notificationDetail.setUser_device_id(notification.getUser_device_id());
        notificationDetail.setMessagetitle(notification.getMessagetitle());
        notificationDetail.setMessagetext(notification.getMessagetext());
        notificationDetail.setUserid(notification.getUserid()+"");
        notificationDetail.setUsertype(notification.getUsertype());
        notificationDetail.setNotificationtime(dateTimeFormat.format(notification.getNotificationtime()));
        notificationDetail.setMessageid(notification.getMessageid()+"");
        notificationDetail.setMessageread(notification.getMessageread());
        notificationDetail.setImage_url(notification.getImage_url());
        notificationDetail.setIcon_image_url(notification.getIcon_image_url());
        notificationDetail.setNotification_type(notification.getNotification_type());
        notificationDetail.setFile_url(notification.getFile_url());
        return notificationDetail;
    }



    public static void main (String[] args) {

        String deviceToken = "ciuRICo7TOqmIq-NqCOzV9:APA91bEXGfacE7vleOibLuAekWiNyi31bcpZXqjq_SoHqOj5niURISAuv4Aqa8Un3fM8iHRuVqKcYASw_r20Zq1Kbcmr0w0IOaatvo9igmvWMA_U4MXhIs7FB55Ithzo30ue42_gt-53";

        for (int i = 4; i < 500; i++) {
            Notification notification = new Notification();
            notification.setUser_device_id(deviceToken);
            notification.setMessagetitle("A test message" + i);
            notification.setMessagetext("This is a message sent by mtm web server " + i);
            notification.setUserid(8);
            notification.setUsertype("OWNER");
            notification.setNotificationtime(new Date());
            notification.setMessageid(1);
            notification.setImage_url("http://34.66.81.100:8080/mtm/resources/images/vehiclegeneraldocument/2?rand=0.3692373930824564");
            notification.setFile_url("http://34.66.81.100:8080/mtm/resources/images/vehiclegeneraldocument/2?rand=0.3692373930824564");
            notification.setNotification_type("DRIVER_LOCATION_SEEK_REQUEST");

            DataNotification dataNotification = new DataNotification();
            dataNotification.setData(notification);
            dataNotification.setToken(deviceToken);
           // dataNotification.setMessageType("NOTIFICATION_CHALLAN_UPLOAD");
            com.mtm.beans.dto.Message message = new com.mtm.beans.dto.Message();
            message.setMessage(dataNotification);
            //message.s

            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            send(message);
        }



    }

}
