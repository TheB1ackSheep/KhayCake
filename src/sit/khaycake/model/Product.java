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

import sit.khaycake.database.CanFindByKeyword;
import sit.khaycake.database.Column;
import sit.khaycake.database.ORM;
import sit.khaycake.database.SQL;

/**
 *
 * @author -milk
 */
public class Product extends Model implements ORM, CanFindByKeyword {
    
    private int id;
    private String name;
    private String detail;
    private double cost;
    private double price;
    private Unit unit;
    private Category category;

    private List<Picture> pictures;
    private List<ProductSale> sales;
    
    public static final String TABLE_NAME = "PRODUCTS";
    public static final Column COLUMN_ID = ORM.column(TABLE_NAME, "PROD_ID");
    public static final Column COLUMN_NAME = ORM.column(TABLE_NAME, "NAME");
    public static final Column COLUMN_DETAIL = ORM.column(TABLE_NAME, "DETAIL");
    public static final Column COLUMN_COST = ORM.column(TABLE_NAME, "COST");
    public static final Column COLUMN_PRICE = ORM.column(TABLE_NAME, "PRICE");
    public static final Column COLUMN_UNIT = ORM.column(TABLE_NAME, "UNIT_ID");
    public static final Column COLUMN_CATEGORY = ORM.column(TABLE_NAME, "CAT_ID");
    public static final List<Column> PRIMARY_KEY = ORM.columns(COLUMN_ID);
    public static final List<Column> CANDIDATE_KEY = ORM.columns(COLUMN_NAME,COLUMN_DETAIL,COLUMN_COST,COLUMN_PRICE);

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

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public void addPicture(Picture picture){
        if(this.pictures == null)
            this.pictures = new ArrayList<>();
        this.pictures.add(picture);
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<ProductSale> getSales() {
        return sales;
    }

    public void setSales(List<ProductSale> sales) {
        this.sales = sales;
    }

    public void addSales(ProductSale sale){
        if(this.sales == null)
            this.sales = new ArrayList<>();
        this.sales.add(sale);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public void orm(ResultSet rs) throws Exception {
        
        this.setId(rs.getInt(COLUMN_ID.getColumnName()));
        this.setName(rs.getString(COLUMN_NAME.getColumnName()));
        this.setDetail(rs.getString(COLUMN_DETAIL.getColumnName()));
        this.setCost(rs.getDouble(COLUMN_COST.getColumnName()));
        this.setPrice(rs.getDouble(COLUMN_PRICE.getColumnName()));
        this.setUnit((Unit)SQL.findById(Unit.class,rs.getInt(COLUMN_UNIT.getColumnName())));
        this.setCategory((Category) SQL.findById(Category.class, rs.getInt(COLUMN_CATEGORY.getColumnName())));
        this.setPictures(Picture.findByProduct(this.id));
        this.setSales(ProductSale.findByProduct(this.id));
        
    }

    public void save(boolean isUpdate) throws Exception {
        SQL sql = new SQL();
        if(isUpdate)
            sql.update(TABLE_NAME)
                    .set(COLUMN_NAME, this.name)
                    .set(COLUMN_DETAIL, this.detail)
                    .set(COLUMN_COST, this.cost)
                    .set(COLUMN_PRICE, this.price)
                    .set(COLUMN_CATEGORY, this.category.getId())
                    .set(COLUMN_UNIT, this.unit.getId())
                    .where(COLUMN_ID, SQL.WhereClause.Operator.EQ, this.id)
                    .exec();
        else
            this.id = sql.insert().into(TABLE_NAME, COLUMN_NAME, COLUMN_DETAIL, COLUMN_COST, COLUMN_PRICE, COLUMN_CATEGORY, COLUMN_UNIT)
                    .values(this.name, this.detail, this.cost, this.price, this.category.getId(), this.unit.getId())
                    .exec();

        if(this.pictures != null){
            sql.clear();
            sql.delete(PicProduct.TABLE_NAME)
                    .where(PicProduct.COLUMN_PROD_ID, SQL.WhereClause.Operator.EQ, this.id)
                    .exec();
            for(Picture pic : this.pictures){
                PicProduct p = new PicProduct();
                p.setProduct(this);
                p.setPicture(pic);
                p.save();
            }
        }

        if(this.sales != null){
            sql.clear();
            sql.delete(ProductSale.TABLE_NAME)
                    .where(ProductSale.COLUMN_PROD_ID, SQL.WhereClause.Operator.EQ, this.id)
                    .exec();
            for(ProductSale p : this.sales){
                p.setProdId(this.id);
                p.save();
            }
        }

    }



    public void delete() throws Exception {
        SQL sql = new SQL();
        sql.delete(TABLE_NAME)
                .where(COLUMN_ID, SQL.WhereClause.Operator.EQ, this.id)
                .exec();
    }

}
