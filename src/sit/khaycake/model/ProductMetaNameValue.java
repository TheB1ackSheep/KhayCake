/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.khaycake.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import sit.khaycake.database.Column;
import sit.khaycake.database.ORM;
/**
 *
 * @author -milk
 */
public class ProductMetaNameValue implements ORM {
    
    private int id;
    private String value;
    private double price;
    private int prmnId;
    
    public static final String TABLE_NAME = "PRODUCT_META_NAME_VALUE";
    public static final Column COLUMN_ID = ORM.column(TABLE_NAME, "PRMV_ID");
    public static final Column COLUMN_VALUE = ORM.column(TABLE_NAME, "VALUE");
    public static final Column COLUMN_PRICE = ORM.column(TABLE_NAME, "PRICE");
    public static final Column COLUMN_PRMN_ID = ORM.column(TABLE_NAME, "PRMN_ID");
    public static final List<Column> PRIMARY_KEY = ORM.columns(COLUMN_ID);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPrmnId() {
        return prmnId;
    }

    public void setPrmnId(int prmnId) {
        this.prmnId = prmnId;
    }
    
     public void orm(ResultSet rs) throws SQLException {
        
        this.setId(rs.getInt(COLUMN_ID.getColumnName()));
        this.setValue(rs.getString(COLUMN_VALUE.getColumnName()));
        this.setPrice(rs.getDouble(COLUMN_PRICE.getColumnName()));
        this.setPrmnId(rs.getInt(COLUMN_PRMN_ID.getColumnName()));
        
        
        
    }
    
    
    
}
