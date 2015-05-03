/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.khaycake.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import sit.khaycake.database.CanFindByKeyword;
import sit.khaycake.database.Column;
import sit.khaycake.database.ORM;
import sit.khaycake.database.SQL;

/**
 * @author -milk
 */
public class Province implements ORM, CanFindByKeyword {
    private int id;
    private String name;

    public static final String TABLE_NAME = "PROVINCES";
    public static final Column COLUMN_ID = ORM.column(TABLE_NAME, "PROV_ID");
    public static final Column COLUMN_NAME = ORM.column(TABLE_NAME, "NAME");
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

    public void orm(ResultSet rs) throws SQLException {

        this.setId(rs.getInt(COLUMN_ID.getColumnName()));
        this.setName(rs.getString(COLUMN_NAME.getColumnName()));

    }

    public List<District> getDistrictList() throws Exception{
            SQL sql = new SQL();
            List<District> result = sql
                    .select()
                    .from(District.TABLE_NAME)
                    .where(District.COLUMN_PROV_ID, SQL.WhereClause.Operator.EQ, this.getId())
                    .fetch(District.class);
        return result;
    }
}
