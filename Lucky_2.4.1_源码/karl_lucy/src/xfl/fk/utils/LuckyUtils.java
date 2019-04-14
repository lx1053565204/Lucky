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
	/**
	 * ����','�ָ����ַ�����ȡΪ����
	 * @param ��','�ָ����ַ���
	 * @return �ַ�������
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
