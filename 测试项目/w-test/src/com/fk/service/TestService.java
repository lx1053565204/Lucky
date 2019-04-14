package com.fk.service;

import java.util.List;

import com.fk.dao.TestDao;
import com.fk.pojo.User;

import xfl.fk.annotation.Autowired;
import xfl.fk.annotation.Service;

@Service
public class TestService {
	
	@Autowired("TestDao")
	private TestDao tdao;

	public List<User> getUser(){
		List<User> list=(List<User>) tdao.userList();
		return list;
	}
	public boolean login(User user) {
		return tdao.login(user);
	}
	public boolean add(User user) {
		return tdao.add(user);
	}
	public boolean del(int id) {
		return tdao.del(User.class,id);
	}
	public User get(int id) {
		User user=(User) tdao.get(User.class, id);
		return user;
	}
	public boolean upd(User user) {
		return tdao.upd(user);
	}
	public List<User> search(String sql,Object...obj){
		List<User> list=(List<User>) tdao.search(User.class,sql,obj);
		return list;
	}
}
