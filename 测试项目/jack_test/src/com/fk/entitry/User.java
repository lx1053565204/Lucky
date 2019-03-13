package com.fk.entitry;

import xfl.fk.annotation.Lucky;

@Lucky(id="uid")
public class User {
	private Integer uid;
	private String uname;
	private String Sex;
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getSex() {
		return Sex;
	}
	public void setSex(String sex) {
		Sex = sex;
	}
	@Override
	public String toString() {
		return "User [uid=" + uid + ", uname=" + uname + ", Sex=" + Sex + "]";
	}
	

}
