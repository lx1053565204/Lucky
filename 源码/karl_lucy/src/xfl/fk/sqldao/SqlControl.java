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
 * Lucky的用户使用类(操作数据库)
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
	 * 无参构造(不负责建表)
	 */
	public SqlControl() {
	};

	/**
	 * 有参构造(自动生成MySQL表)
	 * @param first
	 * 配置文件中Class.Url的第一个标号
	 * @param last
	 *  配置文件中Class.Url的最后一个标号
	 */
	public SqlControl(int first, int last) {
		CreateTable ct = new CreateTable();
		ct.creatTable(first, last);
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
	public boolean delete(String tableName, int id) {
		if(!cache)
			return start.delete(tableName, id);
		else
			return start.deleteCache(tableName, id);
	}

	/**
	 * ID查询(配置方式)
	 * @param c
	 * 包装类的Class
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
	 * id删除(注解方式)
	 * @param c
	 * 所操作类
	 * @param id
	 * id值
	 * @return
	 */
	public boolean delete(Class c, int id) {
		if(!cache)
			return start.delete(c, id);
		else
			return start.deleteCache(c, id);
	}

	/**
	 * ID查询(注解方式)
	 * @param c
	 * 包装类的Class
	 * @param id
	 * 主键id
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
	 * 包装类的Class
	 * @param sql
	 * 预编译的sql语句
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
	 * 预编译语句删除
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
	 * 预编译语句修改
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
	 * 预编译语句保存
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
	 * 添加数据
	 * @param t
	 * 包含添加信息的包装类的对象
	 * @return
	 */
	public <T> boolean save(T t) {
		if(!cache)
			return start.save(t);
		else
			return start.saveCache(t);
	}

	/**
	 * 删除数据
	 * @param t
	 * 包含删除信息的包装类的对象
	 * @return
	 */
	public <T> boolean delete(T t) {
		if(!cache)
			return start.delete(t);
		else
			return start.deleteCache(t);
	}

	/**
	 * 修改数据
	 * @param t
	 * 包含修改信息的包装类的对象
	 * @return
	 */
	public <T> boolean update(T t) {
		if(!cache)
			return start.update(t);
		else
			return start.updateCache(t);
	}

	/**
	 * 查询数据
	 * @param c
	 * 包含查询信息的包装类的对象
	 * @param t
	 * 对象
	 * @return
	 */
	public <T> List<?> getList(T t) {
		if(!cache)
			return start.getList(t);
		else
			return start.getListCache(t);
	}

	/**
	 * 分页查询
	 * @param t
	 * 包含查询信息的包装类的对象
	 * @param index
	 * 第一条数据在表中的位置
	 * @param size
	 * 每页的记录数
	 * @return
	 */
	public <T> List<?> getPagList(T t, int index, int size) {
		if(!cache)
			return start.getPagList(t, index, size);
		else
			return start.getPagListCache(t, index, size);
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
		if(!cache)
			return start.getSortList(t, property, r);
		else
			return start.getSortListCache(t, property, r);
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
	public List<?> getFuzzyList(Class c, String property, String info) {
		if(!cache)
			return start.getFuzzyList(c, property, info);
		else
			return start.getFuzzyListCache(c, property, info);
	}
	
	/**
	 * 对象方式获得单个对象
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
	 * 预编译sql方式获得单一对象
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
	 * 开启Lucky的事务管理
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
	 * 关闭资源
	 */
	public void close() {
		sqlOperation.close();
	}
}
