package xfl.fk.utils;

import java.sql.ResultSet;

import xfl.fk.sqldao.SqlOperation;

/**
 * Lucky的工具类
 * @author fk-7075
 *
 */
public class LuckyUtils {
	/**
	 * 单词的首字母大写
	 * @param tableName 原始单词
	 * @return 首字母变大写后的单词
	 */
	public static String TableToClass(String tableName) {
		return tableName.toUpperCase().substring(0, 1)+tableName.substring(1, tableName.length());
	}
	/**
	 * 快速得到结果集
	 * @param sql 预编译的SQL
	 * @param obj 填充占位符的对象数组
	 * @return 查询结果集
	 */
	public static ResultSet getResultSet(String sql,Object...obj) {
		SqlOperation sqlop=LuckyManager.getSqlOperation();
		return sqlop.getResultSet(sql, obj);
	}
	/**
	 * 获得属性的类型去掉长度
	 * @param type 带长度的属性类型
	 * @return 不带长度的属性
	 */
	public static String getMySqlType(String type) {
		if(type.indexOf("(")>=0)
			return type.substring(0, type.indexOf("("));
		else
			return type;
	}
	/**
	 * MySql类型转Java类型
	 * @param trpe MySql类型
	 * @return Java类型
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
