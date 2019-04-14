package com.fk.entitry3;

import java.util.Date;
import java.sql.*;
import java.util.*;
import xfl.fk.annotation.Lucky;

@SuppressWarnings("all")
@Lucky(id="uid")
public class User{
	private Integer uid;
	private String uname;
	private String ssex;
	private String password;

	public User(){}

	public User(Integer uid, String uname, String ssex, String password){
		this.uid=uid;
		this.uname=uname;
		this.ssex=ssex;
		this.password=password;
	}

	public Integer getUid(){
		return this.uid;
	}
	public void setUid(Integer uid){
		this.uid=uid;
	}

	public String getUname(){
		return this.uname;
	}
	public void setUname(String uname){
		this.uname=uname;
	}

	public String getSsex(){
		return this.ssex;
	}
	public void setSsex(String ssex){
		this.ssex=ssex;
	}

	public String getPassword(){
		return this.password;
	}
	public void setPassword(String password){
		this.password=password;
	}

	@Override
	public String toString() {
		return "User [uid=" + uid + ", uname=" + uname + ", ssex=" + ssex + ", password=" + password + "]";
	}
}