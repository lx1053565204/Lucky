package com.fk.entitry;

import java.util.Date;
import java.sql.*;
import java.util.*;
import xfl.fk.annotation.Lucky;

@SuppressWarnings("all")
@Lucky(id="accid")
public class Account{
	private String accid;
	private String name;
	private Integer money;

	public Account(){}

	public Account(String accid, String name, Integer money){
		this.accid=accid;
		this.name=name;
		this.money=money;
	}

	public String getAccid(){
		return this.accid;
	}
	public void setAccid(String accid){
		this.accid=accid;
	}

	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name=name;
	}

	public Integer getMoney(){
		return this.money;
	}
	public void setMoney(Integer money){
		this.money=money;
	}

	@Override
	public String toString() {
		return "Account [accid=" + accid + ", name=" + name + ", money=" + money + "]";
	}
}