package com.edentomer.med_track_final.SendNotificationPack;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAt0V9Afw:APA91bFZex6Dmiy4dQHVqHdvp1MYbPM_cT5Wq6_jDHNa9VneCWBctGZrSs0zIl5Kg4I8c_eP3XYEoHGrvb2YPPffZ5OGaMJbVW7_JcpbvOz6Ked6VCNKA44v9T12yr_W32lBK3TR_yyV" // Your server key refer to video for finding your server key
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotifcation(@Body NotificationSender body);
}

