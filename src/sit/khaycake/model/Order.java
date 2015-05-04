/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.khaycake.model;

import sit.khaycake.database.Column;
import sit.khaycake.database.ORM;
import sit.khaycake.database.SQL;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.List;

/**
 * @author -milk
 */
public class Order implements ORM {

    private int orderId;
    private Date orderDate;
    private int totalQty;
    private double totalPrice;
    private Status status;
    private ShipMethod shipMethod;
    private String shtrId;
    private Customer customer;

    public enum Status {

        SAVING(1),
        CURRENT(2);

        private OrderStatus status;
        private int id;
        private String name;

        Status(int id) {
            try {
                this.id = id;
                OrderStatus st = (OrderStatus) SQL.findById(OrderStatus.class, id);
                this.name = (st == null) ? null : st.getName();
            } catch (Exception e) {
                //must be caught or dec;ared to be thrown
            }
        }

        public static Status getStatus(int id) {
            switch (id) {
                case 1:
                    return SAVING;
                case 2:
                    return CURRENT;
                default:
                    return SAVING;
            }
        }

        public int getId() {
            return this.id;
        }

    }

    public enum ShipMethod {

        SAVING(1),
        CURRENT(2);

        private ShipmentMethod sm;
        private int id;
        private String name;

        ShipMethod(int id) {
            try {
                this.id = id;
                ShipmentMethod sm = (ShipmentMethod) SQL.findById(ShipmentMethod.class, id);
                this.name = (sm == null) ? null : sm.getName();
            } catch (Exception e) {
                //must be caught or dec;ared to be thrown
            }
        }

        public static ShipMethod getShipMethod(int id) {
            switch (id) {
                case 1:
                    return SAVING;
                case 2:
                    return CURRENT;
                default:
                    return SAVING;
            }
        }

        public int getId() {
            return this.id;
        }

    }

    public static final String TABLE_NAME = "ORDERS";
    public static final Column COLUMN_ID = ORM.column(TABLE_NAME, "ORDER_ID");
    public static final Column COLUMN_ORDER_DATE = ORM.column(TABLE_NAME, "ORDER_DATE");
    public static final Column COLUMN_TOTAL_QTY = ORM.column(TABLE_NAME, "TOTAL_QTY");
    public static final Column COLUMN_TOTAL_PRICE = ORM.column(TABLE_NAME, "TOTAL_PRICE");
    public static final Column COLUMN_ORST_ID = ORM.column(TABLE_NAME, "ORST_ID");
    public static final Column COLUMN_SHME_ID = ORM.column(TABLE_NAME, "SHME_ID");
    public static final Column COLUMN_SHTR_ID = ORM.column(TABLE_NAME, "SHTR_ID");
    public static final Column COLUMN_CUST_ID = ORM.column(TABLE_NAME, "CUST_ID");
    public static final List<Column> PRIMARY_KEY = ORM.columns(COLUMN_ID);

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public int getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(int totalQty) {
        this.totalQty = totalQty;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ShipMethod getShipMethod() {
        return shipMethod;
    }

    public void setShipMethod(ShipMethod shipMethod) {
        this.shipMethod = shipMethod;
    }

    public String getShtrId() {
        return shtrId;
    }

    public void setShtrId(String shtrId) {
        this.shtrId = shtrId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void orm(ResultSet rs) throws Exception {

        this.setOrderId(rs.getInt(COLUMN_ID.getColumnName()));
        this.setOrderDate(rs.getDate(COLUMN_ORDER_DATE.getColumnName()));
        this.setTotalQty(rs.getInt(COLUMN_TOTAL_QTY.getColumnName()));
        this.setTotalPrice(rs.getDouble(COLUMN_TOTAL_PRICE.getColumnName()));
        this.setStatus(Status.getStatus(rs.getInt(COLUMN_ORST_ID.getColumnName())));
        this.setShipMethod(ShipMethod.getShipMethod(rs.getInt(COLUMN_SHME_ID.getColumnName())));
        this.setShtrId(rs.getString(COLUMN_SHTR_ID.getColumnName()));
        this.setCustomer((Customer) SQL.findById(Customer.class, rs.getInt(COLUMN_CUST_ID.getColumnName())));


    }

    public void save() throws Exception {
        SQL sql = new SQL();
        int id = sql
                .insert()
                .into(Order.TABLE_NAME, Order.COLUMN_CUST_ID, Order.COLUMN_ORDER_DATE, Order.COLUMN_ORST_ID, Order.COLUMN_SHME_ID,
                        Order.COLUMN_SHTR_ID, Order.COLUMN_TOTAL_PRICE, Order.COLUMN_TOTAL_QTY)
                .values(this.getCustomer().getId(), this.getOrderDate(), this.getStatus().getId(), this.getShipMethod().getId(), this.getShtrId(),
                        this.getTotalPrice(), this.getTotalQty())
                .exec();
        this.setOrderId(id);
    }

    public void update() throws Exception {
        SQL sql = new SQL();
        sql
                .update(Order.TABLE_NAME)
                .set(Order.COLUMN_CUST_ID, this.getCustomer().getId())
                .set(Order.COLUMN_ORDER_DATE, this.getOrderDate())
                .set(Order.COLUMN_ORST_ID, this.getStatus().getId())
                .set(Order.COLUMN_SHME_ID, this.getShipMethod().getId())
                .set(Order.COLUMN_SHTR_ID, this.getShtrId())
                .set(Order.COLUMN_TOTAL_PRICE, this.getTotalPrice())
                .set(Order.COLUMN_TOTAL_QTY, this.getTotalQty())
                .where(Order.COLUMN_ID, SQL.WhereClause.Operator.EQ, this.getOrderId())
                .exec();

    }

    public static int delete(int ORDER_ID) throws Exception {
        SQL sql = new SQL();
        int a = sql
                .delete(Order.TABLE_NAME)
                .where(Order.COLUMN_ID, SQL.WhereClause.Operator.EQ, ORDER_ID)
                .exec();
        return a;
    }


}
