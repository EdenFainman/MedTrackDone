package com.edentomer.med_track_final.SendNotificationPack;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.edentomer.med_track_final.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFireBaseMessagingService extends FirebaseMessagingService {
    String title,message;
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
            super.onMessageReceived(remoteMessage);
        Log.e("poooqw", "onMessageReceived: "+ remoteMessage.getData().get("Title"));
            title=remoteMessage.getData().get("Title");
            message=remoteMessage.getData().get("Message");

//        NotificationCompat.Builder builder =
//                new NotificationCompat.Builder(getApplicationContext())
//                        .setSmallIcon(R.drawable.ic_android_black_24dp)
//                        .setContentTitle(title)
//                        .setContentText(message);
//        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//        manager.notify(0, builder.build());



        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "notifyLemubit")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(message)
                //.setSound(Emergency_sound_uri, AudioManager.STREAM_NOTIFICATION)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(200,builder.build());
    }

}
