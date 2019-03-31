package xfl.fk.sqldao;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import xfl.fk.utils.LuckyManager;
/**
 * 结果集自动包装类
 * @author fk-7075
 *
 */
public class AutoPackage {
	private ResultSet rs=null;
	private SqlOperation sqloperation=LuckyManager.getSqlOperation();

	/**
	 * 自动将结果集中的内容封装起来
	 * @param c
	 * 封装类的Class对象
	 * @param sql
	 * 预编译的sql语句
	 * @param obj
	 * 替换占位符的数组
	 * @return 返回一个泛型集合
	 */
	@SuppressWarnings("all")
	public List<?> getTable(Class c, String sql, Object... obj) {
		List<Object> list = new ArrayList<Object>();
		rs = sqloperation.getResultSet(sql, obj);
		Object object = null;
		Field[] fields = c.getDeclaredFields();
		try {
			while (rs.next()) {
				object = c.newInstance();
				for(Field f:fields) {
					if(isExistColumn(rs, f.getName())) {
						f.setAccessible(true);
						f.set(object, rs.getObject(f.getName()));
					}
				}
				list.add(object);
			}
		} catch (Exception e) {
			System.err.println("xflfk:反射信息错误，请检查方法中有关Class的参数是正确！确认表与实体类的编写是否符合规范！");
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 判断结果集中是否有指定的列
	 * @param rs 结果集对象
	 * @param columnName 类名
	 * @return 结果集中有指定的列则反true
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

}
