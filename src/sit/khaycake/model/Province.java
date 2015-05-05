/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.khaycake.model;

import sit.khaycake.database.CanFindByKeyword;
import sit.khaycake.database.Column;
import sit.khaycake.database.ORM;
import sit.khaycake.database.SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author -milk
 */
public class Province implements ORM, CanFindByKeyword {
    private int id;
    private String name;
    private Geography geography;

    public static final String TABLE_NAME = "PROVINCES";
    public static final Column COLUMN_ID = ORM.column(TABLE_NAME, "PROV_ID");
    public static final Column COLUMN_NAME = ORM.column(TABLE_NAME, "NAME");
    public static final Column COLUMN_GEO_ID = ORM.column(TABLE_NAME, "GEO_ID");
    public static final List<Column> PRIMARY_KEY = ORM.columns(COLUMN_ID);
    public static final List<Column> COLUMN_KEYWORD = ORM.columns(COLUMN_NAME);


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

    public Geography getGeography() {
        return geography;
    }

    public void setGeography(Geography geography) {
        this.geography = geography;
    }

    public static List<Province> find(String str) throws Exception {
        SQL sql = new SQL();
        return sql.select()
                .from(TABLE_NAME)
                .where(COLUMN_NAME, SQL.WhereClause.Operator.LIKE, "%"+str+"%")
                .chunk(10)
                .fetch(Province.class);
    }

    public void orm(ResultSet rs) throws Exception {

        this.setId(rs.getInt(COLUMN_ID.getColumnName()));
        this.setName(rs.getString(COLUMN_NAME.getColumnName()));
        this.geography = SQL.findById(Geography.class, rs.getInt(COLUMN_GEO_ID.getColumnName()));

    }

    public List<District> getDistrictList() throws Exception {
        SQL sql = new SQL();
        List<District> result = sql
                .select()
                .from(District.TABLE_NAME)
                .where(District.COLUMN_PROV_ID, SQL.WhereClause.Operator.EQ, this.getId())
                .fetch(District.class);
        return result;
    }

}
