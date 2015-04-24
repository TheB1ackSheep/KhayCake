package sit.khaycake.model;

import sit.khaycake.database.Column;
import sit.khaycake.database.ORM;
import sit.khaycake.database.SQL;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Pasuth on 22/4/2558.
 */
public class Picture implements ORM {
    private int id;
    private String path;

    public static final String TABLE_NAME = "PICTURE";
    public static final Column COLUMN_ID = ORM.column(TABLE_NAME, "PIC_ID");
    public static final Column COLUMN_PATH = ORM.column(TABLE_NAME, "PATH");
    public static final List<Column> PRIMARY_KEY = ORM.columns(COLUMN_ID);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void orm(ResultSet rs) throws Exception {
        this.setId(rs.getInt(COLUMN_ID.getColumnName()));
        this.setPath(rs.getString(COLUMN_PATH.getColumnName()));
    }

    public void save() throws Exception {
        SQL sql = new SQL();
        int id = sql
                .insert()
                .into(Picture.TABLE_NAME, Picture.COLUMN_ID, Picture.COLUMN_PATH)
                .values(this.getId(), this.getPath())
                .exec();
        this.setId(id);
    }

    public void update() throws Exception{
        SQL sql = new SQL();
        sql
                .update(Picture.TABLE_NAME)
                .set(Picture.COLUMN_ID, this.getId())
                .set(Picture.COLUMN_PATH, this.getPath())
                .where(Picture.COLUMN_ID, SQL.WhereClause.Operator.EQ, this.getId())
                .exec();
    }

    public static int delete(int PIC_ID) throws Exception{
        SQL sql = new SQL();
        int a = sql
                .delete(Picture.TABLE_NAME)
                .where(Picture.COLUMN_ID, SQL.WhereClause.Operator.EQ, PIC_ID)
                .exec();
        return a;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "path='" + path + '\'' +
                '}';
    }
}
