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
public class Product {
    
    private int id;
    private String name;
    private String detail;
    private double cost;
    private int catId;

    public static final String TABLE_NAME = "PRODUCT";
    public static final Column COLUMN_ID = ORM.column(TABLE_NAME, "PROD_ID");
    public static final Column COLUMN_NAME = ORM.column(TABLE_NAME, "NAME");
    public static final Column COLUMN_DETAIL = ORM.column(TABLE_NAME, "DETAIL");
    public static final Column COLUMN_COST = ORM.column(TABLE_NAME, "COST");
    public static final Column COLUMN_CAT_ID = ORM.column(TABLE_NAME, "CAT_ID");
    public static final List<Column> PRIMARY_KEY = ORM.columns(COLUMN_ID);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getCatId() { return catId;}

    public void setCatId(int catId) { this.catId = catId;}
    
     public void orm(ResultSet rs) throws SQLException {
        
        this.setId(rs.getInt(COLUMN_ID.getColumnName()));
        this.setName(rs.getString(COLUMN_NAME.getColumnName()));
        this.setDetail(rs.getString(COLUMN_DETAIL.getColumnName()));
        this.setCost(rs.getDouble(COLUMN_COST.getColumnName()));
        
    }
}
