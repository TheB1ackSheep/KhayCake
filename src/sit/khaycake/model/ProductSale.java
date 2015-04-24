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
    private double price;
    private int prod;
    private int unitId;
    
    public static final String TABLE_NAME = "PRODUCT_SALES";
    public static final Column COLUMN_ID = ORM.column(TABLE_NAME, "PRSA_ID");
    public static final Column COLUMN_QTY = ORM.column(TABLE_NAME, "QTY");
    public static final Column COLUMN_PRICE = ORM.column(TABLE_NAME, "PRICE");
    public static final Column COLUMN_PROD_ID = ORM.column(TABLE_NAME, "PROD_ID");
    public static final Column COLUMN_UNIT_ID = ORM.column(TABLE_NAME, "UNIT_ID");
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


    public double getPrice() {
        return price;
    }

    public void setPrice(double priceN) {
        this.price = priceN;
    }

    public int getProd() {
        return prod;
    }

    public void setProd(int prod) {
        this.prod = prod;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    
     public void orm(ResultSet rs) throws Exception {
        
        this.setId(rs.getInt(COLUMN_ID.getColumnName()));
        this.setQty(rs.getInt(COLUMN_QTY.getColumnName()));
        this.setPrice(rs.getDouble(COLUMN_PRICE.getColumnName()));
        this.setProd(rs.getInt(COLUMN_PROD_ID.getColumnName()));//(Product)SQL.findById(Product.class,rs.getInt(COLUMN_PROD_ID.getColumnName())));
         this.setUnitId(rs.getInt(COLUMN_UNIT_ID.getColumnName()));
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

    public static double getPrice(List<ProductSale> ps) throws Exception{
        return ps.get(0).getPrice();
    }

    /*public void save() throws Exception {
        SQL sql = new SQL();
        int id = sql
                .insert()
                .into(ProductSale.TABLE_NAME, ProductSale.COLUMN_PRICE_N, ProductSale.COLUMN_PRICE_V, ProductSale.COLUMN_PROD_ID,
                        ProductSale.COLUMN_QTY, ProductSale.COLUMN_UNIT_ID)
                .values(this.getPriceN(), this.getPriceV(), productSale.getProdId(), productSale.getQty(), productSale.getUnitId())
                .exec();
        this.setId(id);
    }

    public void update() throws Exception{
        SQL sql = new SQL();
        sql
                .update(Product.TABLE_NAME)
                .set(Product.COLUMN_NAME, this.getName())
                .set(Product.COLUMN_DETAIL, this.getDetail())
                .set(Product.COLUMN_COST, this.getCost())
                .set(Product.COLUMN_CAT_ID, this.getCategory().getId())
                .where(Product.COLUMN_ID, SQL.WhereClause.Operator.EQ, this.getId())
                .exec();
    }

    public static int delete(int PROD_ID) throws Exception{
        SQL sql = new SQL();
        int a = sql
                .delete(Product.TABLE_NAME)
                .where(Product.COLUMN_ID, SQL.WhereClause.Operator.EQ, PROD_ID)
                .exec();
        return a;
    }*/
}
