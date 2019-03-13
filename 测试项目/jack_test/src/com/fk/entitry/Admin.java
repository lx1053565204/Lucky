package com.fk.entitry;

import xfl.fk.annotation.Lucky;

@Lucky(table="fk_admin",id="admid")
public class Admin {
	private Integer admid;
	private String admname;
	private String password;
	public Integer getAdmid() {
		return admid;
	}
	public void setAdmid(Integer admid) {
		this.admid = admid;
	}
	public String getAdmname() {
		return admname;
	}
	public void setAdmname(String admname) {
		this.admname = admname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Admin [admid=" + admid + ", admname=" + admname + ", password=" + password + "]";
	}
	

}
