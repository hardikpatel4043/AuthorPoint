package com.example.hardik.myapplication.POJO;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.hardik.myapplication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Hardik on 12/31/2017.
 */

public class FirebaseNotificationService extends FirebaseMessagingService {

    FirebaseUser currrentUser;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        currrentUser= FirebaseAuth.getInstance().getCurrentUser();
        String current_user_id=currrentUser.getUid().toString();

        String notificationTitle=remoteMessage.getNotification().getTitle();
        String body=remoteMessage.getNotification().getBody();
        String click=remoteMessage.getNotification().getClickAction();

        String targetUser=remoteMessage.getData().get("sendTOAuthorId").toString();

        int mNotificationId = (int)System.currentTimeMillis();

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.logo_main)
                        .setContentTitle(notificationTitle + "foreground")
                        .setContentText(body);

//        Notification noti = new Notification.Builder(this)
//                .setSmallIcon(R.drawable.logo_main)
//                .setContentTitle("Friend request")
//                .setContentText("You've recieved a new friend request")
//                .build();

        Intent resultIntent = new Intent(click);

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder.setContentIntent(resultPendingIntent);

        if(current_user_id.equals(targetUser)){

            NotificationManager mNotifyMgr =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            mNotifyMgr.notify(mNotificationId, mBuilder.build());
        }

    }
}
