 package com.fk.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * JDBC操作的工具类
 * @author fk-7075
 *
 */
public class JdbcUtil {

	private static String url="jdbc:mysql:///jack_test";
	private static String user="root";
	private static String password="1234";
	private static Connection conn=null;
	private static PreparedStatement pst=null;
	private static ResultSet rs=null;
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("找不到数据库驱动！！");
			e.printStackTrace();
		}
	}
	/**
	 * 获得Conection对象
	 * @return
	 */
	private static Connection getConnection() {
		try {
			conn=DriverManager.getConnection(url, user, password);
			return conn;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 关闭资源
	 * @param conn
	 * @param pst
	 * @param rs
	 * @throws SQLException
	 */
	public static void close(ResultSet rs,PreparedStatement pst,Connection conn) {
		if(rs!=null)
			try {
				rs.close();
				if(pst!=null)
					pst.close();
				if(conn!=null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	/**
	 * 执行非查询操作
	 * @param sql 预编译的SQL语句
	 * @param obj 填充占位符的对象数组
	 * @return 结果集对象ResultSet
	 */
	public static int noQuery(String sql,Object...obj) {
		int p=0;
		try {
			pst=getConnection().prepareStatement(sql);
			if(obj.length!=0) {
				for(int i=0;i<obj.length;i++) {
					pst.setObject(i+1, obj[i]);
				}
			}
			p=pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
	}
	/**
	 * 执行查询操作
	 * @param sql 预编译的SQL语句
	 * @param obj 填充占位符的对象数组
	 * @return 结果集对象ResultSet
	 */
	public static ResultSet query(String sql,Object...obj) {
		try {
			pst=getConnection().prepareStatement(sql);
			if(obj.length!=0) {
				for(int i=0;i<obj.length;i++) {
					pst.setObject(i+1, obj[i]);
				}
			}
			rs=pst.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

}
