package xfl.fk.sqldao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import xfl.fk.annotation.AnnotationCgf;

/**
 * 泛型参数类类信息的抓取与sql语句拼接类
 * 
 * @author fk-7075
 *
 */
@SuppressWarnings("all")
public class ClassUtils {
	private ClassInfo classInfo = null;
	private AnnotationCgf cfg=AnnotationCgf.getAnnCfg(); 
	private List<String> names;
	private List<Object> values;
	private TypeChange tych;
	private LogInfo log;
	private LuckyConfig lucy=LuckyConfig.getConfig();

	public ClassUtils() {
		tych = new TypeChange();
		names = new ArrayList<String>();
		values = new ArrayList<Object>();
		log = new LogInfo();
		
	}

	/**
	 * 传入一个实例化对象，返回一个包含该对象对应类的属性和属性值的对象ClassInfo
	 * @param t
	 * 实体类对象
	 * @return ClassInfo
	 */
	public <T> ClassInfo getClassInfo(T t) {
		classInfo = new ClassInfo();
		classInfo.setClzz(t.getClass());//获得传入对象的Class信息
		String message = t.toString();
		if (message.contains("@"))
			System.err.println("xflfk:未找到" + t.getClass().getName() + "类的toString()方法");
		Class<? extends Object> c = t.getClass();
		Field[] fields = c.getDeclaredFields();
		classInfo.setClassName(cfg.getTableName(c));// 获得接收对象所对应的数据库中的表名
		for (Field f : fields) {
			classInfo.getNames().add(f.getName());// 获得接收对象所对应的类的属性名的集合
		}
		for (int i = 0; i < fields.length; i++) {// 从toString方法获得的字符串中截取各个属性的集合
			if (i != fields.length - 1) {// 前几个属性的截取
				String f = fields[i].getName();
				String f2 = fields[i + 1].getName();
				int p = message.indexOf(f) + f.length() + 1;
				int q = message.indexOf(f2) - 2;
				String fk = message.substring(p, q);
				Object xfl = tych.toType(fields[i].getType().toString(), fk);//类型转换
				classInfo.getValues().add(xfl);
			} else {// 最后一个属性的截取
				int p = message.indexOf(fields[i].getName()) + fields[i].getName().length() + 1;
				int q = (message.length() - 1);
				String fk = message.substring(p, q);
				Object xfl = tych.toType(fields[i].getType().toString(), fk);//类型转换
				classInfo.getValues().add(xfl);
			}
		}
		return classInfo;
	}

	/**
	 * 过滤掉ClassInfo中属性值为null的属性和属性值
	 * @param cs
	 * ClassInfo对象
	 * @return ClassInfo对象
	 */
	private ClassInfo filter(ClassInfo cs) {
		ClassInfo cs1 = new ClassInfo();
		names = cs.getNames();
		values = cs.getValues();
		List<String> nameX = new ArrayList<String>();
		List<Object> valueX = new ArrayList<Object>();
		for (int i = 0; i < values.size(); i++) {// 属性过滤：将属性值为null的属性剔除
			if (!("null".equals(values.get(i)))) {
				valueX.add(values.get(i));
				nameX.add(names.get(i));
			}
		}
		cs1.setClzz(cs.getClzz());
		cs1.setClassName(cs.getClassName());
		cs1.setNames(nameX);
		cs1.setValues(valueX);
		return cs1;
	}

	/**
	 * 返回一个包含预编译的sql语句和填充占位符的Object[]的对象SqlInfo
	 * @param cs
	 * ClassInfo对象
	 * @param operation
	 * 操作关键字
	 * @return SqlInfo
	 */
	public SqlInfo getSqlInfo(ClassInfo cs, String operation) {
		String sql = null;
		SqlInfo sqlInfo = new SqlInfo();
		ClassInfo cs2 = filter(cs);
		Object[] valueY = new Object[cs2.getValues().size()];
		cs2.getValues().toArray(valueY);
		/// 根据属性与属性值拼接sql语句
		if ("SELECT".equalsIgnoreCase(operation)) {
			sql = "SELECT * FROM " + cs2.getClassName() + " WHERE 1=1 ";
			for (int i = 0; i < cs2.getNames().size(); i++) {
				sql = sql + "AND " + cs2.getNames().get(i) + "=? ";
			}
		}
		if ("DELETE".equalsIgnoreCase(operation)) {
			sql = "DELETE FROM " + cs2.getClassName() + " WHERE 1=1 ";
			for (int i = 0; i < cs2.getNames().size(); i++) {
				sql = sql + "AND " + cs2.getNames().get(i) + "=? ";
			}
		}
		if ("INSERT".equalsIgnoreCase(operation)) {
			String sql2 = ") VALUES(";
			sql = "INSERT INTO " + cs2.getClassName() + "(";
			for (int i = 0; i < cs2.getNames().size(); i++) {
				if (i == cs2.getNames().size() - 1) {
					sql = sql + cs2.getNames().get(i);
					sql2 = sql2 + "?)";
				} else {
					sql = sql + cs2.getNames().get(i) + ",";
					sql2 = sql2 + "?,";
				}
			}
			sql = sql + sql2;
		}
		if ("UPDATE".equalsIgnoreCase(operation)) {
			IDAndLocation id_loca=new IDAndLocation(cs2);
			//////如果ID的位置不再最后,则从ID的位置开始，依次将之后的元素向前移动一位，最后将ID放到最后
			if(id_loca.getLocation()!=valueY.length-1) {
				for(int i=id_loca.getLocation();i<valueY.length-1;i++) {
					valueY[i]=valueY[i+1];
				}
				valueY[valueY.length-1]=id_loca.getId();
			}
			String sql2 = " WHERE " + getID(cs2.getClzz()) + "=?";
			sql = "UPDATE " + cs2.getClassName() + " SET ";
			if( cs2.getNames().size()==(id_loca.getLocation()+1)) {
				for (int i = 0; i < cs2.getNames().size()-1; i++) {
					if ((i != cs2.getNames().size() - 2))
						sql = sql + cs2.getNames().get(i) + "=?,";
					else
						sql = sql + cs2.getNames().get(i) + "=?";
				}
			}else {
				for (int i = 0; i < cs2.getNames().size(); i++) {
					if ((i != cs2.getNames().size() - 1)&&(i!=id_loca.getLocation()))
						sql = sql + cs2.getNames().get(i) + "=?,";
					else if((i!=id_loca.getLocation()))
						sql = sql + cs2.getNames().get(i) + "=?";
				}
			}
			sql = sql + sql2;
		}
		sqlInfo.setSql(sql);
		sqlInfo.setObj(valueY);
		return sqlInfo;
	}

	/**
	 * 返回分页查询的ClassInfo对象
	 * @param cs
	 * 过滤后的ClassInfo对象
	 * @param index
	 * 第一条数据在表中的位置
	 * @param size
	 * 每页记录数
	 * @return
	 */
	public SqlInfo getSqlInfo(ClassInfo cs, int index, int size) {
		SqlInfo sqlInfo = new SqlInfo();
		ClassInfo cs2 = filter(cs);
		Object[] valueY = new Object[cs2.getValues().size()];
		cs2.getValues().toArray(valueY);
		String sql = "SELECT * FROM " + cs2.getClassName() + " WHERE 1=1";
		String sql2 = " LIMIT " + index + "," + size;
		for (int i = 0; i < cs2.getNames().size(); i++) {
			sql = sql + "AND " + cs2.getNames().get(i) + "=? ";
		}
		sql = sql + sql2;
		sqlInfo.setObj(valueY);
		sqlInfo.setSql(sql);
		return sqlInfo;
	}

	/**
	 * 返回排序查询的ClassInfo对象
	 * @param cs
	 * 过滤后的ClassInfo对象
	 * @param property
	 * 要排序的属性名
	 * @param r
	 * 排序方式（0-升序 1-降序）
	 * @return
	 */
	public SqlInfo getSqlInfo(ClassInfo cs, String property, int r) {
		String ronk = null;
		if (r == 0)
			ronk = "ASC";
		if (r == 1)
			ronk = "DESC";
		SqlInfo sqlInfo = new SqlInfo();
		ClassInfo cs2 = new ClassInfo();
		cs2 = filter(cs);
		Object[] valueY = new Object[cs2.getValues().size()];
		cs2.getValues().toArray(valueY);
		String sql = "SELECT * FROM " + cs2.getClassName() + " WHERE 1=1 ";
		String sql2 = " ORDER BY " + property + " " + ronk;
		for (int i = 0; i < cs2.getNames().size(); i++) {
			sql = sql + "AND " + cs2.getNames().get(i) + "=? ";
		}
		sql = sql + sql2;
		sqlInfo.setObj(valueY);
		sqlInfo.setSql(sql);
		return sqlInfo;
	}

	/**
	 * 返回简单模糊查询的ClassInfo对象
	 * @param c
	 * 包装类的Class
	 * @param property
	 * 要查询的字段
	 * @param info
	 * 查询关键字
	 * @return
	 */
	public SqlInfo getSqlInfo(Class c, String property, String info) {
		SqlInfo sqlInfo = new SqlInfo();
		sqlInfo.setSql("SELECT * FROM " + cfg.getTableName(c) + " WHERE " + property + " LIKE ?");
		Object[] obj = { info };
		sqlInfo.setObj(obj);
		return sqlInfo;
	}
	/**
	 * 找到Class对应的ID
	 * @param c
	 * @return
	 */
	public String getID(Class c) {
		if(cfg.getId(c)!=null)
			return cfg.getId(c);
		else if(!"err".equals(lucy.nameToValueId(cfg.getTableName(c))))
			return lucy.nameToValueId(c.getSimpleName());
		else
			return null;
	}
}
