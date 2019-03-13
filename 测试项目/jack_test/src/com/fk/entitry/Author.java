package com.fk.entitry;

import xfl.fk.annotation.Lucky;

@Lucky(table="t_author",id="autid")
public class Author {
	private Integer autid;
	private String autname;
	private String autsex;
	public Integer getAutid() {
		return autid;
	}
	public void setAutid(Integer autid) {
		this.autid = autid;
	}
	public String getAutname() {
		return autname;
	}
	public void setAutname(String autname) {
		this.autname = autname;
	}
	public String getAutsex() {
		return autsex;
	}
	public void setAutsex(String autsex) {
		this.autsex = autsex;
	}
	@Override
	public String toString() {
		return "Author [autid=" + autid + ", autname=" + autname + ", autsex=" + autsex + "]";
	}
	

}
