package sit.khaycake.model;

import sit.khaycake.database.CanFindByKeyword;
import sit.khaycake.database.Column;
import sit.khaycake.database.ORM;
import sit.khaycake.database.SQL;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pasuth on 22/4/2558.
 */
public class PicProduct implements ORM, CanFindByKeyword {
    private int prodId;
    private Picture picture;


    public static final String TABLE_NAME = "PIC_PRODUCT";
    public static final Column COLUMN_PROD_ID = ORM.column(TABLE_NAME, "PROD_ID");
    public static final Column COLUMN_PIC_ID = ORM.column(TABLE_NAME, "PIC_ID");
    public static final List<Column> COLUMN_KEYWORD = ORM.columns(COLUMN_PROD_ID);

    public int getProdId() {
        return prodId;
    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }


    public void orm(ResultSet rs) throws Exception {
        this.setProdId(rs.getInt(COLUMN_PROD_ID.getColumnName()));
        this.setPicture((Picture) SQL.findById(Address.class, rs.getInt(COLUMN_PROD_ID.getColumnName())));
    }

    public static List<Picture> getPicture (List<PicProduct> PicProducts){
        List<Picture> pictures = new ArrayList<>();
        for(PicProduct pp : PicProducts) {
            pictures.add(pp.getPicture());
        }
        return null;
    }

    public void save() throws Exception{
        SQL sql = new SQL();
        sql
                .insert()
                .into(PicProduct.TABLE_NAME, PicProduct.COLUMN_PROD_ID, PicProduct.COLUMN_PIC_ID)
                .values(this.getProdId(), this.getPicture().getId())
                .exec();
    }


    public static int delete(int CUST_ID, int ADDR_ID) throws Exception{
        SQL sql = new SQL();
        int a = sql
                .delete(PicProduct.TABLE_NAME)
                .where(PicProduct.COLUMN_PROD_ID, SQL.WhereClause.Operator.EQ, CUST_ID)
                .where(PicProduct.COLUMN_PIC_ID, SQL.WhereClause.Operator.EQ, ADDR_ID)
                .exec();
        return a;
    }

    public static int delete(int CUST_ID) throws Exception{
        SQL sql = new SQL();
        int a = sql
                .delete(PicProduct.TABLE_NAME)
                .where(PicProduct.COLUMN_PROD_ID, SQL.WhereClause.Operator.EQ, CUST_ID)
                .exec();
        return a;
    }

}
