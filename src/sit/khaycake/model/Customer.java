/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.khaycake.model;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.List;

import sit.khaycake.database.*;

/**
 * @author -milk
 */
public class Customer implements ORM, CanFindByKeyword {
    private int id;
    private List<Address> addresses;
    private String fname;
    private String lname;
    private String email;
    private String phone;
    private String sex;
    private Date birthday;
    private String pwd;
    private String vatId;

    public static final String TABLE_NAME = "CUSTOMER";
    public static final Column COLUMN_ID = ORM.column(TABLE_NAME, "CUST_ID");
    //public static final Column COLUMN_ADDR_ID = ORM.column(TABLE_NAME, "ADDR_ID");
    public static final Column COLUMN_FNAME = ORM.column(TABLE_NAME, "FNAME");
    public static final Column COLUMN_LNAME = ORM.column(TABLE_NAME, "LNAME");
    public static final Column COLUMN_EMAIL = ORM.column(TABLE_NAME, "EMAIL");
    public static final Column COLUMN_PHONE = ORM.column(TABLE_NAME, "PHONE");
    public static final Column COLUMN_SEX = ORM.column(TABLE_NAME, "SEX");
    public static final Column COLUMN_BOD = ORM.column(TABLE_NAME, "BOD");
    public static final Column COLUMN_PWD = ORM.column(TABLE_NAME, "PWD");
    public static final Column COLUMN_VAT_ID = ORM.column(TABLE_NAME, "VAT_ID");
    public static final List<Column> PRIMARY_KEY = ORM.columns(COLUMN_ID);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddress(List<Address> addresses) {
        this.addresses = addresses;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getVatId() {
        return vatId;
    }

    public void setVatId(String vatId) {
        this.vatId = vatId;
    }

    public void orm(ResultSet rs) throws Exception {
        this.setId(rs.getInt(COLUMN_ID.getColumnName()));
        this.setAddress(CustAddress.getAddresses(
                (List<CustAddress>) SQL.findByKeyword(CustAddress.class,
                        rs.getString(COLUMN_ID.getColumnName()))));
        this.setVatId(rs.getString(COLUMN_VAT_ID.getColumnName()));
        this.setFname(rs.getString(COLUMN_FNAME.getColumnName()));
        this.setLname(rs.getString(COLUMN_LNAME.getColumnName()));
        this.setEmail(rs.getString(COLUMN_EMAIL.getColumnName()));
        this.setPhone(rs.getString(COLUMN_PHONE.getColumnName()));
        this.setSex(rs.getString(COLUMN_SEX.getColumnName()));
        this.setBirthday(rs.getDate(COLUMN_BOD.getColumnName()));
        this.setPwd(rs.getString(COLUMN_PWD.getColumnName()));
    }

    public void save() throws Exception{
        SQL sql = new SQL();
        int id = sql
                .insert()
                .into(Customer.TABLE_NAME, Customer.COLUMN_FNAME, Customer.COLUMN_LNAME, Customer.COLUMN_SEX,
                        Customer.COLUMN_BOD, Customer.COLUMN_PHONE, Customer.COLUMN_EMAIL, Customer.COLUMN_VAT_ID,
                        Customer.COLUMN_PWD)
                .values(this.getFname(), this.getLname(), this.getSex(), this.getBirthday(), this.getPhone(),
                        this.getEmail(), this.getVatId(), this.getPwd())
                .exec();
        this.setId(id);
    }

    public void update() throws Exception {
        SQL sql = new SQL();
        sql
                .update(Customer.TABLE_NAME)
                .set(Customer.COLUMN_FNAME, this.getFname())
                .set(Customer.COLUMN_LNAME, this.getLname())
                .set(Customer.COLUMN_SEX, this.getSex())
                .set(Customer.COLUMN_BOD, this.getBirthday())
                .set(Customer.COLUMN_PHONE, this.getPhone())
                .set(Customer.COLUMN_EMAIL, this.getEmail())
                .set(Customer.COLUMN_VAT_ID, this.getVatId())
                .where(Customer.COLUMN_ID, SQL.WhereClause.Operator.EQ, this.getId())
                .exec();
    }


    public static int delete(int CUST_ID) throws Exception{
        SQL sql = new SQL();
            int a = sql
                    .delete(Customer.TABLE_NAME)
                    .where(Customer.COLUMN_ID, SQL.WhereClause.Operator.EQ, CUST_ID)
                    .exec();
        return a;
    }



}
