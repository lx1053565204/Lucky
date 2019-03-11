package xfl.fk.sqldao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import xfl.fk.annotation.AnnotationCgf;

/**
 * ���Ͳ���������Ϣ��ץȡ��sql���ƴ����
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
	 * ����һ��ʵ�������󣬷���һ�������ö����Ӧ������Ժ�����ֵ�Ķ���ClassInfo
	 * @param t
	 * ʵ�������
	 * @return ClassInfo
	 */
	public <T> ClassInfo getClassInfo(T t) {
		classInfo = new ClassInfo();
		classInfo.setClzz(t.getClass());//��ô�������Class��Ϣ
		String message = t.toString();
		if (message.contains("@"))
			System.err.println("xflfk:δ�ҵ�" + t.getClass().getName() + "���toString()����");
		Class<? extends Object> c = t.getClass();
		Field[] fields = c.getDeclaredFields();
		classInfo.setClassName(cfg.getTableName(c));// ��ý��ն�������Ӧ�����ݿ��еı���
		for (Field f : fields) {
			classInfo.getNames().add(f.getName());// ��ý��ն�������Ӧ������������ļ���
		}
		for (int i = 0; i < fields.length; i++) {// ��toString������õ��ַ����н�ȡ�������Եļ���
			if (i != fields.length - 1) {// ǰ�������ԵĽ�ȡ
				String f = fields[i].getName();
				String f2 = fields[i + 1].getName();
				int p = message.indexOf(f) + f.length() + 1;
				int q = message.indexOf(f2) - 2;
				String fk = message.substring(p, q);
				Object xfl = tych.toType(fields[i].getType().toString(), fk);//����ת��
				classInfo.getValues().add(xfl);
			} else {// ���һ�����ԵĽ�ȡ
				int p = message.indexOf(fields[i].getName()) + fields[i].getName().length() + 1;
				int q = (message.length() - 1);
				String fk = message.substring(p, q);
				Object xfl = tych.toType(fields[i].getType().toString(), fk);//����ת��
				classInfo.getValues().add(xfl);
			}
		}
		return classInfo;
	}

	/**
	 * ���˵�ClassInfo������ֵΪnull�����Ժ�����ֵ
	 * @param cs
	 * ClassInfo����
	 * @return ClassInfo����
	 */
	private ClassInfo filter(ClassInfo cs) {
		ClassInfo cs1 = new ClassInfo();
		names = cs.getNames();
		values = cs.getValues();
		List<String> nameX = new ArrayList<String>();
		List<Object> valueX = new ArrayList<Object>();
		for (int i = 0; i < values.size(); i++) {// ���Թ��ˣ�������ֵΪnull�������޳�
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
	 * ����һ������Ԥ�����sql�������ռλ����Object[]�Ķ���SqlInfo
	 * @param cs
	 * ClassInfo����
	 * @param operation
	 * �����ؼ���
	 * @return SqlInfo
	 */
	public SqlInfo getSqlInfo(ClassInfo cs, String operation) {
		String sql = null;
		SqlInfo sqlInfo = new SqlInfo();
		ClassInfo cs2 = filter(cs);
		Object[] valueY = new Object[cs2.getValues().size()];
		cs2.getValues().toArray(valueY);
		/// ��������������ֵƴ��sql���
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
			//////���ID��λ�ò������,���ID��λ�ÿ�ʼ�����ν�֮���Ԫ����ǰ�ƶ�һλ�����ID�ŵ����
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
	 * ���ط�ҳ��ѯ��ClassInfo����
	 * @param cs
	 * ���˺��ClassInfo����
	 * @param index
	 * ��һ�������ڱ��е�λ��
	 * @param size
	 * ÿҳ��¼��
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
	 * ���������ѯ��ClassInfo����
	 * @param cs
	 * ���˺��ClassInfo����
	 * @param property
	 * Ҫ�����������
	 * @param r
	 * ����ʽ��0-���� 1-����
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
	 * ���ؼ�ģ����ѯ��ClassInfo����
	 * @param c
	 * ��װ���Class
	 * @param property
	 * Ҫ��ѯ���ֶ�
	 * @param info
	 * ��ѯ�ؼ���
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
	 * �ҵ�Class��Ӧ��ID
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
