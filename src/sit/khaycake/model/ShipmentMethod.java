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
public class ShipmentMethod implements ORM {
    private int id;
    private String name;
    private double price;

    public static final String TABLE_NAME = "SHIPMENT_METHODS";
    public static final Column COLUMN_ID = ORM.column(TABLE_NAME, "SHME_ID");
    public static final Column COLUMN_NAME = ORM.column(TABLE_NAME, "NAME");
    public static final Column COLUMN_PRICE = ORM.column(TABLE_NAME, "PRICE");
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void orm(ResultSet rs) throws SQLException {

        this.setId(rs.getInt(COLUMN_ID.getColumnName()));
        this.setName(rs.getString(COLUMN_NAME.getColumnName()));
        this.setPrice(rs.getDouble(COLUMN_PRICE.getColumnName()));
    }

    public void save() throws Exception {
        SQL sql = new SQL();
        int id = sql
                .insert()
                .into(ShipmentMethod.TABLE_NAME, ShipmentMethod.COLUMN_NAME, ShipmentMethod.COLUMN_PRICE)
                .values(this.getName(), this.getPrice())
                .exec();
        this.setId(id);
    }

    public void update() throws Exception {
        SQL sql = new SQL();
        sql
                .update(ShipmentMethod.TABLE_NAME)
                .set(ShipmentMethod.COLUMN_NAME, this.getName())
                .set(ShipmentMethod.COLUMN_PRICE, this.getPrice())
                .where(ShipmentMethod.COLUMN_ID, SQL.WhereClause.Operator.EQ, this.getId())
                .exec();
    }

    public static int delete(int SHME_ID) throws Exception {
        SQL sql = new SQL();
        int a = sql
                .delete(ShipmentMethod.TABLE_NAME)
                .where(ShipmentMethod.COLUMN_ID, SQL.WhereClause.Operator.EQ, SHME_ID)
                .exec();
        return a;
    }
}
