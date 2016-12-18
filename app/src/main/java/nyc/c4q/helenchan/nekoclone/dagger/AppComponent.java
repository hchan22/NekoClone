package nyc.c4q.helenchan.nekoclone.dagger;

import javax.inject.Singleton;

import dagger.Component;
import nyc.c4q.helenchan.nekoclone.ChangeNameDialogFragment;
import nyc.c4q.helenchan.nekoclone.MainActivity;
import nyc.c4q.helenchan.nekoclone.receiver.NotificationService;

/**
 * Created by andresarango on 12/11/16.
 */

@Singleton
@Component(modules = {DatabaseModule.class, NetworkModule.class})
public interface AppComponent {
    void inject(NotificationService notificationService);
    void inject(ChangeNameDialogFragment fragment);
    void inject(MainActivity activity);
}
