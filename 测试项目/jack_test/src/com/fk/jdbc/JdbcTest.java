package com.fk.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

import com.fk.entitry3.Stort;

public class JdbcTest {

	@Test
	public void test01() {
		String sql="INSERT INTO stort(stname) VALUES(?)";
		int rs=JdbcUtil.noQuery(sql, "ÎÄÑ§ÃûÖø");
		System.out.println(rs);
	}
	@Test
	public void test02() {
//		String sql="SELECT * FROM stort WHERE stid=? AND stname=?";
		String sql="SELECT * FROM stort WHERE stid>=?";
		ResultSet rs=JdbcUtil.query(sql, 1);
		try {
			while(rs.next()) {
				Stort st=new Stort();
				st.setStid(rs.getInt("stid"));
				st.setStname(rs.getString("stname"));
				System.out.println(st);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
