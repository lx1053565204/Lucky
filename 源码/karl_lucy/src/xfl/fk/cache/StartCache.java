package xfl.fk.cache;

import java.util.List;

import xfl.fk.annotation.AnnotationCgf;
import xfl.fk.sqldao.AutoPackage;
import xfl.fk.sqldao.ClassUtils;
import xfl.fk.sqldao.LuckyConfig;
import xfl.fk.sqldao.SqlInfo;
import xfl.fk.sqldao.SqlOperation;

@SuppressWarnings("all")
public class StartCache {
	private List<?> list = null;
	private SqlOperation sqlOperation = new SqlOperation();
	private boolean isOk = false;
	private LuckyCache lucy = LuckyCache.getLuckyCache();
	private AutoPackage autopackage = new AutoPackage();
	private CreateSql createSql = new CreateSql();
	private ClassUtils classUtils = new ClassUtils();
	private AnnotationCgf cfg=AnnotationCgf.getAnnCfg();

	/**
	 * ���û�����Ƶ�ID��ѯ(���÷�ʽ)
	 * @param c
	 * ��װ���Class
	 * @param id
	 * @return
	 */
	@Deprecated
	public Object getOneCache(Class c, int id) {
		Object object = null;
		String id_ = LuckyConfig.getConfig().nameToValueId(cfg.getTableName(c));
		String sql = "SELECT * FROM " + cfg.getTableName(c) + " WHERE " + id_ + "=?";
		if (lucy.get(createSql.getSqlString(sql, id)) == null) {
			list = autopackage.getTable(c, sql, id);
			lucy.add(createSql.getSqlString(sql, id), list);
		} else {
			list = lucy.get(createSql.getSqlString(sql, id));
		}
		if (!(list == null || list.size() == 0))
			object = list.get(0);
		return object;
	}
	
	/**
	 * ���û����ID��ѯ(ע�ⷽʽ)
	 * 
	 * @param c
	 *            ��װ���Class
	 * @param id
	 *            ����id
	 * @return
	 */
	public Object getoneCache(Class c, int id) {
		Object object = null;
		String id_ = AnnotationCgf.getAnnCfg().getId(c);
		String sql = "SELECT * FROM " + cfg.getTableName(c) + " WHERE " + id_ + "=?";
		if (lucy.get(createSql.getSqlString(sql, id)) == null) {
			list = autopackage.getTable(c, sql, id);
			lucy.add(createSql.getSqlString(sql, id), list);
		} else {
			list = lucy.get(createSql.getSqlString(sql, id));
		}
		if (!(list == null || list.size() == 0))
			object = list.get(0);
		return object;
	}

	/**
	 * ���û����Ԥ����Sql��ѯ
	 * 
	 * @param c
	 *            ��װ���Class
	 * @param sql
	 *            Ԥ�����sql���
	 * @param obj
	 * @return
	 */
	public List<?> getListCache(Class c, String sql, Object... obj) {
		if (lucy.get(createSql.getSqlString(sql, obj)) == null) {
			list = autopackage.getTable(c, sql, obj);
			lucy.add(createSql.getSqlString(sql, obj), list);
		} else {
			list = lucy.get(createSql.getSqlString(sql, obj));
		}
		return list;
	}

	/**
	 * ���û���Ķ����ѯ
	 * 
	 * @param c
	 *            ������ѯ��Ϣ�İ�װ��Ķ���
	 * @param t
	 *            ����
	 * @return
	 */
	public <T> List<?> getListCache(T t) {
		SqlInfo f = classUtils.getSqlInfo(classUtils.getClassInfo(t), "select");
		if (lucy.get(createSql.getSqlString(f)) == null) {
			list = autopackage.getTable(t.getClass(), f.getSql(), f.getObj());
			lucy.add(createSql.getSqlString(f), list);
		} else {
			list = lucy.get(createSql.getSqlString(f));
		}
		return list;
	}

	/**
	 * ���û���ķ�ҳ��ѯ
	 * @param t
	 * ������ѯ��Ϣ�İ�װ��Ķ���
	 * @param index
	 * ��һ�������ڱ��е�λ��
	 * @param size
	 * ÿҳ�ļ�¼��
	 * @return
	 */
	public <T> List<?> getPagListCache(T t, int index, int size) {
		SqlInfo f = classUtils.getSqlInfo(classUtils.getClassInfo(t), index, size);
		if (lucy.get(createSql.getSqlString(f)) == null) {
			list = autopackage.getTable(t.getClass(), f.getSql(), f.getObj());
			lucy.add(createSql.getSqlString(f), list);
		} else {
			list = lucy.get(createSql.getSqlString(f));
		}
		return list;
	}

	/**
	 * ���û���������ѯ
	 * @param t
	 *  ������ѯ��Ϣ�İ�װ��Ķ���
	 * @param property
	 *  ����ؼ���
	 * @param r
	 * ����ʽ��0-���� 1-����
	 * @return
	 */
	public <T> List<?> getSortListCache(T t, String property, int r) {
		SqlInfo f = classUtils.getSqlInfo(classUtils.getClassInfo(t), property, r);
		if (lucy.get(createSql.getSqlString(f)) == null) {
			list = autopackage.getTable(t.getClass(), f.getSql(), f.getObj());
			lucy.add(createSql.getSqlString(f), list);
		} else {
			list = lucy.get(createSql.getSqlString(f));
		}
		return list;
	}

	/**
	 * ���û���ļ�ģ����ѯ
	 * @param c
	 * ��װ���Class
	 * @param property
	 * Ҫ��ѯ���ֶ�
	 * @param info
	 * ��ѯ�ؼ���
	 * @return
	 */
	public <T> List<?> getFuzzyListCache(Class c, String property, String info) {
		info = "%" + info + "%";
		SqlInfo f = classUtils.getSqlInfo(c, property, info);
		if (lucy.get(createSql.getSqlString(f)) == null) {
			list = autopackage.getTable(c, f.getSql(), f.getObj());
			lucy.add(createSql.getSqlString(f), list);
		} else {
			list = lucy.get(createSql.getSqlString(f));
		}
		return list;
	}
	
	/**
	 * ���û�����Ƶı���+idɾ������(���÷�ʽ)
	 * @param tableName
	 * Ҫ������ı���
	 * @param id
	 * �ֶ�id
	 * @return
	 */
	@Deprecated
	public boolean deleteCache(String tableName, int id) {
		String id_ = LuckyConfig.getConfig().nameToValueId(tableName);
		String sql = "DELETE FROM " + tableName + " WHERE " + id_ + "=?";
		isOk = sqlOperation.setSql(sql, id);
		lucy.evenChange(tableName);
		return isOk;
	}
	
	/**
	 * ���û�����Ƶ�idɾ��(ע�ⷽʽ)
	 * @param c
	 * ��������
	 * @param id
	 * idֵ
	 * @return
	 */
	public boolean deleteCache(Class c, int id) {
		String id_ = AnnotationCgf.getAnnCfg().getId(c);
		String sql = "DELETE FROM " +  cfg.getTableName(c) + " WHERE " + id_ + "=?";
		isOk = sqlOperation.setSql(sql, id);
		lucy.evenChange( cfg.getTableName(c));
		return isOk;
	}
	
	/**
	 * ���û�����Ƶ�Ԥ�������ɾ��
	 * @param sql
	 * @param obj
	 * @return
	 */
	public boolean deleteCache(String sql, Object... obj) {
		isOk = sqlOperation.setSql(sql, obj);
		lucy.evenChange(lucy.getName(sql, "delete"));
		return isOk;
	}
	
	/**
	 * ���û�����Ƶ�Ԥ��������޸�
	 * @param sql
	 * @param obj
	 * @return
	 */
	public boolean updateCache(String sql, Object... obj) {
		isOk = sqlOperation.setSql(sql, obj);
		lucy.evenChange(lucy.getName(sql, "update"));
		return isOk;
	}
	
	/**
	 * ���û�����Ƶ�Ԥ������䱣��
	 * @param sql
	 * @param obj
	 * @return
	 */
	public boolean saveCache(String sql, Object... obj) {
		isOk = sqlOperation.setSql(sql, obj);
		lucy.evenChange(lucy.getName(sql, "insert"));
		return isOk;
	}
	
	/**
	 * ���û�����Ƶ��������
	 * @param t
	 * ���������Ϣ�İ�װ��Ķ���
	 * @return
	 */
	public <T> boolean saveCache(T t) {
		SqlInfo f = classUtils.getSqlInfo(classUtils.getClassInfo(t), "insert");
		sqlOperation.setSql(f.getSql(), f.getObj());
		lucy.evenChange(t.getClass().getSimpleName());
		return true;
	}
	
	/**
	 * ���û�����Ƶ�ɾ������
	 * @param t
	 * ����ɾ����Ϣ�İ�װ��Ķ���
	 * @return
	 */
	public <T> boolean deleteCache(T t) {
		SqlInfo f = classUtils.getSqlInfo(classUtils.getClassInfo(t), "delete");
		sqlOperation.setSql(f.getSql(), f.getObj());
		lucy.evenChange(t.getClass().getSimpleName());
		return true;
	}
	
	/**
	 * ���û�����Ƶ��޸�����
	 * @param t
	 * �����޸���Ϣ�İ�װ��Ķ���
	 * @return
	 */
	public <T> boolean updateCache(T t) {
		SqlInfo f = classUtils.getSqlInfo(classUtils.getClassInfo(t), "update");
		sqlOperation.setSql(f.getSql(), f.getObj());
		lucy.evenChange(t.getClass().getSimpleName());
		return true;
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
		Object object = null;
		String id_ = LuckyConfig.getConfig().nameToValueId(cfg.getTableName(c));
		String sql = "SELECT * FROM " + cfg.getTableName(c) + " WHERE " + id_ + "=?";
			list = autopackage.getTable(c, sql, id);
		if (!(list == null || list.size() == 0))
			object = list.get(0);
		return object;
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
		Object object = null;
		String id_ = AnnotationCgf.getAnnCfg().getId(c);
		String sql = "SELECT * FROM " + cfg.getTableName(c)+ " WHERE " + id_ + "=?";
		list = autopackage.getTable(c, sql, id);
		if (!(list == null || list.size() == 0))
			object = list.get(0);
		return object;
	}

	/**
	 * Ԥ����Sql��ѯ
	 * @param c
	 * ��װ���Class
	 * @param sql
	 * Ԥ�����sql���
	 * @param obj
	 * @return
	 */
	public List<?> getList(Class c, String sql, Object... obj) {
		list = autopackage.getTable(c, sql, obj);
		return list;
	}

	/**
	 * �����ѯ
	 * @param c
	 * ������ѯ��Ϣ�İ�װ��Ķ���
	 * @param t
	 * ����
	 * @return
	 */
	public <T> List<?> getList(T t) {
		SqlInfo f = classUtils.getSqlInfo(classUtils.getClassInfo(t), "select");
		list = autopackage.getTable(t.getClass(), f.getSql(), f.getObj());
		return list;
	}

	/**
	 * ��ҳ��ѯ
	 * @param t
	 * ������ѯ��Ϣ�İ�װ��Ķ���
	 * @param index
	 * ��һ�������ڱ��е�λ��
	 * @param size
	 *  ÿҳ�ļ�¼��
	 * @return
	 */
	public <T> List<?> getPagList(T t, int index, int size) {
		SqlInfo f = classUtils.getSqlInfo(classUtils.getClassInfo(t), index, size);
		list = autopackage.getTable(t.getClass(), f.getSql(), f.getObj());
		return list;
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
		SqlInfo f = classUtils.getSqlInfo(classUtils.getClassInfo(t), property, r);
		list = autopackage.getTable(t.getClass(), f.getSql(), f.getObj());
		return list;
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
	public <T> List<?> getFuzzyList(Class c, String property, String info) {
		info = "%" + info + "%";
		SqlInfo f = classUtils.getSqlInfo(c, property, info);
		list = autopackage.getTable(c, f.getSql(), f.getObj());
		return list;
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
		String id_ = LuckyConfig.getConfig().nameToValueId(tableName);
		String sql = "DELETE FROM " + tableName + " WHERE " + id_ + "=?";
		isOk = sqlOperation.setSql(sql, id);
		return isOk;
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
		String id_ = AnnotationCgf.getAnnCfg().getId(c);
		String sql = "DELETE FROM " + cfg.getTableName(c) + " WHERE " + id_ + "=?";
		isOk = sqlOperation.setSql(sql, id);
		return isOk;
	}
	
	/**
	 * Ԥ�������ɾ��
	 * @param sql
	 * @param obj
	 * @return
	 */
	public boolean delete(String sql, Object... obj) {
		isOk = sqlOperation.setSql(sql, obj);
		return isOk;
	}
	
	/**
	 * Ԥ��������޸�
	 * @param sql
	 * @param obj
	 * @return
	 */
	public boolean update(String sql, Object... obj) {
		isOk = sqlOperation.setSql(sql, obj);
		return isOk;
	}
	
	/**
	 * Ԥ������䱣��
	 * @param sql
	 * @param obj
	 * @return
	 */
	public boolean save(String sql, Object... obj) {
		isOk = sqlOperation.setSql(sql, obj);
		return isOk;
	}
	
	/**
	 * �������
	 * @param t
	 * ���������Ϣ�İ�װ��Ķ���
	 * @return
	 */
	public <T> boolean save(T t) {
		SqlInfo f = classUtils.getSqlInfo(classUtils.getClassInfo(t), "insert");
		sqlOperation.setSql(f.getSql(), f.getObj());
		return true;
	}
	
	/**
	 * ɾ������
	 * @param t
	 * ����ɾ����Ϣ�İ�װ��Ķ���
	 * @return
	 */
	public <T> boolean delete(T t) {
		SqlInfo f = classUtils.getSqlInfo(classUtils.getClassInfo(t), "delete");
		sqlOperation.setSql(f.getSql(), f.getObj());
		return true;
	}
	
	/**
	 * �޸�����
	 * @param t
	 *  �����޸���Ϣ�İ�װ��Ķ���
	 * @return
	 */
	public <T> boolean update(T t) {
		SqlInfo f = classUtils.getSqlInfo(classUtils.getClassInfo(t), "update");
		sqlOperation.setSql(f.getSql(), f.getObj());
		return true;
	}
	
	/**
	 * ����ʽ��õ�������
	 * @param t
	 * @return
	 */
	public <T> Object getObject(T t) {
		if(getList(t).size()!=0)
			return getList(t).get(0);
		else
			return null;
	}
	
	/**
	 * Ԥ���뷽ʽ��õ�������
	 * @param c
	 * @param sql
	 * @param obj
	 * @return
	 */
	public Object getObject(Class c, String sql, Object... obj) {
		if(getList(c,sql,obj).size()!=0)
			return getList(c,sql,obj).get(0);
		else
			return null;
	}
	
	/**
	 * ����ʽ��õ������ݣ����棩
	 * @param t
	 * @return
	 */
	public <T> Object getObjectCache(T t) {
		if(getListCache(t).size()!=0)
			return getListCache(t).get(0);
		else
			return null;
	}
	
	/**
	 * Ԥ����sql��ʽ��õ������ݣ����棩
	 * @param c
	 * @param sql
	 * @param obj
	 * @return
	 */
	public Object getObjectCache(Class c, String sql, Object... obj) {
		if(getListCache(c,sql,obj).size()!=0)
			return getListCache(c,sql,obj).get(0);
		else
			return null;
	}
}
