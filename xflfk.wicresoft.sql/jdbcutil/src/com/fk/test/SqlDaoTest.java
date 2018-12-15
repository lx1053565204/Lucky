package com.fk.test;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fk.dao.SqlDao;
import com.fk.entitry.User;

class SqlDaoTest {
	private SqlDao sqlDao;

	@BeforeEach
	void setUp() throws Exception {
		sqlDao=new SqlDao();
	}

	@Test
	void testSetSql() {
		User user=new User();
		user.setUsername("ÍõÎå");
		user.setPassword("1234");
		Object[] obj= {user.getUsername(),user.getPassword()};
		sqlDao.setSql("INSERT INTO User(username,password) VALUES(?,?)", obj);
	}

	@Test
	@SuppressWarnings("unchecked")
	void testGetSql() {
		Object[] obj= {"1234"};
		List<User> list=(List<User>) sqlDao.getTable(User.class, "SELECT * FROM User WHERE password=?", obj);
		for(User user:list) {
			System.out.println(user);
		}
		
	}
	@Test
	@SuppressWarnings("unchecked")
	void testGetTable() {
		Object[] obj= {"1234"};
		List<User> list=(List<User>) sqlDao.getTable(User.class,"SELECT * FROM User WHERE password=?", obj);
		for(User user:list) {
			System.out.println(user);
		}
		
	}

}
