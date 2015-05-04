/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.khaycake.model;

import sit.khaycake.database.Column;
import sit.khaycake.database.ORM;
import sit.khaycake.database.SQL;

import java.sql.ResultSet;
import java.util.List;


/**
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

}
