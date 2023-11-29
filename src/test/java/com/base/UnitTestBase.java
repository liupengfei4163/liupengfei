package com.base;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.csv.CsvDataSet;
import org.dbunit.operation.DatabaseOperation;

public class UnitTestBase {
	/**
	 * コネクションを接続し、テストデータを取込む
	 **/
	public static IDatabaseConnection connect() throws Exception {
	    // ①DBコネクション取得
	    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/mysql_test", "root", "test");
	    IDatabaseConnection dbconn = new DatabaseConnection(conn);
	    
	    return dbconn;
	}
	
	/**
	 * コネクションを接続し、テストデータを取込む
	 **/
	public static void initData(String path, IDatabaseConnection dbconn) throws Exception {

	    // ②CSV用データセット作成
	    IDataSet dataset = new CsvDataSet(new File(path));
	    DatabaseOperation.DELETE_ALL.execute(dbconn, dataset);
	    // ④データの挿入
	    DatabaseOperation.INSERT.execute(dbconn, dataset);
	    
	}
	/**
	 * コネクションを接続し、テストデータを取込む
	 **/
	public static void clearData(String path, IDatabaseConnection dbconn) throws Exception {

	    // ②CSV用データセット作成
	    IDataSet dataset = new CsvDataSet(new File(path));

	    DatabaseOperation.DELETE_ALL.execute(dbconn, dataset);
	}
	/**
	 * DB接続をクローズする
	 **/
	public static void closeConnection(IDatabaseConnection dbconn) throws Exception {
		dbconn.close();
	}  
    //登録データを抽出する
    public static ITable executeQuery(IDatabaseConnection dbconn, String tableNm ,String sql){

        PreparedStatement ps;
        ITable iTable = null;
		try {
			ps = dbconn.getConnection().prepareStatement(sql);
			iTable = dbconn.createTable(tableNm, ps);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (DataSetException e) {
			e.printStackTrace();
		}
		return iTable;
    }
}