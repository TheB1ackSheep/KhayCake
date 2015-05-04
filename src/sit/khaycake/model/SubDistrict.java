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
    private int districtId;
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

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
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
        this.setDistrictId(rs.getInt(COLUMN_DIST_ID.getColumnName()));
        this.setName(rs.getString(COLUMN_NAME.getColumnName()));
        this.setZipCode(rs.getString(COLUMN_ZIPCODE.getColumnName()));
    }



    public void save() throws Exception {
        SQL sql = new SQL();
        int id = sql
                .insert()
                .into(SubDistrict.TABLE_NAME, SubDistrict.COLUMN_NAME, SubDistrict.COLUMN_DIST_ID)
                .values(this.getName(), this.getDistrictId())
                .exec();
        this.setId(id);
    }

    public void update() throws Exception{
        SQL sql = new SQL();
        sql
                .update(SubDistrict.TABLE_NAME)
                .set(SubDistrict.COLUMN_NAME, this.getName())
                .set(SubDistrict.COLUMN_DIST_ID, this.getDistrictId())
                .where(BankAccountType.COLUMN_ID, SQL.WhereClause.Operator.EQ, this.getId())
                .exec();
    }

    public static int delete(int SUDT_ID) throws Exception{
        SQL sql = new SQL();
        int a = sql
                .delete(SubDistrict.TABLE_NAME)
                .where(SubDistrict.COLUMN_SUDT_ID, SQL.WhereClause.Operator.EQ, SUDT_ID)
                .exec();
        return a;
    }
    
}
