package sit.khaycake.model;

import sit.khaycake.database.Column;
import sit.khaycake.database.ORM;
import sit.khaycake.database.SQL;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Falook Glico on 4/26/2015.
 */
public class Category extends Model implements ORM, Serializable{
    private int id;
    private String name;


    public static final String TABLE_NAME = "CATEGORIES";
    public static final Column COLUMN_ID = ORM.column(TABLE_NAME, "CAT_ID");
    public static final Column COLUMN_NAME = ORM.column(TABLE_NAME, "NAME");
    public static final List<Column> PRIMARY_KEY = ORM.columns(COLUMN_ID);

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

    @Override
    public void orm(ResultSet rs) throws Exception {
        this.setId(rs.getInt(COLUMN_ID.getColumnName()));
        this.setName(rs.getString(COLUMN_NAME.getColumnName()));
    }

    public static List<Product> getProductList(String catId) throws Exception {
        SQL sql = new SQL();
        List<Product> products = sql.select().from(Product.TABLE_NAME)
                .where(Product.COLUMN_CATEGORY, SQL.WhereClause.Operator.EQ, catId)
                .fetch(Product.class);
        return products;
    }
}
