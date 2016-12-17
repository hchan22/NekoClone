package nyc.c4q.helenchan.nekoclone;

import android.app.Application;

import com.facebook.stetho.Stetho;

import nyc.c4q.helenchan.nekoclone.dagger.AppComponent;
import nyc.c4q.helenchan.nekoclone.dagger.DaggerAppComponent;
import nyc.c4q.helenchan.nekoclone.dagger.DatabaseModule;

/**
 * Created by andresarango on 12/11/16.
 */

public class MyApplication extends Application {

    AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        component = DaggerAppComponent.builder()
                .databaseModule(new DatabaseModule(this))
                .build();
    }

    public AppComponent getComponent() {
        return component;
    }


}
