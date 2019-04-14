package xfl.fk.cache;

import java.util.List;

import xfl.fk.annotation.AnnotationCgf;
import xfl.fk.sqldao.AutoPackage;
import xfl.fk.sqldao.ClassUtils;
import xfl.fk.sqldao.LuckyConfig;
import xfl.fk.sqldao.PropAnnotCombination;
import xfl.fk.sqldao.SqlInfo;
import xfl.fk.sqldao.SqlOperation;
import xfl.fk.utils.LuckyManager;

@SuppressWarnings("all")
public class StartCache {
	private List<?> list = null;
	private PropAnnotCombination pac=new PropAnnotCombination();
	private SqlOperation sqlOperation =LuckyManager.getSqlOperation();
	private boolean isOk = false;
	private LuckyCache lucy = LuckyCache.getLuckyCache();
	private AutoPackage autopackage = new AutoPackage();
	private CreateSql createSql = new CreateSql();
	private ClassUtils classUtils = new ClassUtils();
	private AnnotationCgf cfg=AnnotationCgf.getAnnCfg();

	/**
	 * 启用缓存机制的ID查询
	 * @param c
	 * 包装类的Class
	 * @param id
	 * @return
	 */
	public Object getOneCache(Class c, Object id) {
		Object object = null;
		String id_ = pac.tableID(c);
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
	 * 启用缓存的预编译Sql查询
	 * 
	 * @param c
	 *            包装类的Class
	 * @param sql
	 *            预编译的sql语句
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
	 * 启用缓存的对象查询
	 * 
	 * @param c
	 *            包含查询信息的包装类的对象
	 * @param t
	 *            对象
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
	 * 启用缓存的分页查询
	 * @param t
	 * 包含查询信息的包装类的对象
	 * @param index
	 * 第一条数据在表中的位置
	 * @param size
	 * 每页的记录数
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
	 * 启用缓存的排序查询
	 * @param t
	 *  包含查询信息的包装类的对象
	 * @param property
	 *  排序关键字
	 * @param r
	 * 排序方式（0-升序 1-降序）
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
	 * 启用缓存的简单模糊查询
	 * @param c
	 * 包装类的Class
	 * @param property
	 * 要查询的字段
	 * @param info
	 * 查询关键字
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
	 * 启用缓存机制的表名+id删除数据(配置方式)
	 * @param tableName
	 * 要操作表的表名
	 * @param id
	 * 字段id
	 * @return
	 */
	@Deprecated
	public boolean deleteByIdCache(String tableName, Object id) {
		String id_ = LuckyConfig.getConfig().nameToValueId(tableName);
		String sql = "DELETE FROM " + tableName + " WHERE " + id_ + "=?";
		isOk = sqlOperation.setSql(sql, id);
		lucy.evenChange(tableName);
		return isOk;
	}
	
	/**
	 * 启用缓存机制的id删除(注解方式)
	 * @param c
	 * 所操作类
	 * @param id
	 * id值
	 * @return
	 */
	public boolean deleteCache(Class c, Object id) {
		String id_ = AnnotationCgf.getAnnCfg().getId(c);
		String sql = "DELETE FROM " +  cfg.getTableName(c) + " WHERE " + id_ + "=?";
		isOk = sqlOperation.setSql(sql, id);
		lucy.evenChange( cfg.getTableName(c));
		return isOk;
	}
	
	/**
	 * 启用缓存机制的预编译语句删除
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
	 * 启用缓存机制的预编译语句修改
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
	 * 启用缓存机制的预编译语句保存
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
	 * 启用缓存机制的添加数据
	 * @param t
	 * 包含添加信息的包装类的对象
	 * @return
	 */
	public <T> boolean saveCache(T t) {
		SqlInfo f = classUtils.getSqlInfo(classUtils.getClassInfo(t), "insert");
		sqlOperation.setSql(f.getSql(), f.getObj());
		lucy.evenChange(t.getClass().getSimpleName());
		return true;
	}
	
	/**
	 * 启用缓存机制的删除数据
	 * @param t
	 * 包含删除信息的包装类的对象
	 * @return
	 */
	public <T> boolean deleteCache(T t) {
		SqlInfo f = classUtils.getSqlInfo(classUtils.getClassInfo(t), "delete");
		sqlOperation.setSql(f.getSql(), f.getObj());
		lucy.evenChange(t.getClass().getSimpleName());
		return true;
	}
	
	/**
	 * 启用缓存机制的修改数据
	 * @param t
	 * 包含修改信息的包装类的对象
	 * @return
	 */
	public <T> boolean updateCache(T t) {
		SqlInfo f = classUtils.getSqlInfo(classUtils.getClassInfo(t), "update");
		sqlOperation.setSql(f.getSql(), f.getObj());
		lucy.evenChange(t.getClass().getSimpleName());
		return true;
	}


	/**
	 * ID查询
	 * @param c
	 * 包装类的Class
	 * @param id
	 * @return
	 */
	public Object getOne(Class c, Object id) {
		Object object = null;
		String id_ =pac.tableID(c);
		String sql = "SELECT * FROM " + cfg.getTableName(c) + " WHERE " + id_ + "=?";
			list = autopackage.getTable(c, sql, id);
		if (!(list == null || list.size() == 0))
			object = list.get(0);
		return object;
	}

	/**
	 * 预编译Sql查询
	 * @param c
	 * 包装类的Class
	 * @param sql
	 * 预编译的sql语句
	 * @param obj
	 * @return
	 */
	public List<?> getList(Class c, String sql, Object... obj) {
		list = autopackage.getTable(c, sql, obj);
		return list;
	}

	/**
	 * 对象查询
	 * @param c
	 * 包含查询信息的包装类的对象
	 * @param t
	 * 对象
	 * @return
	 */
	public <T> List<?> getList(T t) {
		SqlInfo f = classUtils.getSqlInfo(classUtils.getClassInfo(t), "select");
		list = autopackage.getTable(t.getClass(), f.getSql(), f.getObj());
		return list;
	}

	/**
	 * 分页查询
	 * @param t
	 * 包含查询信息的包装类的对象
	 * @param index
	 * 第一条数据在表中的位置
	 * @param size
	 *  每页的记录数
	 * @return
	 */
	public <T> List<?> getPagList(T t, int index, int size) {
		SqlInfo f = classUtils.getSqlInfo(classUtils.getClassInfo(t), index, size);
		list = autopackage.getTable(t.getClass(), f.getSql(), f.getObj());
		return list;
	}

	/**
	 * 排序查询
	 * @param t
	 * 包含查询信息的包装类的对象
	 * @param property
	 * 排序关键字
	 * @param r
	 * 排序方式（0-升序 1-降序）
	 * @return
	 */
	public <T> List<?> getSortList(T t, String property, int r) {
		SqlInfo f = classUtils.getSqlInfo(classUtils.getClassInfo(t), property, r);
		list = autopackage.getTable(t.getClass(), f.getSql(), f.getObj());
		return list;
	}

	/**
	 * 简单模糊查询
	 * @param c
	 * 包装类的Class
	 * @param property
	 * 要查询的字段
	 * @param info
	 * 查询关键字
	 * @return
	 */
	public <T> List<?> getFuzzyList(Class c, String property, String info) {
		info = "%" + info + "%";
		SqlInfo f = classUtils.getSqlInfo(c, property, info);
		list = autopackage.getTable(c, f.getSql(), f.getObj());
		return list;
	}
	
	/**
	 * 表名+id删除数据(配置方式)
	 * @param tableName
	 * 要操作表的表名
	 * @param id
	 * 字段id
	 * @return
	 */
	@Deprecated
	public boolean deleteById(String tableName, Object id) {
		String id_ = LuckyConfig.getConfig().nameToValueId(tableName);
		String sql = "DELETE FROM " + tableName + " WHERE " + id_ + "=?";
		isOk = sqlOperation.setSql(sql, id);
		return isOk;
	}
	
	/**
	 * id删除(注解方式)
	 * @param c
	 * 所操作类
	 * @param id
	 * id值
	 * @return
	 */
	public boolean delete(Class c, Object id) {
		String id_ = AnnotationCgf.getAnnCfg().getId(c);
		String sql = "DELETE FROM " + cfg.getTableName(c) + " WHERE " + id_ + "=?";
		isOk = sqlOperation.setSql(sql, id);
		return isOk;
	}
	
	/**
	 * 预编译语句删除
	 * @param sql
	 * @param obj
	 * @return
	 */
	public boolean delete(String sql, Object... obj) {
		isOk = sqlOperation.setSql(sql, obj);
		return isOk;
	}
	
	/**
	 * 预编译语句修改
	 * @param sql
	 * @param obj
	 * @return
	 */
	public boolean update(String sql, Object... obj) {
		isOk = sqlOperation.setSql(sql, obj);
		return isOk;
	}
	
	/**
	 * 预编译语句保存
	 * @param sql
	 * @param obj
	 * @return
	 */
	public boolean save(String sql, Object... obj) {
		isOk = sqlOperation.setSql(sql, obj);
		return isOk;
	}
	
	/**
	 * 添加数据
	 * @param t
	 * 包含添加信息的包装类的对象
	 * @return
	 */
	public <T> boolean save(T t) {
		SqlInfo f = classUtils.getSqlInfo(classUtils.getClassInfo(t), "insert");
		sqlOperation.setSql(f.getSql(), f.getObj());
		return true;
	}
	
	/**
	 * 删除数据
	 * @param t
	 * 包含删除信息的包装类的对象
	 * @return
	 */
	public <T> boolean delete(T t) {
		SqlInfo f = classUtils.getSqlInfo(classUtils.getClassInfo(t), "delete");
		sqlOperation.setSql(f.getSql(), f.getObj());
		return true;
	}
	
	/**
	 * 修改数据
	 * @param t
	 *  包含修改信息的包装类的对象
	 * @return
	 */
	public <T> boolean update(T t) {
		SqlInfo f = classUtils.getSqlInfo(classUtils.getClassInfo(t), "update");
		sqlOperation.setSql(f.getSql(), f.getObj());
		return true;
	}
	
	/**
	 * 对象方式获得单条数据
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
	 * 预编译方式获得单条数据
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
	 * 对象方式获得单条数据（缓存）
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
	 * 预编译sql方式获得单条数据（缓存）
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
	/**
	 * 批量添加操作(缓存模式)
	 * @param sql
	 * 模板预编译SQL语句
	 * @param obj
	 * 填充占位符的一组组对象数组组成的二维数组
	 * [[xxx],[xxx],[xxx]]
	 * @return
	 */
	public boolean saveBatchCache(String sql,Object[]... obj) {
		isOk = sqlOperation.setSqlBatch(sql, obj);
		lucy.evenChange(lucy.getName(sql, "insert"));
		return isOk;
	}
	/**
	 * 批量删除操作(缓存模式)
	 * @param sql
	 * 模板预编译SQL语句
	 * @param obj
	 * 填充占位符的一组组对象数组组成的二维数组
	 * [[xxx],[xxx],[xxx]]
	 * @return
	 */
	public boolean deleteBatchCache(String sql,Object[]... obj) {
		isOk = sqlOperation.setSqlBatch(sql, obj);
		lucy.evenChange(lucy.getName(sql, "delete"));
		return isOk;
	}
	/**
	 * 批量更新操作(缓存模式)
	 * @param sql
	 * 模板预编译SQL语句
	 * @param obj
	 * 填充占位符的一组组对象数组组成的二维数组
	 * [[xxx],[xxx],[xxx]]
	 * @return
	 */
	public boolean updateBatchCache(String sql,Object[]... obj) {
		isOk = sqlOperation.setSqlBatch(sql, obj);
		lucy.evenChange(lucy.getName(sql, "update"));
		return isOk;
	}
	/**
	 * 批量添加(缓存模式)
	 * @param obj
	 * 封装着添加信息的对象数组
	 * @return
	 */
	public boolean saveBatchByArrayCache(Object...obj) {
		for(Object o:obj)
			saveCache(o);
		return true;
	}
	/**
	 * 批量删除(缓存模式)
	 * @param obj
	 * 封装着删除信息的对象数组
	 * @return
	 */
	public boolean deleteBatchByArrayCache(Object...obj) {
		for(Object o:obj)
			deleteCache(o);
		return true;
	}
	/**
	 * 批量更新(缓存模式)
	 * @param obj
	 * 封装着更新信息的对象数组
	 * @return
	 */
	public boolean updateBatchByArrayCache(Object...obj) {
		for(Object o:obj)
			updateCache(o);
		return true;
	}
	/**
	 * 批量添加操作
	 * @param sql
	 * 模板预编译SQL语句
	 * @param obj
	 * 填充占位符的一组组对象数组组成的二维数组
	 * [[xxx],[xxx],[xxx]]
	 * @return
	 */
	public boolean saveBatch(String sql,Object[]... obj) {
		isOk = sqlOperation.setSqlBatch(sql, obj);
		return isOk;
	}
	/**
	 * 批量删除操作
	 * @param sql
	 * 模板预编译SQL语句
	 * @param obj
	 * 填充占位符的一组组对象数组组成的二维数组
	 * [[xxx],[xxx],[xxx]]
	 * @return
	 */
	public boolean deleteBatch(String sql,Object[]... obj) {
		isOk = sqlOperation.setSqlBatch(sql, obj);
		return isOk;
	}
	/**
	 * 批量更新操作
	 * @param sql
	 * 模板预编译SQL语句
	 * @param obj
	 * 填充占位符的一组组对象数组组成的二维数组
	 * [[xxx],[xxx],[xxx]]
	 * @return
	 */
	public boolean updateBatch(String sql,Object[]... obj) {
		isOk = sqlOperation.setSqlBatch(sql, obj);
		return isOk;
	}
	/**
	 * 批量保存
	 * @param obj
	 * 封装着保存信息的对象数组
	 * @return
	 */
	public boolean saveBatchByArray(Object... obj) {
		for(Object o:obj) {
			save(o);
		}
		return true;
	}
	/**
	 * 批量更新
	 * @param obj
	 * 封装着更新信息的对象数组
	 * @return
	 */
	public boolean updateBatchByArray(Object... obj) {
		for(Object o:obj) {
			update(o);
		}
		return true;
	}
	/**
	 * 批量删除
	 * @param obj
	 * 封装着删除信息的对象数组
	 * @return
	 */
	public boolean deleteBatchByArray(Object... obj) {
		for(Object o:obj)
			delete(o);
		return true;
	}
	/**
	 * 批量ID删除
	 * @param clzz
	 * 要操作表对应类的Class
	 * @param ids
	 * 要删除的id所组成的集合
	 * @return
	 */
	public boolean deleteBatchById(Class clzz,Object...ids) {
		for(Object i:ids)
			delete(clzz, i);
		return true;
	}
	/**
	 * 批量ID删除(缓存模式)
	 * @param clzz
	 * 要操作表对应类的Class
	 * @param ids
	 * 要删除的id所组成的集合
	 * @return
	 */
	public boolean deleteBatchByIdCache(Class clzz,Object...ids) {
		for(Object i:ids)
			deleteCache(clzz, i);
		return true;
	}
	/**
	 * 批量添加
	 * @param list 要操作的对象所组成的集合
	 * @return false or true
	 */
	public <T> boolean saveBatchByList(List<T> list) {
		for (T t : list) {
			save(t);
		}
		return true;
	}
	/**
	 * 批量删除
	 * @param list 要操作的对象所组成的集合
	 * @return false or true
	 */
	public <T> boolean deleteBatchByList(List<T> list) {
		for (T t : list) {
			delete(t);
		}
		return true;
	}
	/**
	 * 批量更新
	 * @param list 要操作的对象所组成的集合
	 * @return false or true
	 */
	public <T> boolean updateBatchByList(List<T> list) {
		for (T t : list) {
			update(t);
		}
		return true;
	}
	/**
	 * 批量添加(缓存模式)
	 * @param list 要操作的对象所组成的集合
	 * @return false or true
	 */
	public <T> boolean saveBatchByListCache(List<T> list){
		for (T t : list) {
			saveCache(t);
		}
		return true;
	}
	/**
	 * 批量删除(缓存模式)
	 * @param list 要操作的对象所组成的集合
	 * @return false or true
	 */
	public <T> boolean deleteBatchByListCache(List<T> list) {
		for (T t : list) {
			deleteCache(t);
		}
		return true;
	}
	/**
	 * 批量更新(缓存模式)
	 * @param list 要操作的对象所组成的集合
	 * @return false or true
	 */
	public <T> boolean updateBatchByListCache(List<T> list) {
		for (T t : list) {
			updateCache(t);
		}
		return true;
	}
}
