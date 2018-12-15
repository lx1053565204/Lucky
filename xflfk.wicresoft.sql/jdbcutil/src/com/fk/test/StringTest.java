package com.fk.test;

public class StringTest {

	public static void main(String[] args) {
		int last;
		String sql = "SELECT * FROM Usererer";
		int index = sql.indexOf("FROM") + 5;
		if (sql.lastIndexOf("where") == -1)
			last = sql.length();
		else
			last=sql.lastIndexOf("where")-1;
		char[] buf=new char[last-index];
		sql.getChars(index, last, buf, 0);
		String tableName=new String(buf);
		// int last=sql.lastIndexOf("where");
		System.out.println(tableName );

	}
	public String getTableName(String sql) {
		String tableName="";
		int last;
		int index = sql.indexOf("FROM") + 5;
		if (sql.lastIndexOf("where") == -1)
			last = sql.length();
		else
			last=sql.lastIndexOf("where")-1;
		char[] buf=new char[last-index];
		sql.getChars(index, last, buf, 0);
		tableName=new String(buf);
		return tableName;
	}

}
