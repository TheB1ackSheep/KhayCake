package sit.khaycake.model;



import sit.khaycake.database.CanFindByKeyword;
import sit.khaycake.database.Column;
import sit.khaycake.database.ORM;
import sit.khaycake.database.SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Falook Glico on 4/12/2015.
 */
public class Bank implements ORM, CanFindByKeyword {

    public static class Branch implements ORM, CanFindByKeyword {
        private int id;
        private String name;
        private Bank bank;

        public static final String TABLE_NAME = "BANK_BRANCHES";
        public static final Column COLUMN_ID = ORM.column(TABLE_NAME, "BABR_ID");
        public static final Column COLUMN_NAME_TH = ORM.column(TABLE_NAME, "NAME_TH");
        //public static final Column COLUMN_NAME_EN = ORM.column(TABLE_NAME, "NAME_EN");
        public static final Column COLUMN_BANK_ID = ORM.column(TABLE_NAME, "BANK_ID");
        public static final List<Column> PRIMARY_KEY = ORM.columns(COLUMN_ID);
        public static final List<Column> COLUMN_KEYWORD = ORM.columns(COLUMN_NAME_TH);

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

        public Bank getBank() {
            return bank;
        }

        public void setBank(Bank bank) {
            this.bank = bank;
        }

        @Override
        public void orm(ResultSet rs) throws Exception {
            this.setId(rs.getInt(COLUMN_ID.getColumnName()));
            this.setName(rs.getString(COLUMN_ID.getColumnName()));
            this.setBank((Bank)SQL.findById(Bank.class,rs.getInt(COLUMN_BANK_ID.getColumnName())));
        }
    }

    private int id;
    private String name;

    public static final String TABLE_NAME = "BANKS";
    public static final Column COLUMN_ID = ORM.column(TABLE_NAME, "BANK_ID");
    public static final Column COLUMN_NAME_TH = ORM.column(TABLE_NAME, "NAME_TH");
    //public static final Column COLUMN_NAME_EN = ORM.column(TABLE_NAME, "NAME_EN");
    public static final List<Column> PRIMARY_KEY = ORM.columns(COLUMN_ID);
    public static final List<Column> COLUMN_KEYWORD = ORM.columns(COLUMN_NAME_TH);

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

    public void orm(ResultSet rs) throws SQLException {
        this.setId(rs.getInt(COLUMN_ID.getColumnName()));
        this.setName(rs.getString(COLUMN_NAME_TH.getColumnName()));
    }

    public void save() throws Exception{
        SQL sql = new SQL();
        int addId = sql
                .insert()
                .into(Bank.TABLE_NAME, Bank.COLUMN_NAME_TH)
                .values(this.getName())
                .exec();
        this.setId(addId);
    }

    public void update() throws Exception{
        SQL sql = new SQL();
            sql
                    .update(Bank.TABLE_NAME)
                    .set(Bank.COLUMN_NAME_TH, this.getName())
                    .where(Bank.COLUMN_ID, SQL.WhereClause.Operator.EQ, this.getId())
                    .exec();
    }

    public static int delete(int BANK_ID) throws Exception{
        SQL sql = new SQL();
        int a = sql
                .delete(Bank.TABLE_NAME)
                .where(Bank.COLUMN_ID, SQL.WhereClause.Operator.EQ, BANK_ID)
                .exec();
        return a;
    }
}
