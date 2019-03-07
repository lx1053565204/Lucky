package xfl.fk.sqldao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * JDBC操作的工具类
 * 
 * @author fk-7075
 *
 */
public class JdbcUtils {
	static {
		try {
			Class.forName(LuckyConfig.getConfig().nameToValue("jdbc.driver"));
		} catch (ClassNotFoundException e) {
			System.err.println("xflfk:缺失mysql的驱动包");
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		try {
			return DriverManager.getConnection(LuckyConfig.getConfig().nameToValue("jdbc.url"),
					LuckyConfig.getConfig().nameToValue("jdbc.username"),
					LuckyConfig.getConfig().nameToValue("jdbc.password"));
		} catch (SQLException e) {
			System.err.println("xflfk：数据库路径错误或数据库用户名密码错误！");
			e.printStackTrace();
			return null;
		}
	}

	public static void release(ResultSet rs, PreparedStatement ps, Connection conn) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			System.out.println("xflfk:资源关闭错误！");
			e.printStackTrace();
		}
	}

}
