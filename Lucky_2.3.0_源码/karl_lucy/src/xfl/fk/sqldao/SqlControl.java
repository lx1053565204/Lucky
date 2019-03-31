package xfl.fk.sqldao;

import java.sql.SQLException;
import java.util.List;

import xfl.fk.annotation.AnnotationCgf;
import xfl.fk.annotation.Lucky;
import xfl.fk.cache.CreateSql;
import xfl.fk.cache.LuckyCache;
import xfl.fk.cache.StartCache;
import xfl.fk.table.CreateTable;
import xfl.fk.table.TableToJava;
import xfl.fk.utils.LuckyManager;

/**
 * Lucky的用户使用类(操作数据库)
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
	 * 无参工厂(不负责建表)
	 * @return 操作数据库的SqlControl类对象
	 */
	public static SqlControl getSqlControl() {
		if(sql1==null)
			sql1=new SqlControl();
		return sql1;
	}
	/**
	 * 有参工厂(自动生成MySQL表)
	 * @param first
	 * 配置文件中Class.Url的第一个标号
	 * @param last
	 *  配置文件中Class.Url的最后一个标号
	 * @return 操作数据库的SqlControl类对象
	 */
	public static SqlControl getSqlControlAddCTable(int first, int last) {
		if(sql2==null)
			sql2=new SqlControl(first, last);
			return sql2;
	}
	/**
	 * 有参工厂(逆向工程生成JavaBean，手动输入srcPath,支持中文)
	 * @param srcPath src目录的绝对路径
	 * @return 操作数据库的SqlControl类对象
	 */
	public static SqlControl getSqlControlAddCJavaBean(String srcPath) {
		if(sql3==null)
			sql3=new SqlControl(srcPath);
		return sql3;
	}
	/**
	 * 有参工厂(逆向工程生成JavaBean,不需要手动输入srcPath,但是要在配置文件中做配置,目前此种方法不支持中文)
	 * @return 操作数据库的SqlControl类对象
	 */
	public static SqlControl getSqlControlAddCJavaBean() {
		if(sql4==null)
			sql4=new SqlControl();
		TableToJava.generateJavaSrc();
		return sql4;
		
	}
	/**
	 * 无参构造(不负责建表)
	 */
	private SqlControl() {};

	/**
	 * 有参构造(自动生成MySQL表)
	 * @param first
	 * 配置文件中Class.Url的第一个标号
	 * @param last
	 *  配置文件中Class.Url的最后一个标号
	 */
	private SqlControl(int first, int last) {
		CreateTable ct = new CreateTable();
		ct.creatTable(first, last);
	}
	/**
	 * 有参构造(逆向工程生成JavaBean)
	 * @param srcPath src目录的绝对路径
	 */
	private SqlControl(String srcPath) {
		TableToJava.generateJavaSrc(srcPath);
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
	public boolean deleteById(String tableName, int id) {
		if(!cache)
			return start.deleteById(tableName, id);
		else
			return start.deleteByIdCache(tableName, id);
	}

	/**
	 * ID查询
	 * @param c
	 * 包装类的Class
	 * @param id
	 * @return
	 */
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
	 * 对象方式批操作—保存
	 * @param obj
	 * 包含保存信息的对象数组
	 * @return
	 */
	public boolean saveBatch(Object...obj) {
		if(!cache)
			return start.saveBatch(obj);
		else
			return start.saveBatchCache(obj);
	}
	/**
	 * 对象方式批操作—删除
	 * @param obj
	 * 包含删除信息的对象数组
	 * @return
	 */
	public boolean deleteBatch(Object...obj) {
		if(!cache)
			return start.deleteBatch(obj);
		else
			return start.deleteBatchCache(obj);
	}
	/**
	 * 对象方式批操作—更新
	 * @param obj
	 * 包含更新信息的对象数组
	 * @return
	 */
	public boolean updateBatch(Object...obj) {
		if(!cache)
			return start.updateBatch(obj);
		else
			return start.updateBatchCache(obj);
	}
	/**
	 * 批量保存操作
	 * @param sql
	 * 模板预编译SQL语句
	 * @param obj
	 * 填充占位符的一组组对象数组组成的二维数组
	 * [[xxx],[xxx],[xxx]]
	 * @return
	 */
	public boolean saveBatch(String sql,Object[]... obj) {
		if(!cache)
			return start.saveBatch(sql, obj);
		else
			return start.saveBatchCache(sql, obj);
	}
	/**
	 * 批量更删除操作
	 * @param sql
	 * 模板预编译SQL语句
	 * @param obj
	 * 填充占位符的一组组对象数组组成的二维数组
	 * [[xxx],[xxx],[xxx]]
	 * @return
	 */
	public boolean deleteBatch(String sql,Object[]... obj) {
		if(!cache)
			return start.deleteBatch(sql, obj);
		else
			return start.deleteBatchCache(sql, obj);
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
		if(!cache)
			return start.updateBatch(sql, obj);
		else
			return start.updateBatchCache(sql, obj);
	}
	/**
	 * 批量ID删除
	 * @param clzz
	 * 要操作表对应类的Class
	 * @param ids
	 * 要删除的id所组成的集合
	 * @return
	 */
	public boolean deleteIDBatch(Class clzz,int...ids) {
		if(!cache)
			return start.deleteBatch(clzz, ids);
		else
			return start.deleteBatchCache(clzz,ids);
	}
	/**
	 * 开启Lucky的事务管理
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
