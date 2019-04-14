package xfl.fk.utils;

import xfl.fk.sqldao.DBConnectionPool;
import xfl.fk.sqldao.SqlOperation;

public class LuckyManager {
	public static DBConnectionPool dbpool;
	
	/**
	 * ����������Ϣ����
	 * @return
	 */
	public static ProperConfig getPropCfg() {
		return ProperConfig.getPropCfg();
	}
	/**
	 * �������ӳض���
	 * @return
	 */
	public static DBConnectionPool getDBPool() {
		if(dbpool==null)
			dbpool=new DBConnectionPool();
		return dbpool;
	}
	/**
	 * ���SqlOperation����
	 * @return
	 */
	public static SqlOperation getSqlOperation() {
		return new SqlOperation();
	}
	
}
