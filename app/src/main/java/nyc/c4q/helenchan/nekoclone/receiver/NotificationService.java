package nyc.c4q.helenchan.nekoclone.receiver;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.NotificationCompat;

import javax.inject.Inject;

import nyc.c4q.helenchan.nekoclone.MainActivity;
import nyc.c4q.helenchan.nekoclone.MyApplication;
import nyc.c4q.helenchan.nekoclone.R;
import nyc.c4q.helenchan.nekoclone.model.Chinchilla;
import nyc.c4q.helenchan.nekoclone.model.DatabaseHelper;
import nyc.c4q.helenchan.nekoclone.model.networkmodels.GiphyResult;
import nyc.c4q.helenchan.nekoclone.model.networkmodels.RandomUserResult;
import nyc.c4q.helenchan.nekoclone.network.GiphyAPI;
import nyc.c4q.helenchan.nekoclone.network.RandomUserAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by helenchan on 12/15/16.
 */

public class NotificationService extends IntentService {
    private static final String SERVICE_NAME = "notification service";
    public NotificationService() {
        super(SERVICE_NAME);
    }
    @Inject
    public DatabaseHelper dbHelper;

    public SQLiteDatabase db;

    @Inject
    public RandomUserAPI mRandomUserAPI;
    @Inject
    public GiphyAPI mGiphyAPI;



    @Override
    protected void onHandleIntent(Intent intent) {
        int NOTIFICATION_ID = 1;
        int requestID = (int) System.currentTimeMillis();
        ((MyApplication) getApplication()).getComponent().inject(this);
        db = dbHelper.getWritableDatabase();
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
        putRandomChinchilla(new Chinchilla());
    }

    private void putRandomChinchilla(Chinchilla chinchilla) {
        setName(chinchilla);
        setImage(chinchilla);
    }


    private void setName(final Chinchilla chinchilla) {
        mRandomUserAPI.getRandomUserResult().enqueue(new Callback<RandomUserResult>() {
            @Override
            public void onResponse(Call<RandomUserResult> call, Response<RandomUserResult> response) {
                String name = response.body().getResults().get(0).getName().getFirst();
                chinchilla.setName(name);
                addChinchilla(chinchilla);
            }

            @Override
            public void onFailure(Call<RandomUserResult> call, Throwable t) {

            }
        });
    }

    private void setImage(final Chinchilla chinchilla) {
        mGiphyAPI.getPic("chinchilla").enqueue(new Callback<GiphyResult>() {
            @Override
            public void onResponse(Call<GiphyResult> call, Response<GiphyResult> response) {
                String image = response.body().getData().getImageOriginalURL();
                chinchilla.setImage_url(image);
                addChinchilla(chinchilla);
            }

            @Override
            public void onFailure(Call<GiphyResult> call, Throwable t) {

            }
        });

    }

    public void addChinchilla(Chinchilla chinchilla) {
        cupboard().withDatabase(db).put(chinchilla);
    }

}
