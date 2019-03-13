package xfl.fk.table;

import java.util.List;

import xfl.fk.sqldao.LuckyConfig;
import xfl.fk.sqldao.SqlOperation;

public class CreateTable {
	private SqlOperation sqlop = SqlOperation.getSqlOperation();
	

	public void creatTable(int first, int last) {
		DeleteKeySql dtlkeysql=new DeleteKeySql();
		try {
			/////---------ִ�н������-----------/////
				for (int i = first; i <= last; i++) {
					String name = "Class.Url." + i;
					String sql = CreateTableSql
							.getCreateTable(Class.forName(LuckyConfig.getConfig().nameToValue(name)));
					sqlop.setSql(sql);
				}
			/////--------------------------------/////
				dtlkeysql.deleteKey();//ɾ���������е����
			/////---------ִ�������������-----/////
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
			/////--------------------------------/////
		} catch (ClassNotFoundException e) {
			System.err.println("xflfk:  ���������ļ�Class.Url�����Ƿ���д��ȷ");
			e.printStackTrace();
		}
	}
}
