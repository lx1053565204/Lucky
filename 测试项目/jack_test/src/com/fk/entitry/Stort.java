package com.fk.entitry;

import xfl.fk.annotation.Lucky;

@Lucky(table="t_stort",id="stid")
public class Stort {
	private Integer stid;
	private String stname;
	public Integer getStid() {
		return stid;
	}
	public void setStid(Integer stid) {
		this.stid = stid;
	}
	public String getStname() {
		return stname;
	}
	public void setStname(String stname) {
		this.stname = stname;
	}
	@Override
	public String toString() {
		return "Stort [stid=" + stid + ", stname=" + stname + "]";
	}
	

}
