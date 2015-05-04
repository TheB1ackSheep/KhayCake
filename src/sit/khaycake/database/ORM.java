package sit.khaycake.database;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Falook Glico on 4/12/2015.
 */
public interface ORM {

    static Column column(String tableName) {
        return new Column(tableName, "*");
    }

    static Column column(String tableName, String col) {
        return new Column(tableName, col);
    }

    static List<Column> columns(Column... columns) {
        List<Column> cols = new ArrayList<>();
        for (Column column : columns)
            cols.add(column);
        return cols;
    }

    static String getTableName(Class<? extends ORM> entity) throws NoSuchFieldException, IllegalAccessException {
        return (String) entity.getDeclaredField("TABLE_NAME").get(entity);
    }

    static List<Column> getPrimaryKey(Class<? extends ORM> entity) throws NoSuchFieldException, IllegalAccessException {
        return (List<Column>) entity.getDeclaredField("PRIMARY_KEY").get(entity);
    }

    ;

    void orm(ResultSet rs) throws Exception;

}
