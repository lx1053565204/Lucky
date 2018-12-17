package com.fk.test;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fk.dao.SqlControl;
import com.fk.entitry.Admin;
import com.fk.entitry.User;
import com.fk.entitry.UserToAdmin;

class SqlControlTest {
	private User user;
	private Admin admin;
	private SqlControl sql;

	@BeforeEach
	void setUp() throws Exception {
		user=new User();
		admin=new Admin();
		sql=new SqlControl();
		
	}

	@Test
	void testDeleteStringInt() {
		fail("Not yet implemented");
	}

	@Test
	void testGetOne() {
		fail("Not yet implemented");
	}

	@Test
	void testGetListClassStringObjectArray() {
		String SQL="SELECT * FROM Admin";
		List<Admin> list=(List<Admin>) sql.getList(Admin.class, SQL);
		System.out.println(list);
	}

	@Test
	void testDeleteStringObjectArray() {
		sql.delete("delete from user where username=?", "LL");
	}

	@Test
	void testUpdateStringObjectArray() {
		fail("Not yet implemented");
	}

	@Test
	void testSaveStringObjectArray() {
		sql.save("insert into admin(adminName,adminpassword) values(?,?)","lucy","12345");
	}

	@Test
	void testSaveT() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteT() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateT() {
		fail("Not yet implemented");
	}

	@Test
	void testGetListT() {
		List<User> list=(List<User>) sql.getList(user);
		System.out.println(list);
	}

	@Test
	void testGetListTIntInt() {
		fail("Not yet implemented");
	}

	@Test
	void testGetListTStringInt() {
		fail("Not yet implemented");
	}

	@Test
	void testGetListClassStringString() {
		fail("Not yet implemented");
	}

}
