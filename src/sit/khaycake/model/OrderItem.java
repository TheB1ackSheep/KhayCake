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
public class OrderItem implements ORM {

    private int id;
    private Product product;
    private Order order;
    private int qty;
    private double amount;

    public static final String TABLE_NAME = "ORDER_ITEMS";
    public static final Column COLUMN_ID = ORM.column(TABLE_NAME, "ORIT_ID");
    public static final Column COLUMN_PROD_ID = ORM.column(TABLE_NAME, "PROD_ID");
    public static final Column COLUMN_ORDER_ID = ORM.column(TABLE_NAME, "ORDER_ID");
    public static final Column COLUMN_QTY = ORM.column(TABLE_NAME, "QTY");
    public static final Column COLUMN_AMOUNT = ORM.column(TABLE_NAME, "TOTAL_PRICE");
    public static final List<Column> PRIMARY_KEY = ORM.columns(COLUMN_ID);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void orm(ResultSet rs) throws Exception {

        this.setId(rs.getInt(COLUMN_ID.getColumnName()));
        this.setProduct(SQL.findById(Product.class, rs.getInt(COLUMN_PROD_ID.getColumnName())));
        this.setOrder(SQL.findById(Order.class, rs.getInt(COLUMN_ORDER_ID.getColumnName())));
        this.setQty(rs.getInt(COLUMN_QTY.getColumnName()));
        this.setAmount(rs.getInt(COLUMN_AMOUNT.getColumnName()));


    }

    public void save() throws Exception {
        SQL sql = new SQL();
        this.id = sql
                .insert()
                .into(TABLE_NAME, COLUMN_AMOUNT, COLUMN_ORDER_ID,
                        COLUMN_PROD_ID, COLUMN_QTY)
                .values(this.amount, this.order.getId(), this.product.getId(), this.qty)
                .exec();
    }

    public static int delete(int ORIT_ID) throws Exception {
        SQL sql = new SQL();
        int a = sql
                .delete(OrderItem.TABLE_NAME)
                .where(OrderItem.COLUMN_ID, SQL.WhereClause.Operator.EQ, ORIT_ID)
                .exec();
        return a;
    }
}
