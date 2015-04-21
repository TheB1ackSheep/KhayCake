package sit.khaycake.model;

import sit.khaycake.database.CanFindByKeyword;
import sit.khaycake.database.Column;
import sit.khaycake.database.ORM;

import java.sql.ResultSet;

/**
 * Created by Pasuth on 21/4/2558.
 */
public class Category implements ORM, CanFindByKeyword {
    private int id;
    private String name;
    private int catParentId;

    public static final String TABLE_NAME = "CATEGORIES";
    public static final Column COLUMN_ID = ORM.column(TABLE_NAME, "CAT_ID");
    public static final Column COLUMN_NAME = ORM.column(TABLE_NAME, "NAME");
    public static final Column COLUMN_CAT_PART_ID = ORM.column(TABLE_NAME,"CAT_PARENT_ID");

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

    public int getCatParentId() {
        return catParentId;
    }

    public void setCatParentId(int catParentId) {
        this.catParentId = catParentId;
    }


    @Override
    public void orm(ResultSet rs) throws Exception {
        this.setId(rs.getInt(COLUMN_ID.getColumnName()));
        this.setName(rs.getString(COLUMN_NAME.getColumnName()));
        this.setCatParentId(rs.getInt(COLUMN_CAT_PART_ID.getColumnName()));
    }




}
