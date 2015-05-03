package sit.khaycake;


import com.google.gson.Gson;
import sit.khaycake.database.ORM;
import sit.khaycake.database.SQL;
import sit.khaycake.database.SQL.*;
import sit.khaycake.database.exception.ColumnValueMismatchException;
import sit.khaycake.database.exception.InsertMultiTableException;
import sit.khaycake.model.*;
import sit.khaycake.util.Util;

import java.util.List;


/**
 * Created by Falook Glico on 4/11/2015.
 */
public class BankTesting {
    public static void main(String[] args) throws Exception {

        Product product = (Product)SQL.findById(Product.class, 10);
        System.out.println(product.getPictures());


    }
}
