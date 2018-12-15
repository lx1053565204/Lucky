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
	 * ����+idɾ������
	 * @param tableName
	 * Ҫ������ı���
	 * @param id
	 * �ֶ�id
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
	 *  ��װ���Class
	 * @param tableName
	 *  ��ѯ�ı���
	 * @param id
	 * ��ѯ��id
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
	 * @param c ��װ���Class
	 * @param sql Ԥ�����sql���
	 * @param obj ���ռλ��������
	 * @return
	 */
	public  List<?> getList(Class c,String sql,Object[] obj){
		list=sqlOperation.getTable(c, sql, obj);
		return list;
	}
	/**
	 * Ԥ����sql���ɾ��
	 * @param sql Ԥ�����sql���
	 * @param obj ���ռλ��������
	 * @return
	 */
	public  boolean delete(String sql, Object[] obj) {
		isOk=sqlOperation.setSql(sql, obj);
		return isOk;
	}
	/**
	 * Ԥ����sql����޸�
	 * @param sql Ԥ�����sql���
	 * @param obj ���ռλ��������
	 * @return
	 */
	public  boolean update(String sql, Object[] obj) {
		isOk=sqlOperation.setSql(sql, obj);
		return isOk;
	}
	/**
	 * Ԥ����sql��䱣��
	 * @param sql Ԥ�����sql���
	 * @param obj ���ռλ��������
	 * @return
	 */
	public  boolean save(String sql, Object[] obj) {
		isOk=sqlOperation.setSql(sql, obj);
		return isOk;
	}
	/**
	 * �������
	 * @param t
	 * ���������Ϣ�İ�װ��Ķ���
	 * @return
	 */
	public <T> boolean save(T t) {
		SqlInfo f=classUtils.getSqlInfo(classUtils.getClassInfo(t), "insert");
		sqlOperation.setSql(f.getSql(), f.getObj());
		return false;
	}
	/**
	 * ɾ������
	 * @param t
	 * ����ɾ����Ϣ�İ�װ��Ķ���
	 * @return
	 */
	public <T> boolean delete(T t) {
		SqlInfo f=classUtils.getSqlInfo(classUtils.getClassInfo(t), "delete");
		sqlOperation.setSql(f.getSql(), f.getObj());
		return false;
	}
	/**
	 * �޸�����
	 * @param t
	 * �����޸���Ϣ�İ�װ��Ķ���
	 * @return
	 */
	public <T> boolean update(T t) {
		SqlInfo f=classUtils.getSqlInfo(classUtils.getClassInfo(t), "update");
		sqlOperation.setSql(f.getSql(), f.getObj());
		return false;
	}
	/**
	 * ��ѯ����
	 * @param c
	 * ������ѯ��Ϣ�İ�װ��Ķ���
	 * @param t
	 * ����
	 * @return
	 */
	public <T> List<?> getList(T t){
		SqlInfo f=classUtils.getSqlInfo(classUtils.getClassInfo(t), "select");
		list=sqlOperation.getTable(t.getClass(), f.getSql(), f.getObj());
		return list;
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
	public <T> List<?> getList(T t,int index,int size){
		SqlInfo f=classUtils.getSqlInfo(classUtils.getClassInfo(t), index, size);
		list=sqlOperation.getTable(t.getClass(), f.getSql(), f.getObj());
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
	public <T> List<?> getList(T t,String property,int r){
		SqlInfo f=classUtils.getSqlInfo(classUtils.getClassInfo(t), property, r);
		list=sqlOperation.getTable(t.getClass(), f.getSql(), f.getObj());
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
	public <T> List<?> getList(Class c,String property,String info){
		info="%"+info+"%";
		SqlInfo f=classUtils.getSqlInfo(c,property,info);
		list=sqlOperation.getTable(c, f.getSql(), f.getObj());
		return list;
	}
}
