package sit.khaycake.util;

import java.util.regex.Pattern;

/**
 * Created by Falook Glico on 4/12/2015.
 */
public class Util {

    public static boolean isInteger(String str) {
        return str.matches("^[0-9]+$");
    }

    public static boolean isFloat(String str) {
        return str.matches("^\\.[0-9]+$") || str.matches("^[0-9]+\\.[0-9]+$");
    }

    public static boolean isEmail(String str){
        Pattern p = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        return p.matcher(str).matches();
    }

}
