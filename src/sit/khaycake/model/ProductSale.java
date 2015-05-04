/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.khaycake.model;

import sit.khaycake.database.CanFindByKeyword;
import sit.khaycake.database.Column;
import sit.khaycake.database.ORM;
import sit.khaycake.database.SQL;

import java.sql.ResultSet;
import java.util.List;

/**
 * @author -milk
 */
public class ProductSale implements ORM, CanFindByKeyword, Comparable {

    private int id;
    private int qty;
    private int prodId;
    private double price;

    public static final String TABLE_NAME = "PRODUCT_SALES";
    public static final Column COLUMN_ID = ORM.column(TABLE_NAME, "PRSA_ID");
    public static final Column COLUMN_QTY = ORM.column(TABLE_NAME, "QTY");
    public static final Column COLUMN_PROD_ID = ORM.column(TABLE_NAME, "PROD_ID");
    public static final Column COLUMN_PRICE = ORM.column(TABLE_NAME, "PRICE");
    public static final List<Column> PRIMARY_KEY = ORM.columns(COLUMN_ID);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getProdId() {
        return prodId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }

    public void orm(ResultSet rs) throws Exception {
        this.setId(rs.getInt(COLUMN_ID.getColumnName()));
        this.setQty(rs.getInt(COLUMN_QTY.getColumnName()));
        this.setProdId(rs.getInt(COLUMN_PROD_ID.getColumnName()));//(Product)SQL.findById(Product.class,rs.getInt(COLUMN_PROD_ID.getColumnName())));
        this.setPrice(rs.getDouble(COLUMN_PRICE.getColumnName()));
    }

    public void save() throws Exception {
        SQL sql = new SQL();

        this.id = sql
                .insert()
                .into(TABLE_NAME, COLUMN_PROD_ID, COLUMN_QTY, COLUMN_PRICE)
                .values(this.prodId, this.qty, this.price)
                .exec();
    }

    public void update() throws Exception {
        SQL sql = new SQL();
        sql
                .update(ProductSale.TABLE_NAME)
                .set(ProductSale.COLUMN_PROD_ID, this.getProdId())
                .set(ProductSale.COLUMN_QTY, this.getQty())
                .where(Product.COLUMN_ID, SQL.WhereClause.Operator.EQ, this.getId())
                .exec();
    }

    public static int delete(int PRSA_ID) throws Exception {
        SQL sql = new SQL();
        int a = sql
                .delete(Product.TABLE_NAME)
                .where(Product.COLUMN_ID, SQL.WhereClause.Operator.EQ, PRSA_ID)
                .exec();
        return a;
    }

    @Override
    public String toString() {
        return "ProductSale{" +
                "id=" + id +
                ", qty=" + qty +
                ", prodId=" + prodId +
                ", price=" + price +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof ProductSale) {
            ProductSale p = (ProductSale) o;
            return p.qty - this.qty;
        }
        return 0;
    }
}
