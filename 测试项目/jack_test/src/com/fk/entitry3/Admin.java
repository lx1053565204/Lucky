package com.fk.entitry3;

import java.util.Date;
import java.sql.*;
import java.util.*;
import xfl.fk.annotation.Lucky;

@SuppressWarnings("all")
@Lucky(id="admid")
public class Admin{
	private Integer admid;
	private String admname;
	private String password;

	public Admin(){}

	public Admin(Integer admid, String admname, String password){
		this.admid=admid;
		this.admname=admname;
		this.password=password;
	}

	public Integer getAdmid(){
		return this.admid;
	}
	public void setAdmid(Integer admid){
		this.admid=admid;
	}

	public String getAdmname(){
		return this.admname;
	}
	public void setAdmname(String admname){
		this.admname=admname;
	}

	public String getPassword(){
		return this.password;
	}
	public void setPassword(String password){
		this.password=password;
	}

	@Override
	public String toString() {
		return "Admin [admid=" + admid + ", admname=" + admname + ", password=" + password + "]";
	}
}