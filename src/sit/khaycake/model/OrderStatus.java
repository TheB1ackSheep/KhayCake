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
import java.sql.SQLException;
import java.util.List;

/**
 * @author -milk
 */
public class OrderStatus implements ORM {
    private int id;
    private String name;

    public static final String TABLE_NAME = "ORDER_STATUS";
    public static final Column COLUMN_ID = ORM.column(TABLE_NAME, "ORST_ID");
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

    public void save() throws Exception {
        SQL sql = new SQL();
        int id = sql
                .insert()
                .into(OrderStatus.TABLE_NAME, OrderStatus.COLUMN_NAME)
                .values(this.getName())
                .exec();
        this.setId(id);
    }

    public void update() throws Exception {
        SQL sql = new SQL();
        sql
                .update(OrderStatus.TABLE_NAME)
                .set(OrderStatus.COLUMN_NAME, this.getName())
                .where(OrderStatus.COLUMN_ID, SQL.WhereClause.Operator.EQ, this.getId())
                .exec();
    }

    public static int delete(int ORST_ID) throws Exception {
        SQL sql = new SQL();
        int a = sql
                .delete(OrderStatus.TABLE_NAME)
                .where(OrderStatus.COLUMN_ID, SQL.WhereClause.Operator.EQ, ORST_ID)
                .exec();
        return a;
    }
}
