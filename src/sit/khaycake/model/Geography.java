package sit.khaycake.model;

import sit.khaycake.database.Column;
import sit.khaycake.database.ORM;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Falook Glico on 5/5/2015.
 */
public class Geography implements ORM {

    private int id;
    private String name;

    public static final String TABLE_NAME = "GEOGRAPHY";
    public static final Column COLUMN_ID = ORM.column(TABLE_NAME,"GEO_ID");
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
        this.id = rs.getInt(COLUMN_ID.getColumnName());
        this.name = rs.getString(COLUMN_NAME.getColumnName());
    }
}
