package nyc.c4q.helenchan.nekoclone.receiver;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import nyc.c4q.helenchan.nekoclone.MainActivity;
import nyc.c4q.helenchan.nekoclone.R;

/**
 * Created by helenchan on 12/15/16.
 */

public class NotificationService extends IntentService {
    private static final String SERVICE_NAME = "notification service";

    public NotificationService() {
        super(SERVICE_NAME);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int NOTIFICATION_ID = 1;
        int requestID = (int) System.currentTimeMillis();

        Intent intents = new Intent(this, MainActivity.class);

        int flags = PendingIntent.FLAG_CANCEL_CURRENT; // Cancel old intent and create new one

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), requestID, intents, flags);
        Notification notification = new NotificationCompat.Builder(getApplicationContext())
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle("Chinchilla Found!")
                .setContentText("You discovered a new fluffy chinchilla")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}
