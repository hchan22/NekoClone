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
import android.widget.TextView;

import javax.inject.Inject;

import nyc.c4q.helenchan.nekoclone.model.Chinchilla;
import nyc.c4q.helenchan.nekoclone.model.DatabaseHelper;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class MainActivity extends AppCompatActivity {


    private TextView mHelloWorldTV;

    @Inject
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    RecyclerView recyclerView;
    ChinchillaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHelloWorldTV = (TextView) findViewById(R.id.hello_world);
        ((MyApplication) getApplication()).getComponent().inject(this);
        db = dbHelper.getWritableDatabase();
        launchTest();
        alarmNotif();
        addChinchilla(new Chinchilla());

        recyclerView = (RecyclerView)findViewById(R.id.main_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(new ChinchillaAdapter());
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
