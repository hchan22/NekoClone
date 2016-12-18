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

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.qbusict.cupboard.QueryResultIterable;
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

public class MainActivity extends AppCompatActivity implements ChinchillaAdapter.Listener {

    @Inject
    GiphyAPI mGiphyAPI;
    @Inject
    RandomUserAPI mRandomUserAPI;
    @Inject
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    RecyclerView recyclerView;
    ChinchillaAdapter adapter;
    Chinchilla chinchilla = new Chinchilla();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((MyApplication) getApplication()).getComponent().inject(this);
        db = dbHelper.getWritableDatabase();
        launchTestService();
        alarmNotif();
        recyclerView = (RecyclerView) findViewById(R.id.main_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        adapter = new ChinchillaAdapter(selectList(), this);
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


    private List<Chinchilla> selectList() {
        List<Chinchilla> chinchillas = new ArrayList<>();

        try {
            QueryResultIterable<Chinchilla> iterating = cupboard().withDatabase(db).query(Chinchilla.class).query();
            for (Chinchilla chin : iterating) {
                chinchillas.add(chin);
            }
            iterating.close();
        } catch (Exception e) {

        }

        return chinchillas;
    }

    private void refreshList() {
        adapter.setData(selectList());
    }

    public void launchTestService() {
        Intent i = new Intent(this, NotificationService.class);
        startService(i);
    }



    public void alarmNotif() {
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        final PendingIntent pendingIntent = PendingIntent
                .getBroadcast(this, AlarmReceiver.REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        long firstMillis = System.currentTimeMillis();
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis, AlarmManager.INTERVAL_FIFTEEN_MINUTES / 15, pendingIntent);

    }

    @Override
    public void onChinchillaClicked(Chinchilla chinClick) {
        long chinchillaID = chinClick.get_id();
        ChangeNameDialogFragment change = new ChangeNameDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(ChangeNameDialogFragment.CHINCHILLA_ID_KEY,chinchillaID);
        change.setArguments(bundle);
        change.show(getFragmentManager(), "name");
        refreshList();
    }

    @Override
    public void onChinchillaLongClicked(Chinchilla chinLongClick) {
        long chinchillaID = chinLongClick.get_id();
        cupboard().withDatabase(db).delete(Chinchilla.class, chinchillaID);
        refreshList();
    }


}
