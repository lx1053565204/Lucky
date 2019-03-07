package xfl.fk.table;

import java.util.List;

import xfl.fk.sqldao.LuckyConfig;
import xfl.fk.sqldao.SqlOperation;

public class CreateTable {
	private SqlOperation sqlop = new SqlOperation();
	private LuckyConfig lucy = LuckyConfig.getConfig();
	

	public void creatTable(int first, int last) {
		TableNum tn = lucy.getTableNum();
		try {
			if (tn.getTables() == 0) {//数据库中表的张数为0时执行建表语句，否则不执行，以保证外键不会多次生成
				for (int i = first; i <= last; i++) {
					String name = "Class.Url." + i;
					String sql = CreateTableSql
							.getCreateTable(Class.forName(LuckyConfig.getConfig().nameToValue(name)));
					sqlop.setSql(sql);
				}
				for (int i = first; i <= last; i++) {
					String name = "Class.Url." + i;
					List<String> sqls = CreateTableSql
							.getForeignKey(Class.forName(LuckyConfig.getConfig().nameToValue(name)));
					if (sqls != null) {
						for (String st : sqls) {
							sqlop.setSql(st);
						}
					}
				}
			}
		} catch (ClassNotFoundException e) {
			System.err.println("xflfk:  请检查配置文件Class.Url属性是否书写正确");
			e.printStackTrace();
		}
	}
}
