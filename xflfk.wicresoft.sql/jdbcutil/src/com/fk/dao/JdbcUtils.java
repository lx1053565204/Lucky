package com.fk.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class JdbcUtils {
	private static ResourceBundle rbundle;
	static {
	    rbundle = ResourceBundle.getBundle("db");
		try {
			Class.forName(rbundle.getString("jdbc.driver"));
		} catch (ClassNotFoundException e) {
			System.out.println("È±Ê§mysqlµÄÇý¶¯°ü");
			e.printStackTrace();
		}
	}
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(rbundle.getString("jdbc.url"), rbundle.getString("jdbc.username"), rbundle.getString("jdbc.password"));
	}
	public static void release(ResultSet rs,PreparedStatement ps,Connection conn){
		try {
			if(rs!=null){
				rs.close();
			}
			if(ps!=null){
				ps.close();
			}
			if(conn!=null){
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
