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
public class Payment implements ORM {

    public enum Status {

        COMPLETED(1),
        CANCELED(2),
        PENDING(3),
        FAILED(4);

        private PaymentStatus paymentStatus;
        private int id;
        private String name;

        Status(int id) {
            try {
                this.id = id;
                paymentStatus = (PaymentStatus) SQL.findById(PaymentStatus.class, id);
                this.name = (paymentStatus == null) ? null : paymentStatus.getName();
            } catch (Exception e) {
                //must be caught or dec;ared to be thrown
            }
        }

        public static Status getStatus(int id) {
            switch (id) {
                case 1:
                    return COMPLETED;
                case 2:
                    return CANCELED;
                case 3:
                    return PENDING;
                case 4:
                    return FAILED;
                default:
                    return PENDING;
            }
        }

        public int getId() {
            return this.id;
        }
    }

    private int id;
    private Order order;
    private BankAccount baac;
    private Status status;
    private Date dateTime;
    private double amount;

    public static final String TABLE_NAME = "PAYMENTS";
    public static final Column COLUMN_ID = ORM.column(TABLE_NAME, "PATM_ID");
    public static final Column COLUMN_ORDER_ID = ORM.column(TABLE_NAME, "ORDER_ID");
    public static final Column COLUMN_BAAC_ID = ORM.column(TABLE_NAME, "BAAC_ID");
    public static final Column COLUMN_PAST_ID = ORM.column(TABLE_NAME, "PAST_ID");
    public static final Column COLUMN_DATE_TIME = ORM.column(TABLE_NAME, "DATE_TIME");
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

    public BankAccount getBaac() {
        return baac;
    }

    public void setBaac(BankAccount baac) {
        this.baac = baac;
    }

    public Status getStatus() {
        return status;
    }

    public void setPast(Status status) {
        this.status = status;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
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
        this.setOrder((Order) SQL.findById(Order.class, rs.getInt(COLUMN_ORDER_ID.getColumnName())));
        this.setBaac((BankAccount) SQL.findById(BankAccount.class, rs.getInt(COLUMN_BAAC_ID.getColumnName())));
        this.setPast(Status.getStatus(rs.getInt(COLUMN_PAST_ID.getColumnName())));
        this.setDateTime(rs.getDate(COLUMN_DATE_TIME.getColumnName()));
        this.setAmount(rs.getDouble(COLUMN_AMOUNT.getColumnName()));

    }

    public void save() throws Exception {
        SQL sql = new SQL();
        int id = sql
                .insert()
                .into(Payment.TABLE_NAME, Payment.COLUMN_ORDER_ID, Payment.COLUMN_AMOUNT, Payment.COLUMN_BAAC_ID,
                        Payment.COLUMN_DATE_TIME, Payment.COLUMN_PAST_ID)
                .values(this.getOrder(), this.getAmount(), this.getBaac(), this.getDateTime(), this.getStatus().getId())
                .exec();
        this.setId(id);
    }

    public void update() throws Exception {
        SQL sql = new SQL();
        sql
                .update(Payment.TABLE_NAME)
                .set(Payment.COLUMN_ORDER_ID, this.getOrder())
                .set(Payment.COLUMN_AMOUNT, this.getAmount())
                .set(Payment.COLUMN_BAAC_ID, this.getBaac())
                .set(Payment.COLUMN_DATE_TIME, this.getDateTime())
                .set(Payment.COLUMN_PAST_ID, this.getStatus().getId())
                .where(Payment.COLUMN_ID, SQL.WhereClause.Operator.EQ, this.getId())
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
}
