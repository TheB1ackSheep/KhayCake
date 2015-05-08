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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author -milk
 */
public class Product implements ORM, CanFindByKeyword {

    private int id;
    private String name;
    private String detail;
    private double cost;
    private Category category;
    private double price;
    private Unit unit;

    public static final String TABLE_NAME = "PRODUCTS";
    public static final Column COLUMN_ID = ORM.column(TABLE_NAME, "PROD_ID");
    public static final Column COLUMN_NAME = ORM.column(TABLE_NAME, "NAME");
    public static final Column COLUMN_DETAIL = ORM.column(TABLE_NAME, "DETAIL");
    public static final Column COLUMN_COST = ORM.column(TABLE_NAME, "COST");
    public static final Column COLUMN_CAT_ID = ORM.column(TABLE_NAME, "CAT_ID");
    public static final Column COLUMN_PRICE = ORM.column(TABLE_NAME, "PRICE");
    public static final Column COLUMN_UNIT_ID = ORM.column(TABLE_NAME, "UNIT_ID");
    public static final List<Column> PRIMARY_KEY = ORM.columns(COLUMN_ID);
    public static final List<Column> COLUMN_KEYWORD = ORM.columns(COLUMN_NAME, COLUMN_DETAIL);

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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Unit getUnit() {
        return unit;
    }

    public void orm(ResultSet rs) throws Exception {
        this.setId(rs.getInt(COLUMN_ID.getColumnName()));
        this.setName(rs.getString(COLUMN_NAME.getColumnName()));
        this.setDetail(rs.getString(COLUMN_DETAIL.getColumnName()));
        this.setCost(rs.getDouble(COLUMN_COST.getColumnName()));
        this.setPrice(rs.getDouble(COLUMN_PRICE.getColumnName()));
        this.setCategory((Category)
                SQL.findById(Category.class, rs.getInt(COLUMN_CAT_ID.getColumnName())));
        this.setPrice(rs.getDouble(COLUMN_PRICE.getColumnName()));
        this.setUnit((Unit) SQL.findById(Unit.class, rs.getInt(COLUMN_UNIT_ID.getColumnName())));

    }

    public void deletePictures() throws Exception {
        SQL sql = new SQL();
        sql.delete(PicProduct.TABLE_NAME)
                .where(PicProduct.COLUMN_PROD_ID, SQL.WhereClause.Operator.EQ, this.id)
                .exec();
    }

    public void deleteSales() throws Exception {
        SQL sql = new SQL();
        sql.delete(ProductSale.TABLE_NAME)
                .where(ProductSale.COLUMN_PROD_ID, SQL.WhereClause.Operator.EQ, this.id)
                .exec();
    }

    public static List<Product> findByCategory(Object CAT_ID) throws Exception {
        SQL sql = new SQL();
        List<Product> result = sql
                .select()
                .from(Product.TABLE_NAME)
                .where(Product.COLUMN_CAT_ID, SQL.WhereClause.Operator.EQ, CAT_ID)
                .fetch(Product.class);
        return result;

    }

    public List<Picture> getPictures() throws Exception {
        SQL sql = new SQL();
        List<Picture> result = sql.select()
                .from(Picture.TABLE_NAME)
                .join(PicProduct.TABLE_NAME)
                .on(Picture.COLUMN_ID, PicProduct.COLUMN_PIC_ID)
                .where(PicProduct.COLUMN_PROD_ID, SQL.WhereClause.Operator.EQ, this.id)
                .fetch(Picture.class);
        return result;
    }

    public List<ProductSale> getSales() throws Exception {
        List<ProductSale> sales = new ArrayList<>();
        String sql = "SELECT 1 \"QTY\" , PRODUCTS.PRICE FROM PRODUCTS WHERE PRODUCTS.PROD_ID = ? UNION SELECT PRODUCT_SALES.QTY, PRODUCT_SALES.PRICE FROM PRODUCT_SALES WHERE PRODUCT_SALES.PROD_ID = ?";
        try (Connection conn = SQL.getConnection()) {
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setInt(1, this.id);
            prep.setInt(2, this.id);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                ProductSale p = new ProductSale();
                p.setPrice(rs.getDouble("PRICE"));
                p.setQty(rs.getInt("QTY"));
                sales.add(p);
            }
        }
        return sales;
    }

    public List<ProductSale> getPrices() throws Exception {
        SQL sql = new SQL();
        List<ProductSale> result = sql.select()
                .from(ProductSale.TABLE_NAME)
                .where(ProductSale.COLUMN_PROD_ID, SQL.WhereClause.Operator.EQ, this.id)
                .fetch(ProductSale.class);
        return result;
    }

    public static List<Product> find(Object... keywords) throws Exception {
        SQL sql = new SQL();
        String sqlCmd = "SELECT * FROM "+TABLE_NAME+" WHERE ";
        for(int i=0;i<keywords.length;i++){
            if(i<keywords.length-1) {
                sqlCmd += "("+COLUMN_NAME+" LIKE ? OR "+COLUMN_DETAIL+" LIKE ?) AND ";
                sql.addParam("%"+keywords[i]+"%");
                sql.addParam("%"+keywords[i]+"%");
            }
            else {
                sqlCmd += "("+COLUMN_NAME+" LIKE ? OR "+COLUMN_DETAIL+" LIKE ?)";
                sql.addParam("%"+keywords[i]+"%");
                sql.addParam("%"+keywords[i]+"%");
            }
        }
        sql.setSql(sqlCmd);
        return sql.fetch(Product.class);
    }

    public static List<Product> find(Object cat_id,Object... keywords) throws Exception {
        SQL sql = new SQL();
        String sqlCmd = "SELECT * FROM "+TABLE_NAME+" WHERE CAT_ID = ? AND (";
        sql.addParam(cat_id);
        for(int i=0;i<keywords.length;i++){
            if(i<keywords.length-1) {
                sqlCmd += "("+COLUMN_NAME+" LIKE ? OR "+COLUMN_DETAIL+" LIKE ?) AND ";
                sql.addParam("%"+keywords[i]+"%");
                sql.addParam("%"+keywords[i]+"%");
            }
            else {
                sqlCmd += "("+COLUMN_NAME+" LIKE ? OR "+COLUMN_DETAIL+" LIKE ?)";
                sql.addParam("%"+keywords[i]+"%");
                sql.addParam("%"+keywords[i]+"%");
            }
        }
        sqlCmd += ")";
        sql.setSql(sqlCmd);
        return sql.fetch(Product.class);
    }


    @Override
    public boolean equals(Object object) {
        if (object != null && object instanceof Product) {
            return this.getId() == ((Product) object).getId();
        }

        return false;
    }


    public void save() throws Exception {
        SQL sql = new SQL();
        int id = sql
                .insert()
                .into(Product.TABLE_NAME, Product.COLUMN_NAME, Product.COLUMN_DETAIL, Product.COLUMN_PRICE
                        , Product.COLUMN_COST, Product.COLUMN_CAT_ID, Product.COLUMN_UNIT_ID)
                .values(this.getName(), this.getDetail(), this.getPrice(), this.getCost(),
                        this.getCategory().getId(), this.getUnit().getId())
                .exec();
        this.setId(id);
    }

    public void update() throws Exception {
        SQL sql = new SQL();
        sql
                .update(Product.TABLE_NAME)
                .set(Product.COLUMN_NAME, this.getName())
                .set(Product.COLUMN_DETAIL, this.getDetail())
                .set(Product.COLUMN_PRICE, this.getPrice())
                .set(Product.COLUMN_COST, this.getCost())
                .set(Product.COLUMN_CAT_ID, this.getCategory().getId())
                .set(Product.COLUMN_UNIT_ID, this.getUnit().getId())
                .where(Product.COLUMN_ID, SQL.WhereClause.Operator.EQ, this.getId())
                .exec();
    }

    public int delete() throws Exception {
        SQL sql = new SQL();
        int a = sql
                .delete(Product.TABLE_NAME)
                .where(Product.COLUMN_ID, SQL.WhereClause.Operator.EQ, this.id)
                .exec();
        return a;
    }
}
