package nyc.c4q.helenchan.nekoclone.network;

import com.google.gson.annotations.SerializedName;

import nyc.c4q.helenchan.nekoclone.model.networkmodels.GiphyResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by andresarango on 12/16/16.
 */

public interface GiphyService {
    @GET("random")
    Call<GiphyResult> getRandomGiphyResult(
            @Query("api_key") String api_key,
            @Query("tag") String tag);
}
