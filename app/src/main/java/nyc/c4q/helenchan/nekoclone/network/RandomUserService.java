package nyc.c4q.helenchan.nekoclone.network;

import nyc.c4q.helenchan.nekoclone.model.networkmodels.RandomUserResult;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by andresarango on 12/16/16.
 */
public interface RandomUserService {
    @GET("api/")
    Call<RandomUserResult> getRandomUsers();
}
