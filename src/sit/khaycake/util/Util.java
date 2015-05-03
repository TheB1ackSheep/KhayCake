package sit.khaycake.util;

/**
 * Created by Falook Glico on 4/12/2015.
 */
public class Util {

    public static boolean isInteger(String str){
        return str.matches("^[0-9]+$");
    }

    public static boolean isFloat(String str){
        return str.matches("^\\.[0-9]+$") || str.matches("^[0-9]+\\.[0-9]+$");
    }

}
