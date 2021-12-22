package kz.ukteplo.uktsrepairs;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class UKTSRepairsService  extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getData().size() > 0) {
            handleNow(remoteMessage.getData().get("message"),
                    remoteMessage.getData().get("repairId"),
                    remoteMessage.getData().get("actionType"));
        }

        if (remoteMessage.getNotification() != null) {
        }

    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.e("TOKEN", token);
        sendRegistrationToServer(token);
    }

    private void handleNow(final String msg, final String id, String actionType) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("message", msg);
        intent.putExtra("repairId", id);
        intent.putExtra("actionType", actionType);

        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);
        //Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.logo_mobile_inspectors);
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.logo_mobile_repairs);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ukts_small_icon)
                .setContentTitle("УКТС Ремонты")
                .setLargeIcon(largeIcon)
                .setColor(Color.parseColor("#001372"))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setContentText(msg)
                .setAutoCancel(true)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notice))
                .setLights(Color.MAGENTA, 500, 1000)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify((int) (Math.random() * 101), notificationBuilder.build());
    }

    private void sendRegistrationToServer(String token) {
        Log.e("aaa", "Refreshed token: " + token);
    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.app_name);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.mipmap.ukts_small_icon)
                        .setContentTitle("")
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify((int) (Math.random() * 101), notificationBuilder.build());
    }
}
