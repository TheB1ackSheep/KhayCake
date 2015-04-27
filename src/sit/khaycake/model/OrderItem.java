/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.khaycake.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import sit.khaycake.database.Column;
import sit.khaycake.database.ORM;
import sit.khaycake.database.SQL;

/**
 *
 * @author -milk
 */
public class OrderItem implements ORM {
    
    private int oritId;
    private ProductSale productSale;
    private Order order;
    private int qty;
    private double priceUnit;
    private double amount;
    
    public static final String TABLE_NAME = "ORDER_ITEMS";
    public static final Column COLUMN_ID = ORM.column(TABLE_NAME,"ORIT_ID");
    public static final Column COLUMN_PRSA_ID = ORM.column(TABLE_NAME, "PRSA_ID");
    public static final Column COLUMN_ORDER_ID = ORM.column(TABLE_NAME, "ORDER_ID");
    public static final Column COLUMN_QTY = ORM.column(TABLE_NAME, "QTY");
    public static final Column COLUMN_PRICE_UNIT = ORM.column(TABLE_NAME, "PRICE_UNIT");
    public static final Column COLUMN_AMOUNT = ORM.column(TABLE_NAME, "AMOUNT");
    public static final List<Column> PRIMARY_KEY = ORM.columns(COLUMN_ID);

    public int getOritId() {
        return oritId;
    }

    public void setOritId(int oritId) {
        this.oritId = oritId;
    }

    public ProductSale getProductSale() {
        return productSale;
    }

    public void setProductSale(ProductSale productSale) {
        this.productSale = productSale;
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

    public double getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(double priceUnit) {
        this.priceUnit = priceUnit;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public void orm(ResultSet rs) throws Exception {
        
        this.setOritId(rs.getInt(COLUMN_ID.getColumnName()));
        this.setProductSale((ProductSale)SQL.findById(ProductSale.class,rs.getInt(COLUMN_PRSA_ID.getColumnName())));
        this.setOrder((Order)SQL.findById(Order.class,rs.getInt(COLUMN_ORDER_ID.getColumnName())));
        this.setQty(rs.getInt(COLUMN_QTY.getColumnName()));        
        this.setPriceUnit(rs.getDouble(COLUMN_PRICE_UNIT.getColumnName()));
        this.setAmount(rs.getInt(COLUMN_AMOUNT.getColumnName()));
        
             
    }

    public void save() throws Exception {
        SQL sql = new SQL();
        int id = sql
                .insert()
                .into(OrderItem.TABLE_NAME, OrderItem.COLUMN_AMOUNT, OrderItem.COLUMN_ORDER_ID, OrderItem.COLUMN_PRICE_UNIT,
                        OrderItem.COLUMN_PRSA_ID, OrderItem.COLUMN_QTY)
                .values(this.getAmount(), this.getOrder().getOrderId(), this.getPriceUnit(), this.getProductSale().getId(), this.getQty())
                .exec();
        this.setOritId(id);
    }

    public void update() throws Exception{
        SQL sql = new SQL();
            sql
                    .update(OrderItem.TABLE_NAME)
                    .set(OrderItem.COLUMN_AMOUNT, this.getAmount())
                    .set(OrderItem.COLUMN_ORDER_ID, this.getOrder().getOrderId())
                    .set(OrderItem.COLUMN_PRICE_UNIT, this.getPriceUnit())
                    .set(OrderItem.COLUMN_PRSA_ID, this.getProductSale().getId())
                    .set(OrderItem.COLUMN_QTY, this.getQty())
                    .where(OrderItem.COLUMN_ID, SQL.WhereClause.Operator.EQ, this.getOritId())
                    .exec();

    }

    public static int delete(int ORIT_ID) throws Exception{
        SQL sql = new SQL();
        int a = sql
                .delete(OrderItem.TABLE_NAME)
                .where(OrderItem.COLUMN_ID, SQL.WhereClause.Operator.EQ, ORIT_ID)
                .exec();
        return a;
    }
}
