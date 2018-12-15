package xfl.fk.sqldao;

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
			System.err.println("xflfk:缺失mysql的驱动包");
			e.printStackTrace();
		}
	}
	public static Connection getConnection() {
		try {
			return DriverManager.getConnection(rbundle.getString("jdbc.url"), rbundle.getString("jdbc.username"), rbundle.getString("jdbc.password"));
		} catch (SQLException e) {
			System.err.println("xflfk：数据库路径错误或数据库用户名密码错误！");
			e.printStackTrace();
			return null;
		}
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
			System.out.println("xflfk:资源关闭错误！");
			e.printStackTrace();
		}
	}

}
