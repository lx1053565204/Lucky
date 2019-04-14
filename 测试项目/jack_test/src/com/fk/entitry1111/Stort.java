package com.fk.entitry1111;

import java.util.Date;
import java.sql.*;
import java.util.*;
import xfl.fk.annotation.Lucky;

@SuppressWarnings("all")
@Lucky(id="stid")
public class Stort{
	private Integer stid;
	private String stname;

	public Stort(){}

	public Stort(Integer stid, String stname){
		this.stid=stid;
		this.stname=stname;
	}

	public Integer getStid(){
		return this.stid;
	}
	public void setStid(Integer stid){
		this.stid=stid;
	}

	public String getStname(){
		return this.stname;
	}
	public void setStname(String stname){
		this.stname=stname;
	}

	@Override
	public String toString() {
		return "Stort [stid=" + stid + ", stname=" + stname + "]";
	}
}