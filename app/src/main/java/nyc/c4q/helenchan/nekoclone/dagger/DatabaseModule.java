package nyc.c4q.helenchan.nekoclone.dagger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import nyc.c4q.helenchan.nekoclone.model.DatabaseHelper;

/**
 * Created by andresarango on 12/11/16.
 */


@Module
public class DatabaseModule {

    Context mContext;

    public DatabaseModule(Context context) {
        mContext = context;
    }

    @Singleton
    @Provides
    public DatabaseHelper providesDatabaseHelper(){
        return new DatabaseHelper(mContext);
    }


}
