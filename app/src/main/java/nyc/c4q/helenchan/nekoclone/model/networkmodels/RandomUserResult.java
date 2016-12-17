package nyc.c4q.helenchan.nekoclone.model.networkmodels;

import java.util.List;

/**
 * Created by andresarango on 12/16/16.
 */

public class RandomUserResult {
    List<Result> results;
    public List<Result> getResults() {
        return results;
    }

    public static class Result {
        Name name;
        public Name getName() {
            return name;
        }


    }

    public static class Name {
        String first;
        public String getFirst() {
            return first;
        }


    }


}
