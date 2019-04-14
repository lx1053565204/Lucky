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
	/**
	 * 将用','分隔的字符串截取为数组
	 * @param 用','分隔的字符串
	 * @return 字符串数组
	 */
	public static String[] strToArray(String str) {
//		int i=0;
//		List<Integer> list=new ArrayList<Integer>();
//		String s=str;
//		while(s.indexOf(",")!=-1) {
//			list.add(s.lastIndexOf(","));
//			s=s.replace(s.substring(s.lastIndexOf(","), s.length()), "");
//			i++;
//		}
//		String[] array=new String[i+1];
//		array[0]=str.substring(0, list.get(list.size()-1));
//		array[array.length-1]=str.substring(list.get(0)+1, str.length());
//		for(int j=1;j<array.length-1;j++) {
//			array[j]=str.substring(list.get(list.size()-j)+1, list.get(list.size()-1-j));
//		}
		return str.split(",");
	}
	public static void main(String[] args) {
		String st="com.cn";
		String[] s1=strToArray("com.cn,rrr,rrt,gg,dfd");
		String[] s=st.split(",");
		for (String string : s) {
			System.out.println(string);
		}
		for (String string : s1) {
			System.out.println(string);
		}
	}
}
