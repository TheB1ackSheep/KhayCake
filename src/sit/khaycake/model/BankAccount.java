/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sit.khaycake.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import sit.khaycake.database.Column;
import sit.khaycake.database.ORM;
import sit.khaycake.database.SQL;
import sit.khaycake.database.exception.ColumnValueMismatchException;

/**
 * @author -milk
 */
public class BankAccount {


    private int id;
    private Bank.Branch branch;
    private String accNo;
    private String accName;

    public static final String TABLE_NAME = "BANKACCOUNTS";
    public static final Column COLUMN_ID = ORM.column(TABLE_NAME, "BAAC_ID");
    public static final Column COLUMN_BABR_ID = ORM.column(TABLE_NAME, "BABR_ID");
    public static final Column COLUMN_ACC_NO = ORM.column(TABLE_NAME, "ACC_NO");
    public static final Column COLUMN_ACC_NAME = ORM.column(TABLE_NAME, "ACC_NAME");
    public static final List<Column> PRIMARY_KEY = ORM.columns(COLUMN_ID);


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bank.Branch getBranch() {
        return branch;
    }

    public void setBranch(Bank.Branch branch) {
        this.branch = branch;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String acc_name) {
        this.accName = accName;
    }

    public void orm(ResultSet rs) throws Exception {
        this.setId(rs.getInt(COLUMN_ID.getColumnName()));
        this.setBranch((Bank.Branch) SQL.findById(Bank.Branch.class,rs.getInt(COLUMN_BABR_ID.getColumnName())));
        this.setAccNo(rs.getString(COLUMN_ACC_NO.getColumnName()));
        this.setAccName(rs.getString(COLUMN_ACC_NAME.getColumnName()));
    }

    public void save() throws Exception {
        SQL insert = new SQL();
        insert.into(TABLE_NAME,COLUMN_ACC_NAME,COLUMN_ACC_NO,COLUMN_BABR_ID)
                .values(this.accName,this.accNo,this.branch.getId());
    }

}
