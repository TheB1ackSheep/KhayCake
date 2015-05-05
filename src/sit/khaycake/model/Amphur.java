package sit.khaycake.model;

import sit.khaycake.database.CanFindByKeyword;
import sit.khaycake.database.Column;
import sit.khaycake.database.ORM;
import sit.khaycake.database.SQL;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Falook Glico on 5/5/2015.
 */
public class Amphur implements ORM, CanFindByKeyword {

    private int id;
    private String name;
    private Province province;

    public static final String TABLE_NAME = "AMPHURS";
    public static final Column COLUMN_ID = ORM.column(TABLE_NAME, "AMPH_ID");
    public static final Column COLUMN_NAME = ORM.column(TABLE_NAME, "NAME");
    public static final Column COLUMN_PROV_ID= ORM.column(TABLE_NAME, "PROV_ID");
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

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public static List<Amphur> find(String str) throws Exception {
        SQL sql = new SQL();
        return sql.select()
                .from(TABLE_NAME)
                .where(COLUMN_NAME, SQL.WhereClause.Operator.LIKE, "%"+str+"%")
                .chunk(10)
                .fetch(Amphur.class);
    }

    @Override
    public void orm(ResultSet rs) throws Exception {
        this.id = rs.getInt(COLUMN_ID.getColumnName());
        this.name = rs.getString(COLUMN_NAME.getColumnName());
        this.province = SQL.findById(Province.class, rs.getInt(COLUMN_PROV_ID.getColumnName()));
    }
}
