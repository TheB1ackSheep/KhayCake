package sit.khaycake.database;

import java.io.Serializable;

/**
 * Created by Falook Glico on 4/12/2015.
 */
public class Column extends Table implements Serializable {
    protected String columnName;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Column(String tableName, String columnName) {
        super(tableName);
        this.columnName = columnName;
    }

    @Override
    public String toString() {
        return tableName+"."+columnName;
    }
}
