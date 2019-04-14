package com.fk.dao;

import java.util.List;

import com.fk.pojo.User;

import xfl.fk.annotation.Dao;
import xfl.fk.sqldao.SqlControl;

@Dao
public class TestDao {
	private SqlControl sql=SqlControl.getSqlControl();
	public List<?> userList(){
		return sql.getList(new User());
	}
	public <T> boolean login(T t) {
		return (!sql.getList(t).isEmpty());
	}
	public <T> boolean add(T t) {
		return sql.save(t);
	}
	public boolean del(Class c,int id) {
		return sql.delete(c,id);
	}
	public Object get(Class c,int id) {
		return sql.getOne(c,id);
	}
	
	public <T> boolean upd(T t) {
		return sql.update(t);
	}
	
	public List<?> search(Class c,String sqlt,Object...obj){
		return sql.getList(c, sqlt, obj);
	}
	

}
