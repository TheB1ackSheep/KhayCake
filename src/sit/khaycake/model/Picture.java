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
    private String filename;

    public static final String TABLE_NAME = "PICTURES";
    public static final Column COLUMN_ID = ORM.column(TABLE_NAME, "PIC_ID");
    public static final Column COLUMN_FILENAME = ORM.column(TABLE_NAME, "FILENAME");
    public static final List<Column> PRIMARY_KEY = ORM.columns(COLUMN_ID);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void orm(ResultSet rs) throws Exception {
        this.setId(rs.getInt(COLUMN_ID.getColumnName()));
        this.setFilename(rs.getString(COLUMN_FILENAME.getColumnName()));
    }

    public void save() throws Exception {
        SQL sql = new SQL();
        int id = sql
                .insert()
                .into(Picture.TABLE_NAME, Picture.COLUMN_FILENAME)
                .values(this.getFilename())
                .exec();
        this.setId(id);
    }

    public void update() throws Exception{
        SQL sql = new SQL();
        sql
                .update(Picture.TABLE_NAME)
                .set(Picture.COLUMN_ID, this.getId())
                .set(Picture.COLUMN_FILENAME, this.getFilename())
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
                "id=" + id +
                ", filename='" + filename + '\'' +
                '}';
    }
}
