package xfl.fk.sqldao;

import java.util.List;
import java.util.ResourceBundle;

@SuppressWarnings("all")
public class SqlControl {
	private  SqlOperation sqlOperation=new SqlOperation();
	private  boolean isOk=false;
	private  List<?> list=null;
	private ClassUtils classUtils=new ClassUtils();
	private  ResourceBundle rb = ResourceBundle.getBundle("db");
	/**
	 * 表名+id删除数据
	 * @param tableName
	 * 要操作表的表名
	 * @param id
	 * 字段id
	 * @return
	 */
	public  boolean delete(String tableName,int id) {
		String id_=rb.getString(tableName+".id");
		String sql="DELETE FROM "+tableName+" WHERE "+id_+"=?";
		Object[] obj= {id};
		isOk=sqlOperation.setSql(sql, obj);
		return isOk;
	}
	/**
	 * 
	 * @param c
	 *  封装类的Class
	 * @param tableName
	 *  查询的表名
	 * @param id
	 * 查询的id
	 * @return
	 */
	public  Object getOne(Class c,int id) {
		Object object=null;
		String id_=rb.getString(c.getSimpleName()+".id");
		String sql="SELECT * FROM "+c.getSimpleName()+" WHERE "+id_+"=?";
		Object[] obj= {id};
		list=sqlOperation.getTable(c, sql, obj);
		if(!(list==null||list.size()==0))
			object=list.get(0);
		return object;
	}
	/**
	 * 
	 * @param c 包装类的Class
	 * @param sql 预编译的sql语句
	 * @param obj 填充占位符的数组
	 * @return
	 */
	public  List<?> getList(Class c,String sql,Object[] obj){
		list=sqlOperation.getTable(c, sql, obj);
		return list;
	}
	/**
	 * 预编译sql语句删除
	 * @param sql 预编译的sql语句
	 * @param obj 填充占位符的数组
	 * @return
	 */
	public  boolean delete(String sql, Object[] obj) {
		isOk=sqlOperation.setSql(sql, obj);
		return isOk;
	}
	/**
	 * 预编译sql语句修改
	 * @param sql 预编译的sql语句
	 * @param obj 填充占位符的数组
	 * @return
	 */
	public  boolean update(String sql, Object[] obj) {
		isOk=sqlOperation.setSql(sql, obj);
		return isOk;
	}
	/**
	 * 预编译sql语句保存
	 * @param sql 预编译的sql语句
	 * @param obj 填充占位符的数组
	 * @return
	 */
	public  boolean save(String sql, Object[] obj) {
		isOk=sqlOperation.setSql(sql, obj);
		return isOk;
	}
	/**
	 * 添加数据
	 * @param t
	 * 包含添加信息的包装类的对象
	 * @return
	 */
	public <T> boolean save(T t) {
		SqlInfo f=classUtils.getSqlInfo(classUtils.getClassInfo(t), "insert");
		sqlOperation.setSql(f.getSql(), f.getObj());
		return false;
	}
	/**
	 * 删除数据
	 * @param t
	 * 包含删除信息的包装类的对象
	 * @return
	 */
	public <T> boolean delete(T t) {
		SqlInfo f=classUtils.getSqlInfo(classUtils.getClassInfo(t), "delete");
		sqlOperation.setSql(f.getSql(), f.getObj());
		return false;
	}
	/**
	 * 修改数据
	 * @param t
	 * 包含修改信息的包装类的对象
	 * @return
	 */
	public <T> boolean update(T t) {
		SqlInfo f=classUtils.getSqlInfo(classUtils.getClassInfo(t), "update");
		sqlOperation.setSql(f.getSql(), f.getObj());
		return false;
	}
	/**
	 * 查询数据
	 * @param c
	 * 包含查询信息的包装类的对象
	 * @param t
	 * 对象
	 * @return
	 */
	public <T> List<?> getList(T t){
		SqlInfo f=classUtils.getSqlInfo(classUtils.getClassInfo(t), "select");
		list=sqlOperation.getTable(t.getClass(), f.getSql(), f.getObj());
		return list;
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
	public <T> List<?> getList(T t,int index,int size){
		SqlInfo f=classUtils.getSqlInfo(classUtils.getClassInfo(t), index, size);
		list=sqlOperation.getTable(t.getClass(), f.getSql(), f.getObj());
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
	public <T> List<?> getList(T t,String property,int r){
		SqlInfo f=classUtils.getSqlInfo(classUtils.getClassInfo(t), property, r);
		list=sqlOperation.getTable(t.getClass(), f.getSql(), f.getObj());
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
	public <T> List<?> getList(Class c,String property,String info){
		info="%"+info+"%";
		SqlInfo f=classUtils.getSqlInfo(c,property,info);
		list=sqlOperation.getTable(c, f.getSql(), f.getObj());
		return list;
	}
}
