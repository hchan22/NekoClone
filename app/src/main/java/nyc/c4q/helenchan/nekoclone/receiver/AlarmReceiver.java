package nyc.c4q.helenchan.nekoclone.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

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
}
