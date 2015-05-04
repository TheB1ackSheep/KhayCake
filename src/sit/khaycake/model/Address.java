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
public class Address implements ORM {
    private int id;
    private int subDistrictId;
    private String street;
    private String addrAdd;
    private String addrNo;

    public static final String TABLE_NAME = "ADDRESSES";
    public static final Column COLUMN_ID = ORM.column(TABLE_NAME, "ADDR_ID");
    public static final Column COLUMN_SUB_DISTRICT_ID = ORM.column(TABLE_NAME, "SUDT_ID");
    public static final Column COLUMN_ADDR_NO = ORM.column(TABLE_NAME, "ADDR_NO");
    public static final Column COLUMN_STREET = ORM.column(TABLE_NAME, "STREET");
    public static final Column COLUMN_ADDR_ADD = ORM.column(TABLE_NAME, "ADDR_ADD");
    public static final List<Column> PRIMARY_KEY = ORM.columns(COLUMN_ID);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddrAdd() {
        return addrAdd;
    }

    public void setAddrAdd(String addrAdd) {
        this.addrAdd = addrAdd;
    }

    public String getAddrNo() {
        return addrNo;
    }

    public void setAddrNo(String addrNo) {
        this.addrNo = addrNo;
    }

    public int getSubDistrictId() {
        return subDistrictId;
    }

    public void setSubDistrictId(int subDistrictId) {
        this.subDistrictId = subDistrictId;
    }

    public void orm(ResultSet rs) throws Exception {
        this.setId(rs.getInt(COLUMN_ID.getColumnName()));
        this.setSubDistrictId(rs.getInt(COLUMN_SUB_DISTRICT_ID.getColumnName()));
        this.setAddrNo(rs.getString(COLUMN_ADDR_NO.getColumnName()));
        this.setStreet(rs.getString(COLUMN_STREET.getColumnName()));
        this.setAddrAdd(rs.getString(COLUMN_ADDR_ADD.getColumnName()));
    }

    public void save() throws Exception {
        SQL sql = new SQL();
        int addId = sql
                .insert()
                .into(Address.TABLE_NAME, Address.COLUMN_ADDR_ADD, Address.COLUMN_ADDR_NO, Address.COLUMN_STREET,
                        Address.COLUMN_SUB_DISTRICT_ID)
                .values(this.getAddrAdd(), this.getAddrNo(), this.getStreet(), this.getSubDistrictId())
                .exec();
        this.setId(addId);
    }

    public void update() throws Exception {
        SQL sql = new SQL();
        sql
                .update(Address.TABLE_NAME)
                .set(Address.COLUMN_ADDR_NO, this.getAddrNo())
                .set(Address.COLUMN_ADDR_ADD, this.getAddrAdd())
                .set(Address.COLUMN_STREET, this.getStreet())
                .set(Address.COLUMN_SUB_DISTRICT_ID, this.getSubDistrictId())
                .where(Address.COLUMN_ID, SQL.WhereClause.Operator.EQ, this.getId())
                .exec();
    }

    public static int delete(int ADDR_ID) throws Exception {
        SQL sql = new SQL();
        int a = sql
                .delete(Address.TABLE_NAME)
                .where(Address.COLUMN_ID, SQL.WhereClause.Operator.EQ, ADDR_ID)
                .exec();
        return a;
    }
}
