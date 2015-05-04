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
import java.util.List;

/**
 * @author -milk
 */
public class District implements ORM, CanFindByKeyword {
    private int id;
    private int provinceId;
    private String name;

    public static final String TABLE_NAME = "DISTRICT";
    public static final Column COLUMN_DIST_ID = ORM.column(TABLE_NAME, "DIST_ID");
    public static final Column COLUMN_PROV_ID = ORM.column(TABLE_NAME, "PROV_ID");
    public static final Column COLUMN_NAME = ORM.column(TABLE_NAME, "NAME");
    public static final List<Column> PRIMARY_KEY = ORM.columns(COLUMN_DIST_ID);
    public static final List<Column> COLUMN_KEYWORD = ORM.columns(COLUMN_NAME);


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvince(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void orm(ResultSet rs) throws Exception {
        this.setId(rs.getInt(COLUMN_DIST_ID.getColumnName()));
        this.setProvince(rs.getInt(COLUMN_PROV_ID.getColumnName()));
        this.setName(rs.getString(COLUMN_NAME.getColumnName()));
    }


    public List<SubDistrict> getSubDistrictList() throws Exception {
        SQL sql = new SQL();
        List<SubDistrict> result = sql
                .select()
                .from(SubDistrict.TABLE_NAME)
                .where(SubDistrict.COLUMN_DIST_ID, SQL.WhereClause.Operator.EQ, this.getId())
                .fetch(District.class);
        return result;
    }


    public void save() throws Exception {
        SQL sql = new SQL();
        int id = sql
                .insert()
                .into(District.TABLE_NAME, District.COLUMN_NAME, District.COLUMN_PROV_ID)
                .values(this.getName(), this.getProvinceId())
                .exec();
        this.setId(id);
    }

    public void update() throws Exception {
        SQL sql = new SQL();
        sql
                .update(District.TABLE_NAME)
                .set(District.COLUMN_NAME, this.getName())
                .set(District.COLUMN_PROV_ID, this.getProvinceId())
                .where(BankAccountType.COLUMN_ID, SQL.WhereClause.Operator.EQ, this.getId())
                .exec();
    }

    public static int delete(int BAAC_ID) throws Exception {
        SQL sql = new SQL();
        int a = sql
                .delete(BankAccountType.TABLE_NAME)
                .where(BankAccountType.COLUMN_ID, SQL.WhereClause.Operator.EQ, BAAC_ID)
                .exec();
        return a;
    }

}
