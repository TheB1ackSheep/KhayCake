package sit.khaycake.database;

/**
 * Created by Falook Glico on 4/12/2015.
 */
public class Table {
    protected String tableName;

    public Table(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String toString() {
        return tableName;
    }

    @Override
    public boolean equals(Object obj) {
        Table t = (Table) obj;
        return this.tableName.equalsIgnoreCase(t.tableName);
    }

    @Override
    public int hashCode() {
        return tableName != null ? tableName.hashCode() : 0;
    }
}
