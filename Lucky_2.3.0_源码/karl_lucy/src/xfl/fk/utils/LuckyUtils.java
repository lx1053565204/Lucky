package xfl.fk.utils;

import java.sql.ResultSet;

import xfl.fk.sqldao.SqlOperation;

/**
 * Lucky�Ĺ�����
 * @author fk-7075
 *
 */
public class LuckyUtils {
	/**
	 * ���ʵ�����ĸ��д
	 * @param tableName ԭʼ����
	 * @return ����ĸ���д��ĵ���
	 */
	public static String TableToClass(String tableName) {
		return tableName.toUpperCase().substring(0, 1)+tableName.substring(1, tableName.length());
	}
	/**
	 * ���ٵõ������
	 * @param sql Ԥ�����SQL
	 * @param obj ���ռλ���Ķ�������
	 * @return ��ѯ�����
	 */
	public static ResultSet getResultSet(String sql,Object...obj) {
		SqlOperation sqlop=LuckyManager.getSqlOperation();
		return sqlop.getResultSet(sql, obj);
	}
	/**
	 * ������Ե�����ȥ������
	 * @param type �����ȵ���������
	 * @return �������ȵ�����
	 */
	public static String getMySqlType(String type) {
		if(type.indexOf("(")>=0)
			return type.substring(0, type.indexOf("("));
		else
			return type;
	}
	/**
	 * MySql����תJava����
	 * @param trpe MySql����
	 * @return Java����
	 */
	public static String mysqlToJava(String trpe) {
		switch (trpe) {
		case "int":
			return "Integer";
		case "double":
			return "Double";
		case "varchar":
			return "String";
		case "datetime":
			return "Date";
		case "date":
			return "Date";
		default:
			return "String";
		}
	}
}
