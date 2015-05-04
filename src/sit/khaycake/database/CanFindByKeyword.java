package sit.khaycake.database;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Falook Glico on 4/14/2015.
 */
public interface CanFindByKeyword extends ORM {

    static List<Column> getCandidateKey(Class<? extends ORM> entity) throws NoSuchFieldException, IllegalAccessException {
        return (List<Column>)entity.getDeclaredField("COLUMN_KEYWORD").get(entity);
    };

}
