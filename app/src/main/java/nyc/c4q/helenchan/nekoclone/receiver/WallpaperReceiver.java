package nyc.c4q.helenchan.nekoclone.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static android.support.v4.content.WakefulBroadcastReceiver.startWakefulService;

/**
 * Created by helenchan on 12/16/16.
 */
public class WallpaperReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent wallpaperIntent = new Intent(context, NotificationService.class);
        startWakefulService(context, wallpaperIntent);
    }
}
