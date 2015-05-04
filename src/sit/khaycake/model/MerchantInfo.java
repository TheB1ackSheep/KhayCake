/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.khaycake.model;

import java.sql.ResultSet;
import java.util.List;

import sit.khaycake.database.Column;
import sit.khaycake.database.ORM;
import sit.khaycake.database.SQL;


/**
 *
 * @author -milk
 */
public class MerchantInfo implements ORM {
    
    private int id;
    private Address address;
    private String name;
    private String phone;
    private String fax;
    private String vatId;
    private double vatValue;
    
    public static final String TABLE_NAME = "MERCHANT_INFO";
    public static final Column COLUMN_ID = ORM.column(TABLE_NAME, "MEIN_ID");
    public static final Column COLUMN_ADDR_ID = ORM.column(TABLE_NAME, "ADDR_ID");
    public static final Column COLUMN_NAME = ORM.column(TABLE_NAME, "NAME");
    public static final Column COLUMN_PHONE = ORM.column(TABLE_NAME, "PHONE");
    public static final Column COLUMN_FAX = ORM.column(TABLE_NAME, "FAX");
    public static final Column COLUMN_VAT_ID = ORM.column(TABLE_NAME, "VAT_ID");
    public static final Column COLUMN_VAT_VALUE = ORM.column(TABLE_NAME, "VAT_VALUE");
    public static final List<Column> PRIMARY_KEY = ORM.columns(COLUMN_ID);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getVatId() {
        return vatId;
    }

    public void setVatId(String vatId) {
        this.vatId = vatId;
    }

    public double getVatValue() {
        return vatValue;
    }

    public void setVatValue(double vatValue) {
        this.vatValue = vatValue;
    }
    
    public void orm(ResultSet rs) throws Exception {
        
        this.setId(rs.getInt(COLUMN_ID.getColumnName()));
        this.setAddress((Address) SQL.findById(Address.class, rs.getInt(COLUMN_ADDR_ID.getColumnName())));
        this.setName(rs.getString(COLUMN_NAME.getColumnName()));
        this.setPhone(rs.getString(COLUMN_PHONE.getColumnName()));
        this.setFax(rs.getString(COLUMN_FAX.getColumnName()));
        this.setVatId(rs.getString(COLUMN_VAT_ID.getColumnName()));
        this.setVatValue(rs.getDouble(COLUMN_VAT_VALUE.getColumnName()));
    }

    public void save() throws Exception {
        SQL sql = new SQL();
        int id = sql
                .insert()
                .into(MerchantInfo.TABLE_NAME, MerchantInfo.COLUMN_NAME, MerchantInfo.COLUMN_VAT_ID, MerchantInfo.COLUMN_PHONE,
                        MerchantInfo.COLUMN_FAX, MerchantInfo.COLUMN_VAT_VALUE, MerchantInfo.COLUMN_ADDR_ID)
                .values(this.getName(), this.getVatId(), this.getPhone(), this.getFax(),
                        this.getVatValue(), this.address.getId())
                .exec();
        this.setId(id);
    }

    public void update() throws Exception{
        SQL sql = new SQL();
            sql
                    .update(this.TABLE_NAME)
                    .set(this.COLUMN_NAME, this.getName())
                    .set(this.COLUMN_VAT_ID, this.getVatId())
                    .set(this.COLUMN_PHONE, this.getPhone())
                    .set(this.COLUMN_FAX, this.getFax())
                    .set(this.COLUMN_VAT_VALUE, this.getVatValue())
                    .set(this.COLUMN_ADDR_ID, address.getId())
                    .where(this.COLUMN_ID, SQL.WhereClause.Operator.EQ, this.getId())
                    .exec();
    }

    public static int delete(int MEIN_ID) throws Exception{
        SQL sql = new SQL();
        int a = sql
                .delete(MerchantInfo.TABLE_NAME)
                .where(MerchantInfo.COLUMN_ID, SQL.WhereClause.Operator.EQ, MEIN_ID)
                .exec();
        return a;
    }
   
}
