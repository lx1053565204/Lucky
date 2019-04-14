package com.fk.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * JDBC事务测试
 * @author fk-7075
 *
 */
@SuppressWarnings("all")
public class JDBCTransaction {
	static {
	try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}
	}

	public static void main(String[] args) throws SQLException {
			Connection conn=DriverManager.getConnection("jdbc:mysql:///jack_test","root","1234");
			try {
				conn.setAutoCommit(false);//开启事务
				String sql1="UPDATE account SET money=money-500 WHERE accid=?";
				String sql2="UPDATE account SET money=money+500 WHERE accid=?";
				PreparedStatement pst1=conn.prepareStatement(sql1);
				PreparedStatement pst2=conn.prepareStatement(sql2);
				pst1.setString(1, "1");
				pst2.setString(1, "2");
				pst1.executeUpdate();
				int i=1/0;//模拟网络异常
				pst2.executeUpdate();
				conn.commit();//提交事务
			}catch (Exception e) {
				e.printStackTrace();
				conn.rollback();//回滚事务
			}
	}

}
