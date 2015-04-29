package sit.khaycake.database;

import sit.khaycake.database.exception.ColumnValueMismatchException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

/**
 * Created by Falook Glico on 4/12/2015.
 */
public class SQL{

    public static class WhereClause{
        private Column column;
        private Operator operator;
        private Object value;
        private SQL.WhereClause.Operator endOperator;

        public enum Operator{
            EQ("="),
            NE("<>"),
            MT(">"),
            MTEQ(">="),
            LT("<"),
            LTEQ("<="),
            LIKE("LIKE"),
            IN("IN"),
            AND("AND"),
            OR("OR")
            ;
            private final String value;

            Operator(final String value) {
                this.value = value;
            }

            @Override
            public String toString() {
                return value;
            }
        }

        public WhereClause(Column column, Operator operator, Object value,SQL.WhereClause.Operator endOperator) {
            this.column = column;
            this.operator = operator;
            this.value = value;
            this.endOperator = endOperator;
        }
    }
    public static class OrderClause{
        protected Column column;
        protected Operator operator;

        public enum Operator{
            ASC("ASC"),
            DESC("DESC")
            ;
            private final String value;

            Operator(final String value) {
                this.value = value;
            }

            @Override
            public String toString() {
                return value;
            }
        }

        public OrderClause(Column column, Operator operator) {
            this.column = column;
            this.operator = operator;
        }
    }

    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DATABASE = "khaycake";
    private static final String USERNAME = "falook";
    private static final String PASSWORD = "falook1559";
    private static final String JNI_NAME = "jdbc/mysql";

    private String sql;

    private List<Object> params;



    public SQL(){this(null);}

    public SQL(String sql) {
        this.sql = sql;
    }

    public static Connection getConnection() throws Exception {
        Connection con = null;
        try {
            Context ctx = null;
            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup(SQL.JNI_NAME);
            con = ds.getConnection();
        } catch (NamingException ex) {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://"+SQL.HOST+":"+SQL.PORT+"/"+SQL.DATABASE;
            con = DriverManager.getConnection(url, SQL.USERNAME, SQL.PASSWORD);
        }
        return con ;
    }

    public SQL select(){
        this.sql = "SELECT * ";
        return this;
    }

    public SQL select(Column... columns){
        this.sql = "SELECT ";
        for (int i = 0; i < columns.length; i++)
            this.sql += columns[i] + ((i<columns.length-1)?", ":" ");
        return this;
    }

    public SQL from(String... tables) throws Exception {
        this.sql += "FROM ";
        for (int i = 0; i < tables.length; i++)
            this.sql += tables[i] + ((i<tables.length-1)?", ":" ");
        return this;
    }

    public SQL where(Column column,WhereClause.Operator operator,Object value){
        return this.where(column,operator,value,null);
    }

    public SQL where(Column column,WhereClause.Operator operator,Object value,WhereClause.Operator whereOperator){
        if(!this.sql.contains("WHERE"))
            this.sql += "WHERE ";
        this.sql += column + " "+operator
                    + " ? " + ((whereOperator != null) ? whereOperator + " " : "");
        if(this.params == null)
            this.params = new ArrayList<>();
        this.params.add(value);
        return this;
    }

    public SQL order(Column column,OrderClause.Operator orderType){
        if(!this.sql.contains("ORDER BY"))
            this.sql += "ORDER BY ";
        else
            this.sql += ", ";
        this.sql += column + " "+ orderType+" ";
        return this;
    }

    public SQL chunk(int size){
        this.sql += "LIMIT 0, "+size;
        return this;
    }

    public SQL chunk(int page,int size){
        this.sql += "LIMIT "+(page-1)*size + 1+", "+size;
        return this;
    }

    public <T extends ORM> List<T> fetch(Class<? extends ORM> entity) throws Exception {
        List<T> result = null;
        try(Connection conn = getConnection()){
            PreparedStatement prep = conn.prepareStatement((sql == null)?this.getSql():this.sql);
            if (this.params != null) {
                for (int i = 0; i < params.size(); i++) {
                    prep.setObject(i+1,params.get(i));
                }
            }
            ResultSet rs = prep.executeQuery();
            while(rs.next()){
                if(result == null)
                    result = new ArrayList<>();
                T t = (T)entity.newInstance();
                t.orm(rs);
                result.add(t);
            }
        }

        this.params = null;
        return result;
    }

    public SQL insert(){
        this.sql = "INSERT ";
        return this;
    }

    public SQL into(String table, Column... columns) throws Exception {
        this.sql += "INTO "+table+ " (";
        for(int i=0;i<columns.length;i++)
            this.sql += columns[i] + ((i<columns.length-1)?", ":") ");
        return this;
    }

    public SQL values(Object... values) {

        if(!this.sql.contains("VALUES")){
            if(this.params == null)
                this.params = new ArrayList<>();
            for(Object o : values)
                this.params.add(o);
            this.sql += "VALUES(";
            for (int i = 0; i < values.length; i++)
                this.sql += "?" + ((i < values.length - 1) ? ", " : ")");
        }

        return this;
    }

    public SQL update(String table) throws Exception {
        this.sql = "UPDATE " + table +" ";
        return this;
    }

    public SQL set(Column column,Object value){
        if(this.params == null)
            this.params = new ArrayList<>();
        this.params.add(value);

        if(!this.sql.contains("SET"))
            this.sql += "SET ";
        else
            this.sql += ", ";
        this.sql += column.columnName + " = ? " ;

        return this;
    }

    public SQL delete(String tableName){
        this.sql = "DELETE FROM "+tableName+" ";
        return this;
    }

    public int exec() throws Exception {
        int ren = 0;
        try (Connection conn = getConnection()) {
            PreparedStatement prep = conn.prepareStatement(this.sql, Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < params.size(); i++)
                prep.setObject(i + 1, params.get(i));
            this.params = null;
            ren = prep.executeUpdate();
            ResultSet rs = prep.getGeneratedKeys();
            if(rs.next())
                ren = rs.getInt(1);
        }
        return ren;
    }

    public static List<? extends ORM> findAll(Class<? extends ORM> entity) throws Exception {
        String table = ORM.getTableName(entity);
        SQL sql = new SQL();
        return sql.select().from(table).fetch(entity);
    }

    public static ORM findById(Class<? extends ORM> entity, Object... id) throws Exception {
        String table = ORM.getTableName(entity);
        List<Column> primaryKeys = ORM.getPrimaryKey(entity);
        SQL sql = new SQL();
        sql.select().from(table);
        for(int i=0;i<primaryKeys.size();i++)
            if(i < primaryKeys.size()-1)
                sql.where(primaryKeys.get(i),WhereClause.Operator.EQ,id[i],WhereClause.Operator.AND);
            else
                sql.where(primaryKeys.get(i),WhereClause.Operator.EQ,id[i]);
        List<ORM> result = sql.fetch(entity);
        return (result==null)?null:result.get(0);
    }

    public static List<? extends ORM> findByKeyword(Class<? extends CanFindByKeyword> entity,String... keywords) throws Exception {
        String table = ORM.getTableName(entity);
        List<Column> candidateKeys = CanFindByKeyword.getCandidateKey(entity);
        SQL sql = new SQL();
        sql.select().from(table);
        for(int i=0;i< keywords.length;i++){
            for(int j=0;j< candidateKeys.size();j++){
                if(j < candidateKeys.size()-1)
                    sql.where(candidateKeys.get(j), WhereClause.Operator.LIKE, "%"+keywords[i]+"%", WhereClause.Operator.OR );
                else
                    sql.where(candidateKeys.get(j), WhereClause.Operator.LIKE, "%"+keywords[i]+"%");
            }
            if(i < keywords.length-1)
                sql.appendSql(" OR ");
        }
        return sql.fetch(entity);
    }

    public void clear(){
        this.sql = "";
        this.params = null;
    }

    public String getSql(){
        return this.sql;
    }

    public void appendSql(String sql){this.sql += sql;}

    @Override
    public String toString() {
        return this.sql;
    }
}