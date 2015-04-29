package sit.khaycake.model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Falook Glico on 4/26/2015.
 */
public class Model {





    @Override
    public String toString() {
        Gson gson = new Gson();
        JsonObject result = new JsonObject();
        JsonElement jsonElement = gson.toJsonTree(this);
        result.add(this.getClass().getSimpleName(), jsonElement);
        return result.toString();
    }
}
