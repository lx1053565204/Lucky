package xfl.fk.table;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import xfl.fk.sqldao.SqlOperation;
import xfl.fk.utils.LuckyManager;
import xfl.fk.utils.ProperConfig;

public class DeleteKeySql {
	private SqlOperation sqlop=LuckyManager.getSqlOperation();
	private String databasename;
	private List<String> delkeysql=new ArrayList<String>();
	private ProperConfig propCfg=LuckyManager.getPropCfg();
	public String getDatabasename() {
		return databasename;
	}
	public void setDatabasename(String databasename) {
		this.databasename = databasename;
	}
	public List<String> getDelkeysql() {
		return delkeysql;
	}
	public void setDelkeysql(List<String> delkeysql) {
		this.delkeysql = delkeysql;
	}
	
	/**
	 * 得到数据库的名字和删除数据库所有表外键的sql语句集合并封装到属性中
	 */
	public DeleteKeySql() {
		String url = propCfg.getUrl();
		this.databasename=url.substring((url.lastIndexOf("/")+1),url.length());
		String sql="SELECT CONCAT('ALTER TABLE ',TABLE_SCHEMA,'.',TABLE_NAME,' DROP FOREIGN KEY ',CONSTRAINT_NAME,' ;') " + 
				"FROM information_schema.TABLE_CONSTRAINTS c " + 
				"WHERE c.TABLE_SCHEMA=? AND c.CONSTRAINT_TYPE='FOREIGN KEY';";
		ResultSet rs=sqlop.getResultSet(sql, this.databasename);
		try {
			while(rs.next()) {
				this.delkeysql.add(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除所有外键
	 */
	public void deleteKey() {
		for(String sql:this.delkeysql) {
			sqlop.setSql(sql);
		}
	}

}
