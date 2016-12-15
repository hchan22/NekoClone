package nyc.c4q.helenchan.nekoclone.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Singleton;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by andresarango on 12/11/16.
 */

@Singleton
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "animal.db";
    private static final int DATABASE_VERSION = 2;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    static {
        cupboard().register(Chinchilla.class);
    }

    public SQLiteDatabase getDatabase(){
        return this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        cupboard().withDatabase(sqLiteDatabase).createTables();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        cupboard().withDatabase(sqLiteDatabase).upgradeTables();
    }
}
