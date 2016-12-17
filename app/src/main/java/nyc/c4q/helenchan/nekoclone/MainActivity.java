package nyc.c4q.helenchan.nekoclone;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import javax.inject.Inject;

import nyc.c4q.helenchan.nekoclone.model.Chinchilla;
import nyc.c4q.helenchan.nekoclone.model.DatabaseHelper;
import nyc.c4q.helenchan.nekoclone.model.networkmodels.GiphyResult;
import nyc.c4q.helenchan.nekoclone.model.networkmodels.RandomUserResult;
import nyc.c4q.helenchan.nekoclone.network.GiphyAPI;
import nyc.c4q.helenchan.nekoclone.network.RandomUserAPI;
import nyc.c4q.helenchan.nekoclone.receiver.AlarmReceiver;
import nyc.c4q.helenchan.nekoclone.receiver.NotificationService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class MainActivity extends AppCompatActivity {

    @Inject
    GiphyAPI mGiphyAPI;
    @Inject
    RandomUserAPI mRandomUserAPI;
    @Inject
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    RecyclerView recyclerView;
    ChinchillaAdapter adapter = new ChinchillaAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((MyApplication) getApplication()).getComponent().inject(this);
        db = dbHelper.getWritableDatabase();
        launchTest();
        alarmNotif();
        Chinchilla chinchilla = new Chinchilla();
        setImage(chinchilla);
        setName(chinchilla);
        addChinchilla(chinchilla);

        recyclerView = (RecyclerView)findViewById(R.id.main_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);
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



    public void launchTest() {
        Intent i = new Intent(this, NotificationService.class);
        startService(i);
    }

    public void alarmNotif() {
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        final PendingIntent pendingIntent = PendingIntent
                .getBroadcast(this, AlarmReceiver.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        long firstMillis = System.currentTimeMillis();
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis, AlarmManager.INTERVAL_FIFTEEN_MINUTES/15, pendingIntent);
    }
    
}
