package sit.khaycake.model;

import sit.khaycake.database.CanFindByKeyword;
import sit.khaycake.database.Column;
import sit.khaycake.database.ORM;
import sit.khaycake.database.SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Falook Glico on 5/5/2015.
 */
public class Tumbon implements ORM, CanFindByKeyword {
    private int id;
    private String name;
    private Amphur amphur;
    private String zipcode;

    public static final String TABLE_NAME = "TUMBONS";
    public static final Column COLUMN_ID = ORM.column(TABLE_NAME, "TUMB_ID");
    public static final Column COLUMN_NAME = ORM.column(TABLE_NAME, "NAME");
    public static final Column COLUMN_AMPH_ID = ORM.column(TABLE_NAME, "AMPH_ID");
    public static final Column COLUMN_ZIPCODE = ORM.column(TABLE_NAME, "ZIPCODE");
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

    public Amphur getAmphur() {
        return amphur;
    }

    public void setAmphur(Amphur amphur) {
        this.amphur = amphur;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public static List<Tumbon> find(String str) throws Exception {
        SQL sql = new SQL();
        return sql.select()
                .from(TABLE_NAME)
                .where(COLUMN_NAME, SQL.WhereClause.Operator.LIKE, "%"+str+"%")
                .chunk(10)
                .fetch(Tumbon.class);
    }


    @Override
    public void orm(ResultSet rs) throws Exception {
        this.id = rs.getInt(COLUMN_ID.getColumnName());
        this.name = rs.getString(COLUMN_NAME.getColumnName());
        this.amphur = SQL.findById(Amphur.class, rs.getInt(COLUMN_AMPH_ID.getColumnName()));
        this.zipcode = rs.getString(COLUMN_ZIPCODE.getColumnName());
    }
}
