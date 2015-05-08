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
import java.sql.SQLException;
import java.util.List;

/**
 * @author -milk
 */
public class Order implements ORM {

    private int id;
    private Date date;
    private int totalQty;
    private double totalPrice;
    private OrderStatus status;
    private ShipmentMethod shipMethod;
    private ShipmentAddress shipAddress;
    private String trackId;
    private Customer customer;

    public static final String TABLE_NAME = "ORDERS";
    public static final Column COLUMN_ID = ORM.column(TABLE_NAME, "ORDER_ID");
    public static final Column COLUMN_ORDER_DATE = ORM.column(TABLE_NAME, "ORDER_DATE");
    public static final Column COLUMN_TOTAL_QTY = ORM.column(TABLE_NAME, "TOTAL_QTY");
    public static final Column COLUMN_TOTAL_PRICE = ORM.column(TABLE_NAME, "TOTAL_PRICE");
    public static final Column COLUMN_ORST_ID = ORM.column(TABLE_NAME, "ORST_ID");
    public static final Column COLUMN_SHME_ID = ORM.column(TABLE_NAME, "SHME_ID");
    public static final Column COLUMN_SHAD_ID = ORM.column(TABLE_NAME, "SHAD_ID");
    public static final Column COLUMN_SHTR_ID = ORM.column(TABLE_NAME, "SHTR_ID");
    public static final Column COLUMN_CUST_ID = ORM.column(TABLE_NAME, "CUST_ID");
    public static final List<Column> PRIMARY_KEY = ORM.columns(COLUMN_ID);

    public int getId() {
        return id;
    }

    public void setId(int orderId) {
        this.id = orderId;
    }

    public Date getOrderDate() {
        return date;
    }

    public void setOrderDate(Date orderDate) {
        this.date = orderDate;
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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public ShipmentMethod getShipMethod() {
        return shipMethod;
    }

    public void setShipMethod(ShipmentMethod shipMethod) {
        this.shipMethod = shipMethod;
    }

    public ShipmentAddress getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(ShipmentAddress shipAddress) {
        this.shipAddress = shipAddress;
    }

    public String getShtrId() {
        return trackId;
    }

    public void setShtrId(String trackId) {
        this.trackId = trackId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void orm(ResultSet rs) throws Exception {

        this.setId(rs.getInt(COLUMN_ID.getColumnName()));
        this.setOrderDate(rs.getDate(COLUMN_ORDER_DATE.getColumnName()));
        this.setTotalQty(rs.getInt(COLUMN_TOTAL_QTY.getColumnName()));
        this.setTotalPrice(rs.getDouble(COLUMN_TOTAL_PRICE.getColumnName()));
        this.setShtrId(rs.getString(COLUMN_SHTR_ID.getColumnName()));
        this.setStatus(SQL.findById(OrderStatus.class, rs.getInt(COLUMN_ORST_ID.getColumnName())));
        this.setShipMethod(SQL.findById(ShipmentMethod.class, rs.getInt(COLUMN_SHME_ID.getColumnName())));
        this.setShipAddress(SQL.findById(ShipmentAddress.class, rs.getInt(COLUMN_SHAD_ID.getColumnName())));
        this.setCustomer(SQL.findById(Customer.class, rs.getInt(COLUMN_CUST_ID.getColumnName())));

    }

    public void save() throws Exception {
        SQL sql = new SQL();
        this.id = sql
                .insert()
                .into(TABLE_NAME, COLUMN_ID, COLUMN_ORDER_DATE, COLUMN_TOTAL_QTY, COLUMN_TOTAL_PRICE, COLUMN_SHTR_ID, COLUMN_ORST_ID
                ,COLUMN_SHME_ID, COLUMN_SHAD_ID, COLUMN_CUST_ID)
                .values(this.id, this.date, this.totalQty, this.totalPrice, this.trackId, this.status.getId()
                , this.shipMethod.getId(), this.shipAddress.getId(), this.customer.getId())
                .exec();
    }

    public void update() throws Exception {
        SQL sql = new SQL();
        sql
                .update(Order.TABLE_NAME)
                .set(Order.COLUMN_ORST_ID, this.getStatus().getId())
                .set(COLUMN_SHTR_ID, this.trackId)
                .where(COLUMN_ID, SQL.WhereClause.Operator.EQ, this.id)
                .exec();

    }



    public List<OrderItem> getItems() throws Exception {
        SQL sql = new SQL();
        return sql.select()
                    .from(OrderItem.TABLE_NAME)
                    .where(OrderItem.COLUMN_ORDER_ID, SQL.WhereClause.Operator.EQ, this.id)
                    .fetch(OrderItem.class);
    }


    public static List<OrderItem> getItems(String order_id) throws Exception {
        SQL sql = new SQL();
        return sql.select()
                .from(OrderItem.TABLE_NAME)
                .where(OrderItem.COLUMN_ORDER_ID, SQL.WhereClause.Operator.EQ, order_id)
                .fetch(OrderItem.class);
    }

    public List<Payment> getPayments() throws Exception {
        SQL sql = new SQL();
        return sql.select()
                .from(Payment.TABLE_NAME)
                .where(Payment.COLUMN_ORDER_ID, SQL.WhereClause.Operator.EQ, this.id)
                .fetch(Payment.class);
    }

    public static List<Order> findAll() throws Exception {
        SQL sql = new SQL();
        return sql.select()
                .from(TABLE_NAME)
                .where(COLUMN_ORST_ID, SQL.WhereClause.Operator.NE, 4)
                .order(COLUMN_ORDER_DATE, SQL.OrderClause.Operator.DESC)
                .fetch(Order.class);
    }

    public static List<Order> findByKeyword(Object... q) throws Exception {
        SQL sql = new SQL();
        String sqlCmd = "SELECT * FROM "+TABLE_NAME+" "+
                "JOIN "+ShipmentAddress.TABLE_NAME+" ON "+Order.COLUMN_SHAD_ID+" = "+ShipmentAddress.COLUMN_ID+" "+
                "JOIN "+Customer.TABLE_NAME+" ON "+Order.COLUMN_CUST_ID+" = "+Customer.COLUMN_ID+" WHERE ";
        for(int i=0;i<q.length;i++){
            sqlCmd +=  "("+Customer.COLUMN_PHONE+" LIKE ? OR "+Customer.COLUMN_EMAIL+" LIKE ? OR "+Customer.COLUMN_FNAME+" LIKE ? " +
                    "OR "+Customer.COLUMN_LNAME+" LIKE ? OR "+ShipmentAddress.COLUMN_FNAME+" LIKE ? OR "+ShipmentAddress.COLUMN_LNAME+" LIKE ? OR "+Order.COLUMN_ID+" = ?)";
            if(i < q.length-1)
                sqlCmd += " AND ";
            sql.addParam("%"+q[i]+"%");
            sql.addParam("%"+q[i]+"%");
            sql.addParam("%"+q[i]+"%");
            sql.addParam("%"+q[i]+"%");
            sql.addParam("%"+q[i]+"%");
            sql.addParam("%"+q[i]+"%");
            sql.addParam(q[i]);
        }
        sql.setSql(sqlCmd);
        return  sql.fetch(Order.class);
    }

    public static List<Order> findByStatusAndKeyword(Object statusId, Object... q) throws Exception {
        SQL sql = new SQL();
        if(q == null) {
            String sqlCmd = "SELECT * FROM khaycake.orders WHERE ORDER_DATE > DATE_SUB(NOW(), INTERVAL 7 DAY) AND ORST_ID="+statusId+" ORDER BY ORDER_DATE DESC";
            return new SQL(sqlCmd).fetch(Order.class);
        }else {
            String sqlCmd = "SELECT * FROM " + TABLE_NAME + " " +
                    "JOIN " + ShipmentAddress.TABLE_NAME + " ON " + Order.COLUMN_SHAD_ID + " = " + ShipmentAddress.COLUMN_ID + " " +
                    "JOIN " + Customer.TABLE_NAME + " ON " + Order.COLUMN_CUST_ID + " = " + Customer.COLUMN_ID + " WHERE " + COLUMN_ORST_ID + " = " + statusId + " AND ";
            for (int i = 0; i < q.length; i++) {
                sqlCmd += "(" + Customer.COLUMN_PHONE + " LIKE ? OR " + Customer.COLUMN_EMAIL + " LIKE ? OR " + Customer.COLUMN_FNAME + " LIKE ? " +
                        "OR " + Customer.COLUMN_LNAME + " LIKE ? OR " + ShipmentAddress.COLUMN_FNAME + " LIKE ? OR " + ShipmentAddress.COLUMN_LNAME + " LIKE ? OR "+Order.COLUMN_ID+" = ?)";
                if (i < q.length - 1)
                    sqlCmd += " AND ";
                sql.addParam("%" + q[i] + "%");
                sql.addParam("%" + q[i] + "%");
                sql.addParam("%" + q[i] + "%");
                sql.addParam("%" + q[i] + "%");
                sql.addParam("%" + q[i] + "%");
                sql.addParam("%" + q[i] + "%");
                sql.addParam(q[i]);
            }
            sql.setSql(sqlCmd);
            return sql.fetch(Order.class);
        }
    }

    public static List<Order> getShippingOrder(Object... q) throws Exception {
        return findByStatusAndKeyword(2, q);
    }

    public static List<Order> getPaidOrder(Object... q) throws Exception {
        return findByStatusAndKeyword(5, q);
    }
}
