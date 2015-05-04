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
public class PaymentStatus implements ORM {
    private int id;
    private String name;

    public static final String TABLE_NAME = "PAYMENT_STATUS";
    public static final Column COLUMN_ID = ORM.column(TABLE_NAME, "PAST_ID");
    public static final Column COLUMN_NAME = ORM.column(TABLE_NAME, "NAME");
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

    public void orm(ResultSet rs) throws SQLException {

        this.setId(rs.getInt(COLUMN_ID.getColumnName()));
        this.setName(rs.getString(COLUMN_NAME.getColumnName()));

    }

}
