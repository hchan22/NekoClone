package nyc.c4q.helenchan.nekoclone.network;

import nyc.c4q.helenchan.nekoclone.model.networkmodels.GiphyResult;
import nyc.c4q.helenchan.nekoclone.model.networkmodels.RandomUserResult;
import retrofit2.Call;

/**
 * Created by andresarango on 12/16/16.
 */

public class GiphyAPI {
    private final String API_KEY = "dc6zaTOxFJmzC";
    private final String BASE_URL = "http://api.giphy.com/v1/gifs/";
    private final GiphyService mGiphyService;


    public GiphyAPI() {
        mGiphyService = (new NetworkServices()).getJSONService(BASE_URL, GiphyService.class);
    }

    public Call<GiphyResult> getPic(String input){
        return mGiphyService.getRandomGiphyResult(API_KEY,input);
    }


}
