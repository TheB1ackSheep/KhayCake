package sit.khaycake.util;

/**
 * Created by Pasuth on 19/4/2558.
 */
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AssisDateTime {
    public static java.sql.Date Date(String choose) {
        try {
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(choose);
            return new java.sql.Date(date.getTime());
        } catch (ParseException ex) {
            return null;
        }
    }
    public static java.sql.Date DateTime(String choose) {
        try {
            Date date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(choose);
            return new java.sql.Date(date.getTime());
        } catch (ParseException ex) {
            return null;
        }
    }
}
