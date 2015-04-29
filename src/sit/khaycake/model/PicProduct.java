package sit.khaycake.model;

import sit.khaycake.database.Column;
import sit.khaycake.database.ORM;
import sit.khaycake.database.SQL;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Falook Glico on 4/26/2015.
 */
public class PicProduct extends Model implements ORM {

    private Product product;
    private Picture picture;

    public static final String TABLE_NAME = "PIC_PRODUCT";
    public static final Column COLUMN_PROD_ID = ORM.column(TABLE_NAME, "PROD_ID");
    public static final Column COLUMN_PIC_ID = ORM.column(TABLE_NAME,"PIC_ID");
    public static final List<Column> PRIMARY_KEY = ORM.columns(COLUMN_PROD_ID,COLUMN_PIC_ID);

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    @Override
    public void orm(ResultSet rs) throws Exception {
        this.setPicture((Picture) SQL.findById(Picture.class,rs.getInt(COLUMN_PIC_ID.getColumnName())));
        //this.setProduct((Product) SQL.findById(Product.class,rs.getInt(COLUMN_PROD_ID.getColumnName())));
    }

    public void save() throws Exception {
        SQL sql = new SQL();
        /*if(isUpdate)
            sql.update(TABLE_NAME)
                    .set(COLUMN_PROD_ID, this.product.getId())
                    .where(COLUMN_PROD_ID, SQL.WhereClause.Operator.EQ, this.product.getId(), SQL.WhereClause.Operator.AND)
                    .where(COLUMN_PIC_ID, SQL.WhereClause.Operator.EQ, this.picture.getId())
                    .exec();
        else*/
            sql.insert().into(TABLE_NAME,COLUMN_PIC_ID,COLUMN_PROD_ID)
                .values(picture.getId(),product.getId())
                .exec();
    }


}
