package nyc.c4q.helenchan.nekoclone.dagger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import nyc.c4q.helenchan.nekoclone.network.GiphyAPI;
import nyc.c4q.helenchan.nekoclone.network.RandomUserAPI;

/**
 * Created by andresarango on 12/16/16.
 */

@Module
public class NetworkModule {

    @Singleton
    @Provides
    public RandomUserAPI providesRandomUserAPI(){ return new RandomUserAPI();}

    @Singleton
    @Provides
    public GiphyAPI providesGiphyAPI(){
        return new GiphyAPI();
    }
}
