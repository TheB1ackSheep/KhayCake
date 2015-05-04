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
public class PicBank implements ORM, CanFindByKeyword {
    private int bankId;
    private Picture picture;


    public static final String TABLE_NAME = "PIC_PRODUCT";
    public static final Column COLUMN_PIC_ID = ORM.column(TABLE_NAME, "PIC_ID");
    public static final Column COLUMN_BANK_ID = ORM.column(TABLE_NAME, "BANK_ID");
    public static final List<Column> COLUMN_KEYWORD = ORM.columns(COLUMN_BANK_ID);

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int prodId) {
        this.bankId = bankId;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }


    public void orm(ResultSet rs) throws Exception {
        this.setBankId(rs.getInt(COLUMN_BANK_ID.getColumnName()));
        this.setPicture((Picture) SQL.findById(Picture.class, rs.getInt(COLUMN_PIC_ID.getColumnName())));
    }

    public static List<Picture> getPicture(List<PicBank> PicProducts) {
        List<Picture> pictures = new ArrayList<>();
        for (PicBank pp : PicProducts) {
            pictures.add(pp.getPicture());
        }
        return null;
    }

    public void save() throws Exception {
        SQL sql = new SQL();
        sql
                .insert()
                .into(PicBank.TABLE_NAME, PicBank.COLUMN_BANK_ID, PicBank.COLUMN_PIC_ID)
                .values(this.getBankId(), this.getPicture().getId())
                .exec();
    }


    public static int delete(int BANK_ID, int PIC_ID) throws Exception {
        SQL sql = new SQL();
        int a = sql
                .delete(PicBank.TABLE_NAME)
                .where(PicBank.COLUMN_BANK_ID, SQL.WhereClause.Operator.EQ, BANK_ID)
                .where(PicBank.COLUMN_PIC_ID, SQL.WhereClause.Operator.EQ, PIC_ID)
                .exec();
        return a;
    }

    public static int delete(int BANK_ID) throws Exception {
        SQL sql = new SQL();
        int a = sql
                .delete(PicBank.TABLE_NAME)
                .where(PicBank.COLUMN_BANK_ID, SQL.WhereClause.Operator.EQ, BANK_ID)
                .exec();
        return a;
    }

}
