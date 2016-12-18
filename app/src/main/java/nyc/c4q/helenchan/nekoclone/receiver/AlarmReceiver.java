package nyc.c4q.helenchan.nekoclone.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import nyc.c4q.helenchan.nekoclone.MainActivity;
import nyc.c4q.helenchan.nekoclone.R;
import nyc.c4q.helenchan.nekoclone.model.Chinchilla;

/**
 * Created by helenchan on 12/16/16.
 */
public class AlarmReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 123;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, NotificationService.class);
        context.startService(i);
    }

    public void sendNotification(Chinchilla chinchilla, Context context){
        int NOTIFICATION_ID = 1;
        int requestID = (int) System.currentTimeMillis();

        Intent intents = new Intent(context, MainActivity.class);
        intents.putExtra("name", chinchilla.getName());
        int flags = PendingIntent.FLAG_CANCEL_CURRENT; // Cancel old intent and create new one

        PendingIntent pendingIntent = PendingIntent.getActivity(context, requestID, intents, flags);
        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle("Chinchilla Found!")
                .setContentText("You discovered a new fluffy chinchilla")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}
