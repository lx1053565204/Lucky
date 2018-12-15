package com.fk.dao;

import java.util.ArrayList;
import java.util.List;

public class ClassInfo {
	private String className;
	private List<String> names=new ArrayList<String>();
	private List<Object> values=new ArrayList<Object>();
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public List<String> getNames() {
		return names;
	}
	public void setNames(List<String> names) {
		this.names = names;
	}
	public List<Object> getValues() {
		return values;
	}
	public void setValues(List<Object> values) {
		this.values = values;
	}
	
}
