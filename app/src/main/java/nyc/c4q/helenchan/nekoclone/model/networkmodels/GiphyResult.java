package nyc.c4q.helenchan.nekoclone.model.networkmodels;

import com.google.gson.annotations.SerializedName;

/**
 * Created by andresarango on 12/16/16.
 */

public class GiphyResult {
    public Data getData() {
        return data;
    }

    Data data;
    public class Data{
        public String getImageOriginalURL() {
            return imageOriginalURL;
        }

        @SerializedName("image_original_url")
        String imageOriginalURL;
    }
}
