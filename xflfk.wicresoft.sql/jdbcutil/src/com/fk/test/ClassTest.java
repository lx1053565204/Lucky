package com.fk.test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import com.fk.entitry.Order;


public class ClassTest {

	public static void main(String[] args) {
		max(1,4,5,7,8);
		
	}
	public static void max(int...param) {
		for(int i=0;i<param.length;i++) {
			System.out.println(param[i]);
		}
		
	}
}
