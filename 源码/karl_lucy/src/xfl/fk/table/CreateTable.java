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
			if (tn.getTables() == 0) {//���ݿ��б������Ϊ0ʱִ�н�����䣬����ִ�У��Ա�֤�������������
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
			System.err.println("xflfk:  ���������ļ�Class.Url�����Ƿ���д��ȷ");
			e.printStackTrace();
		}
	}
}
