package sit.khaycake.model;

import sit.khaycake.database.Column;
import sit.khaycake.database.ORM;
import sit.khaycake.database.SQL;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Falook Glico on 5/6/2015.
 */
public class ShipmentAddress implements ORM {

    private int id;
    private String firstName;
    private String lastName;
    private String address;
    private Tumbon tumbon;
    private Customer customer;

    public static final String TABLE_NAME = "SHIPMENT_ADDRESS";
    public static final Column COLUMN_ID = ORM.column(TABLE_NAME, "SHAD_ID");
    public static final Column COLUMN_FNAME = ORM.column(TABLE_NAME, "FNAME");
    public static final Column COLUMN_LNAME = ORM.column(TABLE_NAME, "LNAME");
    public static final Column COLUMN_ADDRESS = ORM.column(TABLE_NAME, "ADDRESS");
    public static final Column COLUMN_TUMB_ID = ORM.column(TABLE_NAME, "TUMB_ID");
    public static final Column COLUMN_CUST_ID = ORM.column(TABLE_NAME, "CUST_ID");
    public static final List<Column> PRIMARY_KEY = ORM.columns(COLUMN_ID);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Tumbon getTumbon() {
        return tumbon;
    }

    public void setTumbon(Tumbon tumbon) {
        this.tumbon = tumbon;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public void orm(ResultSet rs) throws Exception {
        this.id = rs.getInt(COLUMN_ID.getColumnName());
        this.firstName = rs.getString(COLUMN_FNAME.getColumnName());
        this.lastName = rs.getString(COLUMN_LNAME.getColumnName());
        this.address = rs.getString(COLUMN_ADDRESS.getColumnName());
        this.tumbon = SQL.findById(Tumbon.class, rs.getInt(COLUMN_TUMB_ID.getColumnName()));
        this.customer = SQL.findById(Customer.class, rs.getInt(COLUMN_CUST_ID.getColumnName()));
    }

    public void save(boolean isNew) throws Exception {
        SQL sql = new SQL();
        if(isNew)
            this.id = sql.insert().into(TABLE_NAME, COLUMN_FNAME, COLUMN_LNAME, COLUMN_ADDRESS, COLUMN_TUMB_ID, COLUMN_CUST_ID)
                                .values(this.firstName, this.lastName, this.address, this.tumbon.getId(), this.customer.getId())
                                .exec();
        else
            sql.update(TABLE_NAME).set(COLUMN_FNAME, this.firstName)
                                    .set(COLUMN_LNAME, this.lastName)
                                    .set(COLUMN_ADDRESS, this.address)
                                    .set(COLUMN_TUMB_ID, this.tumbon.getId())
                                    .set(COLUMN_CUST_ID, this.customer.getId())
                                    .exec();
    }
}
