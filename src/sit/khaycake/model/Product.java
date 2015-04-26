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
public class Product implements ORM, CanFindByKeyword{
    
    private int id;
    private String name;
    private String detail;
    private double cost;
    private Category category;
    private double price;
    private List<Picture> picture;

    public static final String TABLE_NAME = "PRODUCTS";
    public static final Column COLUMN_ID = ORM.column(TABLE_NAME, "PROD_ID");
    public static final Column COLUMN_NAME = ORM.column(TABLE_NAME, "NAME");
    public static final Column COLUMN_DETAIL = ORM.column(TABLE_NAME, "DETAIL");
    public static final Column COLUMN_COST = ORM.column(TABLE_NAME, "COST");
    public static final Column COLUMN_CAT_ID = ORM.column(TABLE_NAME, "CAT_ID");
    public static final List<Column> PRIMARY_KEY = ORM.columns(COLUMN_ID);
    public static final List<Column> COLUMN_KEYWORD = ORM.columns(COLUMN_NAME);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Category getCategory() { return category;}

    public void setCategory(Category category) { this.category = category;}

    public double setPrice() { return price;}

    public void setPrice(double price) { this.price = price; }

    public List<Picture> getPicture(){ return picture; }

    public void setPicture(List<Picture> picture) { this.picture = picture; }
    
     public void orm(ResultSet rs) throws Exception {
         System.out.println(rs.getInt(COLUMN_ID.getColumnName()));
        this.setId(rs.getInt(COLUMN_ID.getColumnName()));
        this.setName(rs.getString(COLUMN_NAME.getColumnName()));
        this.setDetail(rs.getString(COLUMN_DETAIL.getColumnName()));
        this.setCost(rs.getDouble(COLUMN_COST.getColumnName()));
        this.setCategory((Category)
                SQL.findById(Category.class, rs.getInt(COLUMN_CAT_ID.getColumnName())));
        this.setPrice(ProductSale.getPrice(ProductSale.findByProdId(rs.getInt(COLUMN_ID.getColumnName()))));
        this.setPicture(PicProduct.getPictures(PicProduct.findByProdId(rs.getInt(COLUMN_ID.getColumnName()))));
    }

    public static List<Product> findByCategory(int CAT_ID) throws Exception{
        SQL sql = new SQL();
        List<Product> result = sql
                .select()
                .from(Product.TABLE_NAME)
                .where(Product.COLUMN_CAT_ID, SQL.WhereClause.Operator.EQ, CAT_ID)
                .fetch(Product.class);
        return result;

    }


    @Override
    public boolean equals(Object object)
    {
        boolean result = false;

        if (object != null && object instanceof Product)
        {
            result = this.getId() == ((Product) object).getId();
        }

        return result;
    }



    /*public void save() throws Exception {
        SQL sql = new SQL();
        int id = sql
                .insert()
                .into(Product.TABLE_NAME, Product.COLUMN_NAME, Product.COLUMN_DETAIL, Product.COLUMN_COST,
                        Product.COLUMN_CAT_ID)
                .values(this.getName(), this.getDetail(), this.getCost(), this.getCategory().getId())
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
