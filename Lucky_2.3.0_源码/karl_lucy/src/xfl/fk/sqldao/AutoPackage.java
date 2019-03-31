package xfl.fk.sqldao;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import xfl.fk.utils.LuckyManager;
/**
 * ������Զ���װ��
 * @author fk-7075
 *
 */
public class AutoPackage {
	private ResultSet rs=null;
	private SqlOperation sqloperation=LuckyManager.getSqlOperation();

	/**
	 * �Զ���������е����ݷ�װ����
	 * @param c
	 * ��װ���Class����
	 * @param sql
	 * Ԥ�����sql���
	 * @param obj
	 * �滻ռλ��������
	 * @return ����һ�����ͼ���
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
			System.err.println("xflfk:������Ϣ�������鷽�����й�Class�Ĳ�������ȷ��ȷ�ϱ���ʵ����ı�д�Ƿ���Ϲ淶��");
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * �жϽ�������Ƿ���ָ������
	 * @param rs ���������
	 * @param columnName ����
	 * @return ���������ָ��������true
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
