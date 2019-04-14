package xfl.fk.sqldao;

import java.sql.SQLException;
import java.util.List;

import xfl.fk.cache.StartCache;
import xfl.fk.table.CreateTable;
import xfl.fk.table.TableToJava;
import xfl.fk.utils.LuckyManager;

/**
 * Lucky���û�ʹ����(�������ݿ�)
 * 
 * @author fk-7075
 *
 */
@SuppressWarnings("all")
public class SqlControl {
	private static SqlControl sql1;
	private static SqlControl sql2;
	private static SqlControl sql3;
	private static SqlControl sql4;
	private SqlOperation sqlOperation =LuckyManager.getSqlOperation();
	private LuckyConfig lucycfg=LuckyConfig.getConfig();
	private StartCache start=new StartCache();
	private boolean cache="true".equals(lucycfg.nameToValue("Cache"));

	/**
	 * �޲ι���(�����𽨱�)
	 * @return �������ݿ��SqlControl�����
	 */
	public static SqlControl getSqlControl() {
		if(sql1==null)
			sql1=new SqlControl();
		return sql1;
	}
	/**
	 * �вι���(�Զ�����MySQL��)
	 * @param first
	 * �����ļ���Class.Url�ĵ�һ�����
	 * @param last
	 *  �����ļ���Class.Url�����һ�����
	 * @return �������ݿ��SqlControl�����
	 */
	public static SqlControl getSqlControlAddCTable(int first, int last) {
		if(sql2==null)
			sql2=new SqlControl(first, last);
			return sql2;
	}
	/**
	 * �вι���(���򹤳�����JavaBean���ֶ�����srcPath,֧������)
	 * @param srcPath srcĿ¼�ľ���·��
	 * @return �������ݿ��SqlControl�����
	 */
	public static SqlControl getSqlControlAddCJavaBean(String srcPath) {
		if(sql3==null)
			sql3=new SqlControl(srcPath);
		return sql3;
	}
	/**
	 * �вι���(���򹤳�����JavaBean,����Ҫ�ֶ�����srcPath,����Ҫ�������ļ���������,Ŀǰ���ַ�����֧������)
	 * @return �������ݿ��SqlControl�����
	 */
	public static SqlControl getSqlControlAddCJavaBean() {
		if(sql4==null)
			sql4=new SqlControl();
		TableToJava.generateJavaSrc();
		return sql4;
		
	}
	/**
	 * �޲ι���(�����𽨱�)
	 */
	private SqlControl() {};

	/**
	 * �вι���(�Զ�����MySQL��)
	 * @param first
	 * �����ļ���Class.Url�ĵ�һ�����
	 * @param last
	 *  �����ļ���Class.Url�����һ�����
	 */
	private SqlControl(int first, int last) {
		CreateTable ct = new CreateTable();
		ct.creatTable(first, last);
	}
	/**
	 * �вι���(���򹤳�����JavaBean)
	 * @param srcPath srcĿ¼�ľ���·��
	 */
	private SqlControl(String srcPath) {
		TableToJava.generateJavaSrc(srcPath);
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
	public boolean deleteById(String tableName, Object id) {
		if(!cache)
			return start.deleteById(tableName, id);
		else
			return start.deleteByIdCache(tableName, id);
	}

	/**
	 * ID��ѯ
	 * @param c
	 * ��װ���Class
	 * @param id
	 * @return
	 */
	public Object getOne(Class c, Object id) {
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
	public boolean delete(Class c, Object id) {
		if(!cache)
			return start.delete(c, id);
		else
			return start.deleteCache(c, id);
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
	 * ��������-����ģʽ
	 * @param obj
	 * ����������Ϣ�Ķ�������
	 * @return
	 */
	public boolean saveArrayBatch(Object...obj) {
		if(!cache)
			return start.saveBatchByArray(obj);
		else
			return start.saveBatchByArrayCache(obj);
	}
	/**
	 * ����ɾ��-����ģʽ
	 * @param obj
	 * ����ɾ����Ϣ�Ķ�������
	 * @return
	 */
	public boolean deleteArrayBatch(Object...obj) {
		if(!cache)
			return start.deleteBatchByArray(obj);
		else
			return start.deleteBatchByArrayCache(obj);
	}
	/**
	 * ��������-����ģʽ
	 * @param obj
	 * ����������Ϣ�Ķ�������
	 * @return
	 */
	public boolean updateArrayBatch(Object...obj) {
		if(!cache)
			return start.updateBatchByArray(obj);
		else
			return start.updateBatchByArrayCache(obj);
	}
	/**
	 * �����������
	 * @param sql
	 * ģ��Ԥ����SQL���
	 * @param obj
	 * ���ռλ����һ�������������ɵĶ�ά����
	 * [[xxx],[xxx],[xxx]]
	 * @return
	 */
	public boolean saveBatch(String sql,Object[][] obj) {
		if(!cache)
			return start.saveBatch(sql, obj);
		else
			return start.saveBatchCache(sql, obj);
	}
	/**
	 * ������ɾ������
	 * @param sql
	 * ģ��Ԥ����SQL���
	 * @param obj
	 * ���ռλ����һ�������������ɵĶ�ά����
	 * [[xxx],[xxx],[xxx]]
	 * @return
	 */
	public boolean deleteBatch(String sql,Object[][] obj) {
		if(!cache)
			return start.deleteBatch(sql, obj);
		else
			return start.deleteBatchCache(sql, obj);
	}
	/**
	 * �������²���
	 * @param sql
	 * ģ��Ԥ����SQL���
	 * @param obj
	 * ���ռλ����һ�������������ɵĶ�ά����
	 * [[xxx],[xxx],[xxx]]
	 * @return
	 */
	public boolean updateBatch(String sql,Object[][] obj) {
		if(!cache)
			return start.updateBatch(sql, obj);
		else
			return start.updateBatchCache(sql, obj);
	}
	/**
	 * ����IDɾ��
	 * @param clzz
	 * Ҫ�������Ӧ���Class
	 * @param ids
	 * Ҫɾ����id����ɵļ���
	 * @return
	 */
	public boolean deleteIDBatch(Class clzz,Object...ids) {
		if(!cache)
			return start.deleteBatchById(clzz, ids);
		else
			return start.deleteBatchByIdCache(clzz,ids);
	}
	/**
	 * ��������-����ģʽ
	 * @param list Ҫ�����Ķ�������ɵļ���
	 * @return false or true
	 */
	public <T> boolean saveListBatch(List<T> list) {
		if(!cache)
			return start.saveBatchByList(list);
		else
			return start.saveBatchByListCache(list);
	}
	/**
	 * ����ɾ��-����ģʽ
	 * @param list Ҫ�����Ķ�������ɵļ���
	 * @return false or true
	 */
	public <T> boolean deleteListBatch(List<T> list) {
		if(!cache)
			return start.deleteBatchByList(list);
		else
			return start.deleteBatchByListCache(list);
	}
	/**
	 * ��������-����ģʽ
	 * @param list Ҫ�����Ķ�������ɵļ���
	 * @return false or true
	 */
	public <T> boolean updateListBatch(List<T> list) {
		if(!cache)
			return start.updateBatchByList(list);
		else
			return start.updateBatchByListCache(list);
	}
	/**
	 * ����Lucky���������
	 * @return
	 */
	public Transaction openTransaction() {
		Transaction tx = new Transaction();
		tx.setConn(sqlOperation.getConn());
		try {
			tx.getConn().setAutoCommit(false);
		} catch (SQLException e) {
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
