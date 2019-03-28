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
	private String Sex;

	public User(){}

	public User(Integer uid, String uname, String Sex){
		this.uid=uid;
		this.uname=uname;
		this.Sex=Sex;
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

	public String getSex(){
		return this.Sex;
	}
	public void setSex(String Sex){
		this.Sex=Sex;
	}

	@Override
	public String toString() {
		return "User [uid=" + uid + ", uname=" + uname + ", Sex=" + Sex + "]";
	}
}