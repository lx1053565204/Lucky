package com.fk.test;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fk.dao.SqlControl;
import com.fk.entitry.Admin;
import com.fk.entitry.User;

class SqlControlTest {
	private SqlControl sql;
	private Admin a;

	@BeforeEach
	void setUp() throws Exception {
		sql=new SqlControl();
		a=new Admin();
	}

	@Test
	void testGetListTStringInt() {
		a.setAdminPassword("1234");
		System.out.println(sql.getList(a, "adminName", 1));
	}

	@Test
	void testGetListClassStringString() {
		System.out.println(sql.getList(Admin.class, "adminpassword", "1234"));
	}
	@Test
	void test3() {
		User user=(User) sql.getOne(User.class, 89);
		System.out.println(user);
	}

}
