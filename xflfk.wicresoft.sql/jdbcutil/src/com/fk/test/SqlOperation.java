package com.fk.test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.fk.dao.ClassAndFields;
import com.fk.dao.JdbcUtils;

@SuppressWarnings("all")
public class SqlOperation {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private boolean isOk = false;
	private ResourceBundle rbundle;

	/**
	 * ʵ�ֶԱ�����h�Ĳ���
	 * 
	 * @param sql��Ԥ�����sql��䣩
	 * @param obj���滻ռλ�������飩
	 * @return boolean
	 */
	public boolean setSql(String sql, Object[] obj) {
		try {
			System.out.println("sql  ��" + sql);
			System.out.print("Object[]��{");
			if (obj != null) {
				for (Object ob : obj) {
					System.out.print(ob + "    ");
				}
			}
			System.out.print("}");
			conn = JdbcUtils.getConnection();
			ps = conn.prepareStatement(sql);
			if (obj != null) {
				for (int i = 0; i < obj.length; i++) {
					ps.setObject(i + 1, obj[i]);
				}
			}
			int i = ps.executeUpdate();
			if (i != 0)
				isOk = true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtils.release(rs, ps, conn);
		}
		return isOk;
	}

	/**
	 * ���ؽ����
	 * 
	 * @param sql
	 * @param obj
	 * @return
	 */
	public ResultSet getResultSet(String sql, Object[] obj) {
		try {
			System.out.println("sql  ��" + sql);
			System.out.print("Object[]��{");
			if (obj != null) {
				for (Object ob : obj) {
					System.out.print(ob + "    ");
				}
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
	 * ִ�жԱ�Ĳ����
	 * 
	 * @param c����װ���Class����
	 * @param sql��Ԥ�����sql��䣩
	 * @param obj���滻ռλ�������飩
	 * @return List<?>
	 */
	@SuppressWarnings("all")
	public List<?> getTable(Class c, String sql, Object[] obj) {
		rbundle = ResourceBundle.getBundle("db");
		List<Object> list = new ArrayList<Object>();
		rs = getResultSet(sql, obj);
		Object object = null;
		Field[] fields = c.getDeclaredFields();
		try {
			while (rs.next()) {
				object = c.newInstance();
				for (int i = 0; i < fields.length; i++) {
					String type = fields[i].getType().toString();
					if (type.equals(rbundle.getString(type))) {
						Class c1 = Class.forName(rbundle.getString(type));
						Object obj1 = c1.newInstance();

					}
					// fields[i].setAccessible(true);
					// fields[i].set(object, rs.getObject(fields[i].getName()));
				}
				list.add(object);
			}
		} catch (Exception e) {
			System.err.println("xflfk:������Ϣ�������鷽�����й�Class�Ĳ�������ȷ��ȷ�ϱ���ʵ����ı�д�Ƿ���Ϲ淶��");
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * �ҳ�һ��Pojo�����е�����Pojo����
	 * 
	 * @param c
	 * @param count
	 * @return
	 * @throws ClassNotFoundException
	 */
	public Set<Class> sort(Class c, int count) throws ClassNotFoundException {
		rbundle = ResourceBundle.getBundle("db");
		String type;
		Set<Class> set = new HashSet<Class>();
		set.add(c);
		Field[] fields = c.getDeclaredFields();
		for (Field f : fields) {
			for (int i = 1; i <= count; i++) {
				type = "Class.Url." + i;
				String properties = rbundle.getString(type);
				String pojoType = f.getType().toString();
				if (pojoType.equals("class " + properties)) {
					Class c1 = Class.forName(properties);
					set.add(c1);
					set.addAll(sort(Class.forName(properties), count));
				}
			}
		}
		return set;
	}

	/**
	 * �жϽ�������Ƿ���ָ������
	 * 
	 * @param rs
	 * @param columnName
	 * @return
	 */
	public boolean isExistColumn(ResultSet rs, String columnName) {
		try {
			if (rs.findColumn(columnName) > 0) {
				return true;
			}
		} catch (SQLException e) {
			return false;
		}
		return false;
	}

	public List<ClassAndFields> getClassAndFields(Set<Class> set) {
		List<ClassAndFields> list = new ArrayList<ClassAndFields>();
		for (Class c : set) {
			ClassAndFields classAndFields = new ClassAndFields();
			classAndFields.setC(c);
			classAndFields.setFields(c.getDeclaredFields());
			list.add(classAndFields);
		}
		return list;

	}

	public List<?> getListCascade(Class c, String sql, Object[] obj, int count)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, SQLException {
		List<Object> objlist = new ArrayList<Object>();
		rs = getResultSet(sql, obj);
		Set<Class> set = sort(c, count);
		List<ClassAndFields> list = getClassAndFields(set);
		for (ClassAndFields classAndFields : list) {
			Object obj1 = classAndFields.getC().newInstance();
			while(rs.next()) {
				for (Field f : classAndFields.getFields()) {
					if (isExistColumn(rs, f.getName())) {
						f.setAccessible(true);
						f.set(obj1, rs.getObject( f.getName()));
					}
				}
				
			}
			objlist.add(obj1);
			
		}
		return objlist;
	}
}
