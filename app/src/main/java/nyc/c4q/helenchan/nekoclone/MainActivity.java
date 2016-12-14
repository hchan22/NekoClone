package nyc.c4q.helenchan.nekoclone;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import javax.inject.Inject;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;
import nyc.c4q.helenchan.nekoclone.model.Chinchilla;
import nyc.c4q.helenchan.nekoclone.model.DatabaseHelper;

public class MainActivity extends AppCompatActivity {


    private TextView mHelloWorldTV;

    @Inject
    DatabaseHelper dbHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHelloWorldTV = (TextView) findViewById(R.id.hello_world);
        ((MyApplication) getApplication()).getComponent().inject(this);
        db = dbHelper.getWritableDatabase();
    }

    private void addChinchilla(Chinchilla chinchilla) {
        cupboard().withDatabase(db).put(chinchilla);
    }
    
}
