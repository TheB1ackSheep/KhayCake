package sit.khaycake;


import sit.khaycake.database.SQL;
import sit.khaycake.database.SQL.*;
import sit.khaycake.database.exception.ColumnValueMismatchException;
import sit.khaycake.database.exception.InsertMultiTableException;
import sit.khaycake.model.Bank;
import sit.khaycake.model.District;


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
        String resource = "/category/1/delete";
        resource = resource.substring(resource.indexOf("/", 1)+1);
        System.out.println(resource);
        System.out.println(resource.substring(0,resource.indexOf("/", 1)));
        System.out.println(resource.substring(resource.indexOf("/", 0)+1));
        System.out.println(resource.indexOf("product"));

    }
}
