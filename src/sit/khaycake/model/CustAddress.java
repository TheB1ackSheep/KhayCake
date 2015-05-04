package sit.khaycake.model;

import sit.khaycake.database.CanFindByKeyword;
import sit.khaycake.database.Column;
import sit.khaycake.database.ORM;
import sit.khaycake.database.SQL;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pasuth on 22/4/2558.
 */
public class CustAddress implements ORM, CanFindByKeyword {
    private int custId;
    private Address address;


    public static final String TABLE_NAME = "CUST_ADDRESS";
    public static final Column COLUMN_CUST_ID = ORM.column(TABLE_NAME, "CUST_ID");
    public static final Column COLUMN_ADDR_ID = ORM.column(TABLE_NAME, "ADDR_ID");
    public static final List<Column> COLUMN_KEYWORD = ORM.columns(COLUMN_CUST_ID);

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


    public void orm(ResultSet rs) throws Exception {
        this.setCustId(rs.getInt(COLUMN_CUST_ID.getColumnName()));
        this.setAddress((Address) SQL.findById(Address.class, rs.getInt(COLUMN_ADDR_ID.getColumnName())));
    }

    public static List<Address> getAddresses(List<CustAddress> custAddresses) {
        List<Address> addresses = new ArrayList<>();
        for (CustAddress ca : custAddresses) {
            addresses.add(ca.getAddress());
        }
        return null;
    }

    public void save() throws Exception {
        SQL sql = new SQL();
        sql
                .insert()
                .into(CustAddress.TABLE_NAME, CustAddress.COLUMN_CUST_ID, CustAddress.COLUMN_ADDR_ID)
                .values(this.getCustId(), this.getAddress().getId())
                .exec();
    }


    public static int delete(int CUST_ID, int ADDR_ID) throws Exception {
        SQL sql = new SQL();
        int a = sql
                .delete(CustAddress.TABLE_NAME)
                .where(CustAddress.COLUMN_CUST_ID, SQL.WhereClause.Operator.EQ, CUST_ID)
                .where(CustAddress.COLUMN_ADDR_ID, SQL.WhereClause.Operator.EQ, ADDR_ID)
                .exec();
        return a;
    }

    public static int delete(int CUST_ID) throws Exception {
        SQL sql = new SQL();
        int a = sql
                .delete(CustAddress.TABLE_NAME)
                .where(CustAddress.COLUMN_CUST_ID, SQL.WhereClause.Operator.EQ, CUST_ID)
                .exec();
        return a;
    }

}
