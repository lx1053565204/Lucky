package com.fk.test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.fk.entitry.Test1;

public class DaoTest {

	public static void main(String[] args) {
		Class<Test1> test=Test1.class;
		Field[] fields=test.getDeclaredFields();
		Method[] methods=test.getDeclaredMethods();
		for (Field f : fields) {
			System.out.println(f.getName());
			System.out.println(f.getType());
		}
		

	}

}
