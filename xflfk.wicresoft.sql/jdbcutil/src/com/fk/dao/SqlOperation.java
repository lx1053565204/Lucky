package com.fk.dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlOperation {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private boolean isOk=false;

	/**
	 * 实现对表的曾刪改操作
	 * @param sql（预编译的sql语句）
	 * @param obj（替换占位符的数组）
	 * @return boolean
	 */
	public boolean setSql(String sql, Object[] obj) {
		try {
			System.out.println("sql  ："+sql);
			System.out.print("Object[]：{");
			for(Object ob:obj) {
				System.out.print(ob+"    ");
			}
			System.out.print("}");
			conn = JdbcUtils.getConnection();
			ps = conn.prepareStatement(sql);
			if (obj != null) {
				for (int i = 0; i < obj.length; i++) {
					ps.setObject(i + 1, obj[i]);
				}
			}
			int i=ps.executeUpdate();
			if(i!=0)
				isOk=true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtils.release(rs, ps, conn);
		}
		return isOk;
	}
	/**
	 * 返回结果集
	 * @param sql
	 * @param obj
	 * @return
	 */
	private ResultSet getResultSet(String sql, Object[] obj) {
		try {
			System.out.println("sql  ："+sql);
			System.out.print("Object[]：{");
			for(Object ob:obj) {
				System.out.print(ob+"    ");
			}
			System.out.println("}");

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
	/**
	 * 执行对表的查操作
	 * @param c（封装类的Class对象）
	 * @param sql（预编译的sql语句）
	 * @param obj（替换占位符的数组）
	 * @return List<?>
	 */
	@SuppressWarnings("all")
	public List<?> getTable(Class c, String sql, Object[] obj) {
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
			System.err.println("xflfk:反射信息错误，请检查方法中有关Class的参数是正确！确认表与实体类的编写是否符合规范！");
			e.printStackTrace();
		}
		return list;
	}
}
