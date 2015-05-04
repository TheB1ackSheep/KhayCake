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
public class Invoice implements ORM {

    private int id;
    private Date date;
    private Double subTotal;
    private Double vat;
    private Double qrandTotal;
    private String qrandTotalText;
    private Payment payment;
    private MerchantInfo merchantInfo;

    public static final String TABLE_NAME = "INVOICES";
    public static final Column COLUMN_ID = ORM.column(TABLE_NAME, "INVO_ID");
    public static final Column COLUMN_DATE = ORM.column(TABLE_NAME, "DATE");
    public static final Column COLUMN_SUB_TOTAL = ORM.column(TABLE_NAME, "SUB_TOTAL");
    public static final Column COLUMN_VAT = ORM.column(TABLE_NAME, "VAT");
    public static final Column COLUMN_QRAND_TOTAL = ORM.column(TABLE_NAME, "QRAND_TOTAL");
    public static final Column COLUMN_QRAND_TOTAL_TEXT = ORM.column(TABLE_NAME, "QRAND_TOTAL_TEXT");
    public static final Column COLUMN_PATM_ID = ORM.column(TABLE_NAME, "PATM_ID");
    public static final Column COLUMN_MEIF_ID = ORM.column(TABLE_NAME, "MEIF_ID");
    public static final List<Column> PRIMARY_KEY = ORM.columns(COLUMN_ID);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getVat() {
        return vat;
    }

    public void setVat(Double vat) {
        this.vat = vat;
    }

    public Double getQrandTotal() {
        return qrandTotal;
    }

    public void setQrandTotal(Double qrandTotal) {
        this.qrandTotal = qrandTotal;
    }

    public String getQrandTotalText() {
        return qrandTotalText;
    }

    public void setQrandTotalText(String qrandTotalText) {
        this.qrandTotalText = qrandTotalText;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public MerchantInfo getMerchantInfo() {
        return merchantInfo;
    }

    public void setMerchantInfo(MerchantInfo merchantInfo) {
        this.merchantInfo = merchantInfo;
    }

    public void orm(ResultSet rs) throws Exception {

        this.setId(rs.getInt(COLUMN_ID.getColumnName()));
        this.setDate(rs.getDate(COLUMN_DATE.getColumnName()));
        this.setSubTotal(rs.getDouble(COLUMN_SUB_TOTAL.getColumnName()));
        this.setVat(rs.getDouble(COLUMN_VAT.getColumnName()));
        this.setQrandTotal(rs.getDouble(COLUMN_QRAND_TOTAL.getColumnName()));
        this.setQrandTotalText(rs.getString(COLUMN_QRAND_TOTAL_TEXT.getColumnName()));
        this.setPayment((Payment) SQL.findById(Payment.class, rs.getInt(COLUMN_PATM_ID.getColumnName())));
        this.setMerchantInfo((MerchantInfo) SQL.findById(MerchantInfo.class, rs.getInt(COLUMN_MEIF_ID.getColumnName())));

    }

    /*public void save() throws Exception {
        SQL sql = new SQL();
        int id = sql
                .insert()
                .into(Invoice.TABLE_NAME, Invoice.COLUMN_DATE, Invoice.COLUMN_MEIF_ID, Invoice.COLUMN_PATM_ID,
                        Invoice.COLUMN_QRAND_TOTAL, Invoice.COLUMN_SUB_TOTAL, Invoice.COLUMN_VAT, Invoice.COLUMN_QRAND_TOTAL_TEXT)
                .values(this.getDate(), this.getMerchantInfo().getId(), this.getPayment().getId(), this.getQrandTotal(),
                        this.getSubTotal(), this.getVat(), this.getQrandTotalText())
                .exec();
        this.setId(id);
    }

    public void update() throws Exception{
        SQL sql = new SQL();
            sql
                    .update(Invoice.TABLE_NAME)
                    .set(Invoice.COLUMN_DATE, this.getDate())
                    .set(Invoice.COLUMN_MEIF_ID, this.getMerchantInfo().getId())
                    .set(Invoice.COLUMN_PATM_ID, this.getPayment().getId())
                    .set(Invoice.COLUMN_QRAND_TOTAL, this.getQrandTotal())
                    .set(Invoice.COLUMN_SUB_TOTAL, this.getSubTotal())
                    .set(Invoice.COLUMN_VAT, this.getVat())
                    .set(Invoice.COLUMN_QRAND_TOTAL_TEXT, this.getQrandTotalText())
                    .where(Invoice.COLUMN_ID, SQL.WhereClause.Operator.EQ, this.getId())
                    .exec();
    }

    public static int delete(int INVO_ID) throws Exception{
        SQL sql = new SQL();
        int a = sql
                .delete(Invoice.TABLE_NAME)
                .where(Invoice.COLUMN_ID, SQL.WhereClause.Operator.EQ, INVO_ID)
                .exec();
        return a;
    }*/

}
