/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.khaycake.model;

import sit.khaycake.database.Column;
import sit.khaycake.database.ORM;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author -milk
 */
public class ProductMeta implements ORM {
    private int id;
    private int prmnId;

    public static final String TABLE_NAME = "PRODUCT_META";
    public static final Column COLUMN_PROD_ID = ORM.column(TABLE_NAME, "PROD_ID");
    public static final Column COLUMN_PRMN_ID = ORM.column(TABLE_NAME, "PRMN_ID");
    public static final List<Column> PRIMARY_KEY = ORM.columns(COLUMN_PROD_ID, COLUMN_PRMN_ID);


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrmnId() {
        return prmnId;
    }

    public void setPrmnId(int prmnId) {
        this.prmnId = prmnId;
    }

    public void orm(ResultSet rs) throws SQLException {

        this.setId(rs.getInt(COLUMN_PROD_ID.getColumnName()));
        this.setPrmnId(rs.getInt(COLUMN_PRMN_ID.getColumnName()));


    }
}
