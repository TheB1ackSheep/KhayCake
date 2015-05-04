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

    public void save() throws Exception {
        SQL sql = new SQL();
        int id = sql
                .insert()
                .into(Province.TABLE_NAME, Province.COLUMN_NAME)
                .values(this.getName())
                .exec();
        this.setId(id);
    }

    public void update() throws Exception{
        SQL sql = new SQL();
        sql
                .update(Province.TABLE_NAME)
                .set(Province.COLUMN_NAME, this.getName())
                .where(Province.COLUMN_ID, SQL.WhereClause.Operator.EQ, this.getId())
                .exec();
    }

    public static int delete(int PROV_ID) throws Exception{
        SQL sql = new SQL();
        int a = sql
                .delete(Province.TABLE_NAME)
                .where(Province.COLUMN_ID, SQL.WhereClause.Operator.EQ, PROV_ID)
                .exec();
        return a;
    }

}
