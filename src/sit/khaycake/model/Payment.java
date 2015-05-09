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
import java.sql.Timestamp;
import java.util.List;

/**
 * @author -milk
 */
public class Payment implements ORM {

    private int id;
    private Order order;
    private BankAccount baac;
    private PaymentStatus status;
    private Timestamp dateTime;
    private double amount;

    public static final String TABLE_NAME = "PAYMENTS";
    public static final Column COLUMN_ID = ORM.column(TABLE_NAME, "PATM_ID");
    public static final Column COLUMN_ORDER_ID = ORM.column(TABLE_NAME, "ORDER_ID");
    public static final Column COLUMN_BAAC_ID = ORM.column(TABLE_NAME, "BAAC_ID");
    public static final Column COLUMN_PAST_ID = ORM.column(TABLE_NAME, "PAST_ID");
    public static final Column COLUMN_DATE_TIME = ORM.column(TABLE_NAME, "DATETIME");
    public static final Column COLUMN_AMOUNT = ORM.column(TABLE_NAME, "AMOUNT");
    public static final List<Column> PRIMARY_KEY = ORM.columns(COLUMN_ID);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public BankAccount getBankAccount() {
        return baac;
    }

    public void setBankAccount(BankAccount baac) {
        this.baac = baac;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void orm(ResultSet rs) throws Exception {

        this.setId(rs.getInt(COLUMN_ID.getColumnName()));
        this.setDateTime(rs.getTimestamp(COLUMN_DATE_TIME.getColumnName()));
        this.setAmount(rs.getDouble(COLUMN_AMOUNT.getColumnName()));
        this.setOrder(SQL.findById(Order.class, rs.getInt(COLUMN_ORDER_ID.getColumnName())));
        this.setBankAccount(SQL.findById(BankAccount.class, rs.getInt(COLUMN_BAAC_ID.getColumnName())));
        this.setStatus(SQL.findById(PaymentStatus.class, rs.getInt(COLUMN_PAST_ID.getColumnName())));

    }

    public void save() throws Exception {
        SQL sql = new SQL();
        this.id = sql
                .insert()
                .into(Payment.TABLE_NAME, Payment.COLUMN_ORDER_ID, Payment.COLUMN_AMOUNT, Payment.COLUMN_BAAC_ID,
                        Payment.COLUMN_DATE_TIME, Payment.COLUMN_PAST_ID)
                .values(this.getOrder().getId(), this.getAmount(), this.getBankAccount().getId(), this.getDateTime(), this.getStatus().getId())
                .exec();
    }

    public void update() throws Exception {
        SQL sql = new SQL();
        sql
                .update(TABLE_NAME)
                .set(COLUMN_PAST_ID, this.status.getId())
                .where(COLUMN_ID, SQL.WhereClause.Operator.EQ, this.id)
                .exec();
    }

    public static int delete(int PATM_ID) throws Exception {
        SQL sql = new SQL();
        int a = sql
                .delete(Payment.TABLE_NAME)
                .where(Payment.COLUMN_ID, SQL.WhereClause.Operator.EQ, PATM_ID)
                .exec();
        return a;
    }

    public static List<Payment> findByKeyword(Object... q) throws Exception {
        return  findByStatusAndKeyword(null,q);
    }

    public static List<Payment> findByStatusAndKeyword(Object statusId,Object... q) throws Exception {
        SQL sql = new SQL();
        if(q == null) {
            String sqlCmd = "SELECT * FROM "+TABLE_NAME+(statusId!=null?" WHERE "+COLUMN_PAST_ID+"="+statusId:"")+" ORDER BY "+COLUMN_DATE_TIME+" DESC";
            return new SQL(sqlCmd).fetch(Payment.class);
        }else {
            String sqlCmd = "SELECT * FROM " + TABLE_NAME + " " +
                    "JOIN " + Order.TABLE_NAME +            " ON " + Payment.COLUMN_ORDER_ID +      " = " + Order.COLUMN_ID +" "+
                    "JOIN " + ShipmentAddress.TABLE_NAME +  " ON " + Order.COLUMN_SHAD_ID +         " = " + ShipmentAddress.COLUMN_ID + " " +
                    "JOIN " + Customer.TABLE_NAME +         " ON " + Order.COLUMN_CUST_ID +         " = " + Customer.COLUMN_ID  + " " +
                    "JOIN " + OrderItem.TABLE_NAME +        " ON " + OrderItem.COLUMN_ORDER_ID +    " = " + Order.COLUMN_ID + " " +
                    "JOIN " + Product.TABLE_NAME +          " ON " + OrderItem.COLUMN_PROD_ID +     " = " + Product.COLUMN_ID  + " " +
                    "JOIN " + OrderStatus.TABLE_NAME +      " ON " + OrderStatus.COLUMN_ID +        " = " + Order.COLUMN_ORST_ID + " " +
                    "JOIN " + PaymentStatus.TABLE_NAME +    " ON " + PaymentStatus.COLUMN_ID +      " = " + Payment.COLUMN_PAST_ID+ " "+
                    "WHERE ";
            for (int i = 0; i < q.length; i++) {
                sqlCmd += "(" + Customer.COLUMN_PHONE + " LIKE ? OR " +
                        Customer.COLUMN_EMAIL + " LIKE ? OR " +
                        Customer.COLUMN_FNAME + " LIKE ? OR " +
                        Customer.COLUMN_LNAME + " LIKE ? OR " +
                        ShipmentAddress.COLUMN_FNAME + " LIKE ? OR " +
                        ShipmentAddress.COLUMN_LNAME + " LIKE ? OR "+
                        Product.COLUMN_NAME + " LIKE ? OR "+
                        OrderStatus.COLUMN_NAME + " LIKE ? OR "+
                        Order.COLUMN_TOTAL_QTY + " LIKE ? OR "+
                        OrderItem.COLUMN_QTY+ " LIKE ? OR "+
                        PaymentStatus.COLUMN_NAME+ " LIKE ? OR "+
                        "DAY("+Payment.COLUMN_DATE_TIME+ ") = ? OR "+
                        "MONTH("+Payment.COLUMN_DATE_TIME+ ") = ? OR "+
                        "YEAR("+Payment.COLUMN_DATE_TIME+ ") = ? OR "+
                        "DAY("+Order.COLUMN_ORDER_DATE+ ") = ? OR "+
                        "MONTH("+Order.COLUMN_ORDER_DATE+ ") = ? OR "+
                        "YEAR("+Order.COLUMN_ORDER_DATE+ ") = ? OR "+
                        Order.COLUMN_TOTAL_PRICE + " = ? OR "+
                        Payment.COLUMN_AMOUNT+ " = ? OR "+
                        Payment.COLUMN_ID+" = ? OR "+
                        Order.COLUMN_ID+" = ?) ";

                if (i < q.length - 1)
                    sqlCmd += "AND ";
                sql.addParam("%" + q[i] + "%");
                sql.addParam("%" + q[i] + "%");
                sql.addParam("%" + q[i] + "%");
                sql.addParam("%" + q[i] + "%");
                sql.addParam("%" + q[i] + "%");
                sql.addParam("%" + q[i] + "%");
                sql.addParam("%" + q[i] + "%");
                sql.addParam("%" + q[i] + "%");
                sql.addParam("%" + q[i] + "%");
                sql.addParam("%" + q[i] + "%");
                sql.addParam("%" + q[i] + "%");
                sql.addParam(q[i]);
                sql.addParam(q[i]);
                sql.addParam(q[i]);
                sql.addParam(q[i]);
                sql.addParam(q[i]);
                sql.addParam(q[i]);
                sql.addParam(q[i]);
                sql.addParam(q[i]);
                sql.addParam(q[i]);
                sql.addParam(q[i]);

            }
            if(statusId != null) {
                sqlCmd += " AND " + COLUMN_PAST_ID + " = ? ";
                sql.addParam(statusId);
            }
            sqlCmd += "GROUP BY "+Payment.COLUMN_ID;
            sql.setSql(sqlCmd);
            return sql.fetch(Payment.class);
        }
    }

    public static List<Payment> getApprovingPayment(Object... q) throws Exception {
        return findByStatusAndKeyword(1,q);
    }

    public static List<Payment> getApprovedPayment(Object... q) throws Exception {
        return findByStatusAndKeyword(2,q);
    }

    public static List<Payment> getInvalidPayment(Object... q) throws Exception {
        return findByStatusAndKeyword(3,q);
    }

    public static List<Payment> findAll() throws Exception {
        SQL sql = new SQL();
        return sql.select()
                .from(TABLE_NAME)
                .where(COLUMN_PAST_ID, SQL.WhereClause.Operator.EQ, 1)
                .order(COLUMN_DATE_TIME, SQL.OrderClause.Operator.DESC)
                .fetch(Payment.class);
    }
}
