package com.fk.dao;

import java.lang.reflect.Field;

@SuppressWarnings("all")
public class ClassAndFields {
	private Class c;
	private Field[] fields;
	public Class getC() {
		return c;
	}
	public void setC(Class c) {
		this.c = c;
	}
	public Field[] getFields() {
		return fields;
	}
	public void setFields(Field[] fields) {
		this.fields = fields;
	}
	

}
