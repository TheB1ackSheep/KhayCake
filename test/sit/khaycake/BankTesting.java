package sit.khaycake;


import com.google.gson.Gson;
import sit.khaycake.database.ORM;
import sit.khaycake.database.SQL;
import sit.khaycake.database.SQL.*;
import sit.khaycake.database.exception.ColumnValueMismatchException;
import sit.khaycake.database.exception.InsertMultiTableException;
import sit.khaycake.model.*;

import java.util.List;


/**
 * Created by Falook Glico on 4/11/2015.
 */
public class BankTesting {
    public static void main(String[] args) throws Exception {
        /*
        SQL select = new SQL();

        //find by bank id
        System.out.println(Bank.findById(Bank.class,2));


        //query with custom sql
        System.out.println(select.from(Bank.TABLE_NAME)
                .order(Bank.COLUMN_NAME_EN, SQL.OrderClause.Operator.DESC)
                .fetch(Bank.class));

        query with your own sql
        System.out.println((new SQL("SELECT * FROM BANKS")).fetch(Bank.class));

        /*Bank ponyBank = new Bank();
        ponyBank.setName("PONY BANK PUBLIC CO.LTD2");
        ponyBank.save();
        System.out.println(ponyBank);*/

        /*SQL sql = new SQL();
        System.out.println(sql
                .select()
                .from(Bank.TABLE_NAME)
                .where(Bank.COLUMN_NAME_EN, WhereClause.Operator.LIKE, "%CI%")
                .order(Bank.COLUMN_NAME_EN, OrderClause.Operator.DESC)
                .chunk(5));

        sql.clear();

        System.out.println(sql
                .insert()
                .into(Bank.TABLE_NAME, Bank.COLUMN_NAME_EN, Bank.COLUMN_NAME_TH)
                .values("PONY BANK PUBLIC CO.LTD", "ธ. โพนี จำกัด (มหาชน)"));

        sql.clear();

        System.out.println(sql
                .update(Bank.TABLE_NAME)
                .set(Bank.COLUMN_NAME_EN, "PONY")
                .where(Bank.COLUMN_ID, WhereClause.Operator.EQ, 84));

        sql.clear();

        System.out.println(sql
                .delete(Bank.TABLE_NAME)
                .where(Bank.COLUMN_ID, WhereClause.Operator.EQ, 84));

        System.out.println(SQL.findByKeyword(Bank.class, "JPMO"));

        System.out.println(SQL.findByKeyword(Bank.class, "JPMO","PONY"));

        System.out.println(SQL.findByKeyword(Bank.class, "JPMO","PONY","กสิกร"));

        District dis = (District)SQL.findById(District.class,1);

        System.out.println(dis.getProvince().getName());*/
        /*String resource = "/category/1/delete";
        resource = resource.substring(resource.indexOf("/", 1)+1);
        System.out.println(resource);
        System.out.println(resource.substring(0,resource.indexOf("/", 1)));
        System.out.println(resource.substring(resource.indexOf("/", 0)+1));
        System.out.println(resource.indexOf("product"));*/
        //List add = SQL.findAll(Product.class);
        //System.out.println(add);

        /*SQL sql = new SQL();
        List<ORM> result = sql
                .select()
                .from(PicProduct.TABLE_NAME)
                .where(PicProduct.COLUMN_PROD_ID, SQL.WhereClause.Operator.EQ, 1)
                .fetch(PicProduct.class);*/


        //System.out.println(PicProduct.findByProdId(11));
        //System.out.println(PicProduct.findByProdId(12));
        //System.out.println(PicProduct.findByProdId(13));
        Gson gson = new Gson();
        /*System.out.println(((Picture)SQL.findById(Picture.class,1)));
        System.out.println(gson.toJson(ProductSale.findByProdId(10)));
        System.out.println(PicProduct.findByProdId(10));
        System.out.println(PicProduct.getPictures(PicProduct.findByProdId(10)));*/
        System.out.println(gson.toJson(SQL.findAll(Product.class)));

        System.out.println("Yo");
    }
}
