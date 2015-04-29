package sit.khaycake.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import sit.khaycake.database.ORM;
import sit.khaycake.database.SQL;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Falook Glico on 4/12/2015.
 */
public class Util {

    public static boolean isInteger(String str){
        return str.matches("^[0-9]+$");
    }

    public static boolean isFloat(String str){
        return Util.isInteger(str) || str.matches("^\\.[0-9]+$") || str.matches("^[0-9]+\\.[0-9]+$");
    }


    public static String print(Object print,HttpServletRequest request){
        Gson gson = new Gson();
        JsonObject result = new JsonObject();
        JsonElement jsonElement = gson.toJsonTree(print);
        result.addProperty("JSESSIONID", request.getSession().getId());
        result.add(print.getClass().getSimpleName(), jsonElement);

        return result.toString();
    }
    public static String print(List print,HttpServletRequest request){
        Gson gson = new Gson();
        JsonObject result = new JsonObject();
        JsonElement jsonElement = gson.toJsonTree(print);
        result.addProperty("JSESSIONID", request.getSession().getId());
        result.add(print.get(0).getClass().getSimpleName()+"List", jsonElement);

        return result.toString();
    }

}
