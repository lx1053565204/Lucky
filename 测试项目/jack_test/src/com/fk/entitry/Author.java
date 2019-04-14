package com.fk.entitry;

import java.util.Date;
import java.sql.*;
import java.util.*;
import xfl.fk.annotation.Lucky;

@SuppressWarnings("all")
@Lucky(id="autid")
public class Author{
	private Integer autid;
	private String autname;
	private String autsex;

	public Author(){}

	public Author(Integer autid, String autname, String autsex){
		this.autid=autid;
		this.autname=autname;
		this.autsex=autsex;
	}

	public Integer getAutid(){
		return this.autid;
	}
	public void setAutid(Integer autid){
		this.autid=autid;
	}

	public String getAutname(){
		return this.autname;
	}
	public void setAutname(String autname){
		this.autname=autname;
	}

	public String getAutsex(){
		return this.autsex;
	}
	public void setAutsex(String autsex){
		this.autsex=autsex;
	}

	@Override
	public String toString() {
		return "Author [autid=" + autid + ", autname=" + autname + ", autsex=" + autsex + "]";
	}
}