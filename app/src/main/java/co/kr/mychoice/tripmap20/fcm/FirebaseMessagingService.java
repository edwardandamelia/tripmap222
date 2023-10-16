package co.kr.mychoice.tripmap20.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import co.kr.mychoice.tripmap20.MainActivity;
import co.kr.mychoice.tripmap20.R;


public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService{

    private static final String TAG = "FirebaseMsgService";

    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {


        Log.d("2222","data: "+remoteMessage.getData());
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message From " + remoteMessage.getFrom()); //sender ID
            Log.d(TAG, "Notification Title " + remoteMessage.getNotification().getTitle()); //notification title
            Log.d(TAG, "Notification Body " + remoteMessage.getNotification().getBody()); //notification body
            sendPushNotification(remoteMessage);
        }


        sendPushNotification(remoteMessage);

    }

    private void sendPushNotification(RemoteMessage remoteMessage) {
        //String title = remoteMessage.getNotification().getTitle();
        // String message = remoteMessage.getNotification().getBody();
        //String send_url = remoteMessage.getNotification().getClickAction();

        Map<String, String> data = remoteMessage.getData();
        String title = data.get("title");
        String message = data.get("body");
        String cte = data.get("cte");
        String userid = data.get("userid");
        String userid2 = data.get("userid2");
        String user_str = data.get("user_str");
        String send_url = data.get("send_url");
        //Data Messages trigger the onMessageReceived() callback even if your app is in foreground/background


        Log.d("222","get data : " + title);

        Log.d("222","get data : " + message);

        Log.d("222","get data : " + send_url);



        String channel = "222222222222222";
        String channel_nm = "2222222222222222222222222";
        /**
         * 오레오 버전부터는 Notification Channel이 없으면 푸시가 생성되지 않는 현상이 있습니다.
         * **/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            Intent configureIntent = new Intent(getApplicationContext(), MainActivity.class);

            configureIntent.putExtra("url", send_url);
            configureIntent.putExtra("userid", userid);
            configureIntent.putExtra("userid2", userid2);
            configureIntent.putExtra("user_str", user_str);
            configureIntent.putExtra("cte", cte);

            PendingIntent pendingClearScreenIntent = PendingIntent.getActivity(getApplicationContext(), 0, configureIntent, PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);




            NotificationManager notichannel = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channelMessage = new NotificationChannel(channel, channel_nm,
                    NotificationManager.IMPORTANCE_HIGH);
            channelMessage.setDescription("channel설명");
            channelMessage.enableLights(true);
            channelMessage.enableVibration(true);
            channelMessage.setShowBadge(false);
            notichannel.createNotificationChannel(channelMessage);

            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(getApplicationContext(), channel)
                            .setSmallIcon(R.drawable.user_icon)
                            .setContentTitle(title)
                            .setContentText(message)
                            .setChannelId(channel)
                            .setAutoCancel(true)
                            .setContentIntent(pendingClearScreenIntent)
                            .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(222, notificationBuilder.build());
            //아이디는 삭제할때 사용된다.



        } else {

            Intent configureIntent = new Intent(getApplicationContext(), MainActivity.class);
            configureIntent.putExtra("url", send_url);
            configureIntent.putExtra("userid", userid);
            configureIntent.putExtra("userid2", userid2);
            configureIntent.putExtra("user_str", user_str);
            configureIntent.putExtra("cte", cte);

            PendingIntent pendingClearScreenIntent = PendingIntent.getActivity(getApplicationContext(), 0, configureIntent, PendingIntent.FLAG_UPDATE_CURRENT);


            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(getApplicationContext(), "bbb")
                            .setSmallIcon(R.drawable.user_icon)
                            .setContentTitle(title)
                            .setContentText(message)
                            .setAutoCancel(true)
                            .setContentIntent(pendingClearScreenIntent)
                            .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(222, notificationBuilder.build());

        }

    }


}
