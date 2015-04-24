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
public class PicProduct implements ORM{
    private int prodId;
    private Picture picture;


    public static final String TABLE_NAME = "PIC_PRODUCT";
    public static final Column COLUMN_PROD_ID = ORM.column(TABLE_NAME, "PROD_ID");
    public static final Column COLUMN_PIC_ID = ORM.column(TABLE_NAME, "PIC_ID");
    public static final List<Column> PRIMARY_KEY = ORM.columns(COLUMN_PROD_ID,COLUMN_PIC_ID);
    //public static final List<Column> COLUMN_KEYWORD = ORM.columns(COLUMN_PROD_ID);

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
        this.setPicture((Picture) SQL.findById(Picture.class, rs.getInt(COLUMN_PIC_ID.getColumnName())));
    }

    public static List<Picture> getPictures (List<PicProduct> PicProducts){
        List<Picture> pictures = new ArrayList<>();
        for(PicProduct pp : PicProducts) {
            pictures.add(pp.getPicture());
        }
        return null;
    }

    public static List<PicProduct> findByProdId(int PROD_ID) throws Exception{
        SQL sql = new SQL();
        List<PicProduct> result = sql
                .select()
                .from(PicProduct.TABLE_NAME)
                .where(PicProduct.COLUMN_PROD_ID, SQL.WhereClause.Operator.EQ, PROD_ID)
                 .fetch(PicProduct.class);
        return result;
    }

    public void save() throws Exception{
        SQL sql = new SQL();
        sql
                .insert()
                .into(PicProduct.TABLE_NAME, PicProduct.COLUMN_PROD_ID, PicProduct.COLUMN_PIC_ID)
                .values(this.getProdId(), this.getPicture().getId())
                .exec();
    }


    public static int delete(int PROD_ID, int PIC_ID) throws Exception{
        SQL sql = new SQL();
        int a = sql
                .delete(PicProduct.TABLE_NAME)
                .where(PicProduct.COLUMN_PROD_ID, SQL.WhereClause.Operator.EQ, PROD_ID)
                .where(PicProduct.COLUMN_PIC_ID, SQL.WhereClause.Operator.EQ, PIC_ID)
                .exec();
        return a;
    }

    public static int delete(int PROD_ID) throws Exception{
        SQL sql = new SQL();
        int a = sql
                .delete(PicProduct.TABLE_NAME)
                .where(PicProduct.COLUMN_PROD_ID, SQL.WhereClause.Operator.EQ, PROD_ID)
                .exec();
        return a;
    }

    @Override
    public String toString() {
        return "PicProduct{" +
                "prodId=" + prodId +
                ", picture=" + picture +
                '}';
    }
}