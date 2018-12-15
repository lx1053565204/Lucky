package com.fk.dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.fk.util.JdbcUtils;

public class SqlDao {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	public boolean setSql(String sql, Object[] obj) {
		try {
			conn = JdbcUtils.getConnection();
			ps = conn.prepareStatement(sql);
			if (obj != null) {
				for (int i = 0; i < obj.length; i++) {
					ps.setObject(i + 1, obj[i]);
				}
			}
			ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtils.release(rs, ps, conn);
		}
		return true;
	}

	public ResultSet getResultSet(String sql, Object[] obj) {
		try {
			conn = JdbcUtils.getConnection();
			ps = conn.prepareStatement(sql);
			if (obj != null) {
				for (int i = 0; i < obj.length; i++) {
					ps.setObject(i + 1, obj[i]);
				}
			}
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	public String getTableName(String sql) {
		String tableName = "";
		int last;
		int index = sql.indexOf("FROM") + 5;
		if (sql.lastIndexOf("WHERE") == -1)
			last = sql.length();
		else
			last = sql.lastIndexOf("WHERE") - 1;
		char[] buf = new char[last - index];
		sql.getChars(index, last, buf, 0);
		tableName = new String(buf);
		return tableName;
	}

	public List<?> getSql(String sql, Object[] obj) {
		String tableName = getTableName(sql);
		List<Object> list = new ArrayList<Object>();
		Object object = null;
		ResourceBundle rbundle = ResourceBundle.getBundle("Config");
		rs = getResultSet(sql, obj);
		try {
			Class<?> className = Class.forName(rbundle.getString("Class." + tableName));
			Field[] fields = className.getDeclaredFields();
			while (rs.next()) {
				object = className.newInstance();
				for (int i = 0; i < fields.length; i++) {
					fields[i].setAccessible(true);
					fields[i].set(object, rs.getObject(i + 1));
				}
				list.add(object);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
	@SuppressWarnings("all") 
	public List<?> getTable(Class c,String sql,Object[] obj) {
		List<Object> list = new ArrayList<Object>();
		rs = getResultSet(sql, obj);
		Object object = null;
		Field[] fields = c.getDeclaredFields();
		try {
			while (rs.next()) {
				object = c.newInstance();
				for (int i = 0; i < fields.length; i++) {
					fields[i].setAccessible(true);
					fields[i].set(object, rs.getObject(fields[i].getName()));
				}
				list.add(object);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
