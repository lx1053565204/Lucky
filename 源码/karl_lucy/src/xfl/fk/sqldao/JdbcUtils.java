package xfl.fk.sqldao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * JDBC�����Ĺ�����
 * 
 * @author fk-7075
 *
 */
public class JdbcUtils {
	static {
		try {
			Class.forName(LuckyConfig.getConfig().nameToValue("jdbc.driver"));
		} catch (ClassNotFoundException e) {
			System.err.println("xflfk:ȱʧmysql��������");
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		try {
			return DriverManager.getConnection(LuckyConfig.getConfig().nameToValue("jdbc.url"),
					LuckyConfig.getConfig().nameToValue("jdbc.username"),
					LuckyConfig.getConfig().nameToValue("jdbc.password"));
		} catch (SQLException e) {
			System.err.println("xflfk�����ݿ�·����������ݿ��û����������");
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
			System.out.println("xflfk:��Դ�رմ���");
			e.printStackTrace();
		}
	}

}
