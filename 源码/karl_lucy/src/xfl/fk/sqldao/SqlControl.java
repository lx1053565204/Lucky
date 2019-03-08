package xfl.fk.sqldao;

import java.sql.SQLException;
import java.util.List;

import xfl.fk.annotation.AnnotationCgf;
import xfl.fk.annotation.Lucky;
import xfl.fk.cache.CreateSql;
import xfl.fk.cache.LuckyCache;
import xfl.fk.cache.StartCache;
import xfl.fk.table.CreateTable;

/**
 * Lucky���û�ʹ����(�������ݿ�)
 * 
 * @author fk-7075
 *
 */
@SuppressWarnings("all")
public class SqlControl {
	private SqlOperation sqlOperation = new SqlOperation();
	private LuckyConfig lucycfg=LuckyConfig.getConfig();
	private StartCache start=new StartCache();
	private boolean cache="true".equals(lucycfg.nameToValue("Cache"));

	/**
	 * �޲ι���(�����𽨱�)
	 */
	public SqlControl() {
	};

	/**
	 * �вι���(�Զ�����MySQL��)
	 * @param first
	 * �����ļ���Class.Url�ĵ�һ�����
	 * @param last
	 *  �����ļ���Class.Url�����һ�����
	 */
	public SqlControl(int first, int last) {
		CreateTable ct = new CreateTable();
		ct.creatTable(first, last);
	}

	/**
	 * ����+idɾ������(���÷�ʽ)
	 * @param tableName
	 * Ҫ������ı���
	 * @param id
	 * �ֶ�id
	 * @return
	 */
	@Deprecated
	public boolean delete(String tableName, int id) {
		if(!cache)
			return start.delete(tableName, id);
		else
			return start.deleteCache(tableName, id);
	}

	/**
	 * ID��ѯ(���÷�ʽ)
	 * @param c
	 * ��װ���Class
	 * @param id
	 * @return
	 */
	@Deprecated
	public Object getOne(Class c, int id) {
		if(!cache)
			return start.getOne(c, id);
		else
			return start.getOneCache(c, id);
	}
	
	/**
	 * idɾ��(ע�ⷽʽ)
	 * @param c
	 * ��������
	 * @param id
	 * idֵ
	 * @return
	 */
	public boolean delete(Class c, int id) {
		if(!cache)
			return start.delete(c, id);
		else
			return start.deleteCache(c, id);
	}

	/**
	 * ID��ѯ(ע�ⷽʽ)
	 * @param c
	 * ��װ���Class
	 * @param id
	 * ����id
	 * @return
	 */
	public Object getone(Class c, int id) {
		if(!cache)
			return start.getone(c, id);
		else
			return start.getoneCache(c, id);
	}

	/**
	 * 
	 * @param c
	 * ��װ���Class
	 * @param sql
	 * Ԥ�����sql���
	 * @param obj
	 * @return
	 */
	public List<?> getList(Class c, String sql, Object... obj) {
		if(!cache)
			return start.getList(c, sql, obj);
		else
			return start.getListCache(c, sql, obj);
	}

	/**
	 * Ԥ�������ɾ��
	 * @param sql
	 * @param obj
	 * @return
	 */
	public boolean delete(String sql, Object... obj) {
		if(!cache)
			return start.delete(sql, obj);
		else
			return start.deleteCache(sql, obj);
	}

	/**
	 * Ԥ��������޸�
	 * @param sql
	 * @param obj
	 * @return
	 */
	public boolean update(String sql, Object... obj) {
		if(!cache)
			return start.update(sql, obj);
		else
			return start.updateCache(sql, obj);
	}

	/**
	 * Ԥ������䱣��
	 * @param sql
	 * @param obj
	 * @return
	 */
	public boolean save(String sql, Object... obj) {
		if(!cache)
			return start.save(sql, obj);
		else
			return start.saveCache(sql, obj);
	}

	/**
	 * �������
	 * @param t
	 * ���������Ϣ�İ�װ��Ķ���
	 * @return
	 */
	public <T> boolean save(T t) {
		if(!cache)
			return start.save(t);
		else
			return start.saveCache(t);
	}

	/**
	 * ɾ������
	 * @param t
	 * ����ɾ����Ϣ�İ�װ��Ķ���
	 * @return
	 */
	public <T> boolean delete(T t) {
		if(!cache)
			return start.delete(t);
		else
			return start.deleteCache(t);
	}

	/**
	 * �޸�����
	 * @param t
	 * �����޸���Ϣ�İ�װ��Ķ���
	 * @return
	 */
	public <T> boolean update(T t) {
		if(!cache)
			return start.update(t);
		else
			return start.updateCache(t);
	}

	/**
	 * ��ѯ����
	 * @param c
	 * ������ѯ��Ϣ�İ�װ��Ķ���
	 * @param t
	 * ����
	 * @return
	 */
	public <T> List<?> getList(T t) {
		if(!cache)
			return start.getList(t);
		else
			return start.getListCache(t);
	}

	/**
	 * ��ҳ��ѯ
	 * @param t
	 * ������ѯ��Ϣ�İ�װ��Ķ���
	 * @param index
	 * ��һ�������ڱ��е�λ��
	 * @param size
	 * ÿҳ�ļ�¼��
	 * @return
	 */
	public <T> List<?> getPagList(T t, int index, int size) {
		if(!cache)
			return start.getPagList(t, index, size);
		else
			return start.getPagListCache(t, index, size);
	}

	/**
	 * �����ѯ
	 * @param t
	 * ������ѯ��Ϣ�İ�װ��Ķ���
	 * @param property
	 * ����ؼ���
	 * @param r
	 * ����ʽ��0-���� 1-����
	 * @return
	 */
	public <T> List<?> getSortList(T t, String property, int r) {
		if(!cache)
			return start.getSortList(t, property, r);
		else
			return start.getSortListCache(t, property, r);
	}

	/**
	 * ��ģ����ѯ
	 * @param c
	 * ��װ���Class
	 * @param property
	 * Ҫ��ѯ���ֶ�
	 * @param info
	 * ��ѯ�ؼ���
	 * @return
	 */
	public List<?> getFuzzyList(Class c, String property, String info) {
		if(!cache)
			return start.getFuzzyList(c, property, info);
		else
			return start.getFuzzyListCache(c, property, info);
	}
	
	/**
	 * ����ʽ��õ�������
	 * @param t
	 * @return
	 */
	public <T> Object getObject(T t) {
		if(!cache)
			return start.getObject(t);
		else
			return start.getObjectCache(t);
	}
	
	/**
	 * Ԥ����sql��ʽ��õ�һ����
	 * @param c
	 * @param sql
	 * @param obj
	 * @return
	 */
	public Object getObject(Class c,String sql,Object...obj) {
		if(!cache)
			return start.getObject(c, sql, obj);
		else
			return start.getObjectCache(c, sql, obj);
	}

	/**
	 * ����Lucky���������
	 * 
	 * @return
	 */
	public Transaction openTransaction() {
		Transaction tx = new Transaction();
		tx.setConn(sqlOperation.getConn());
		try {
			tx.getConn().setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tx;
	}

	/**
	 * �ر���Դ
	 */
	public void close() {
		sqlOperation.close();
	}
}
