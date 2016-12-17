package nyc.c4q.helenchan.nekoclone.dagger;

import javax.inject.Singleton;

import dagger.Component;
import nyc.c4q.helenchan.nekoclone.MainActivity;

/**
 * Created by andresarango on 12/11/16.
 */

@Singleton
@Component(modules = {DatabaseModule.class, NetworkModule.class})
public interface AppComponent {

    void inject(MainActivity activity);
}
