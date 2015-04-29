/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.khaycake.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import sit.khaycake.database.CanFindByKeyword;
import sit.khaycake.database.Column;
import sit.khaycake.database.ORM;
import sit.khaycake.database.SQL;

/**
 *
 * @author -milk
 */
public class ProductSale implements ORM,CanFindByKeyword {
    
    private int id;
    private int qty;
    private int prodId;
    
    public static final String TABLE_NAME = "PRODUCT_SALES";
    public static final Column COLUMN_ID = ORM.column(TABLE_NAME, "PRSA_ID");
    public static final Column COLUMN_QTY = ORM.column(TABLE_NAME, "QTY");
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

    
     public void orm(ResultSet rs) throws Exception {
        
        this.setId(rs.getInt(COLUMN_ID.getColumnName()));
        this.setQty(rs.getInt(COLUMN_QTY.getColumnName()));
        this.setProdId(rs.getInt(COLUMN_PROD_ID.getColumnName()));//(Product)SQL.findById(Product.class,rs.getInt(COLUMN_PROD_ID.getColumnName())));
     }

    public static List<ProductSale> findByProdId(int PROD_ID) throws Exception{
        SQL sql = new SQL();
        List<ProductSale> result = sql
                .select()
                .from(ProductSale.TABLE_NAME)
                .where(ProductSale.COLUMN_PROD_ID, SQL.WhereClause.Operator.EQ, PROD_ID)
                .fetch(ProductSale.class);
        return result;
    }


    public void save() throws Exception {
        SQL sql = new SQL();
        int id = sql
                .insert()
                .into(ProductSale.TABLE_NAME, ProductSale.COLUMN_PROD_ID, ProductSale.COLUMN_QTY)
                .values(this.getProdId(), this.getQty())
                .exec();
        this.setId(id);
    }

    public void update() throws Exception{
        SQL sql = new SQL();
        sql
                .update(ProductSale.TABLE_NAME)
                .set(ProductSale.COLUMN_PROD_ID, this.getProdId())
                .set(ProductSale.COLUMN_QTY, this.getQty())
                .where(Product.COLUMN_ID, SQL.WhereClause.Operator.EQ, this.getId())
                .exec();
    }

    public static int delete(int PRSA_ID) throws Exception{
        SQL sql = new SQL();
        int a = sql
                .delete(Product.TABLE_NAME)
                .where(Product.COLUMN_ID, SQL.WhereClause.Operator.EQ, PRSA_ID)
                .exec();
        return a;
    }
}
