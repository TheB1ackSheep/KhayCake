/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.khaycake.model;

import java.sql.ResultSet;
import java.util.List;

import sit.khaycake.database.CanFindByKeyword;
import sit.khaycake.database.Column;
import sit.khaycake.database.ORM;
import sit.khaycake.database.SQL;

/**
 *
 * @author -milk
 */
public class SubDistrict implements ORM, CanFindByKeyword {
    private int id;
    private District district;
    private String name;
    private String zipCode;
    
    public static final String TABLE_NAME = "SUB_DISTRICTS";
    public static final Column COLUMN_SUDT_ID = ORM.column(TABLE_NAME, "SUDT_ID");
    public static final Column COLUMN_DIST_ID = ORM.column(TABLE_NAME, "DIST_ID");
    public static final Column COLUMN_NAME = ORM.column(TABLE_NAME, "NAME");
    public static final Column COLUMN_ZIPCODE = ORM.column(TABLE_NAME, "ZIPCODE");
    public static final List<Column> PRIMARY_KEY = ORM.columns(COLUMN_SUDT_ID);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public District getDistrict() {
        return district;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    public void orm(ResultSet rs) throws Exception {
        
        
        this.setId(rs.getInt(COLUMN_SUDT_ID.getColumnName()));
        this.setDistrict((District) SQL.findById(District.class,rs.getInt(COLUMN_DIST_ID.getColumnName())));
        this.setName(rs.getString(COLUMN_NAME.getColumnName()));
        this.setZipCode(rs.getString(COLUMN_ZIPCODE.getColumnName()));
        

        
    }
    
    
}
