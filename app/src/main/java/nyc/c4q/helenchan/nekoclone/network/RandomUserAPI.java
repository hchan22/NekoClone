package nyc.c4q.helenchan.nekoclone.network;

import nyc.c4q.helenchan.nekoclone.model.networkmodels.RandomUserResult;
import retrofit2.Call;

/**
 * Created by andresarango on 12/16/16.
 */

public class RandomUserAPI {
    private final String BASE_URL = "https://randomuser.me/";
    private final RandomUserService mRandomUserService;

    public RandomUserAPI() {
        mRandomUserService = (new NetworkServices()).getJSONService(BASE_URL,RandomUserService.class);
    }

    public Call<RandomUserResult> getRandomUserResult(){
        return mRandomUserService.getRandomUsers();
    }
}
