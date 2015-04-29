/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.khaycake.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sit.khaycake.database.Column;
import sit.khaycake.database.ORM;
import sit.khaycake.database.SQL;

/**
 *
 * @author -milk
 */
public class ProductSale extends Model implements ORM {
    
    private int id;
    private int qty;
    private double price;
    private transient int prodId;
    
    public static final String TABLE_NAME = "PRODUCT_SALES";
    public static final Column COLUMN_ID = ORM.column(TABLE_NAME, "PRSA_ID");
    public static final Column COLUMN_QTY = ORM.column(TABLE_NAME, "QTY");
    public static final Column COLUMN_PRICE = ORM.column(TABLE_NAME, "PRICE");
    public static final Column COLUMN_PROD_ID = ORM.column(TABLE_NAME, "PROD_ID");
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

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void orm(ResultSet rs) throws Exception {
        
        this.setId(rs.getInt(COLUMN_ID.getColumnName()));
        this.setQty(rs.getInt(COLUMN_QTY.getColumnName()));
        this.setPrice(rs.getDouble(COLUMN_PRICE.getColumnName()));
        this.setProdId(rs.getInt(COLUMN_PROD_ID.getColumnName()));
        
    }

    public void save() throws Exception {
        SQL sql = new SQL();
        /*if(isUpdate)
            sql.update(TABLE_NAME)
                    .set(COLUMN_PRICE, this.price)
                    .set(COLUMN_QTY, this.qty)
                    .set(COLUMN_PROD_ID, this.prodId)
                    .where(COLUMN_ID, SQL.WhereClause.Operator.EQ, this.id)
                    .exec();
        else*/
            this.id = sql.insert().into(TABLE_NAME,COLUMN_PRICE,COLUMN_QTY,COLUMN_PROD_ID)
                .values(this.price,this.qty,this.prodId)
                .exec();
    }


    public static List<ProductSale> findByProduct(int prodId) throws Exception {
        SQL sql = new SQL();
        List<ProductSale> productSales = sql.select()
                .from(ProductSale.TABLE_NAME)
                .where(ProductSale.COLUMN_PROD_ID, SQL.WhereClause.Operator.EQ, prodId)
                .fetch(ProductSale.class);

        List<ProductSale> sales = null;
        if(productSales!=null)
            for(ProductSale p : productSales)
            {
                if(sales == null)
                    sales = new ArrayList<>();
                sales.add(p);
            }
        return sales;
    }


}
