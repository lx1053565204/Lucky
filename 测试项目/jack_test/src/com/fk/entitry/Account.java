package com.fk.entitry;

import xfl.fk.annotation.Lucky;

@Lucky(table="t_account",id="accid")
public class Account {
	private Integer accid;
	private String name;
	private Integer money;
	public Integer getAccid() {
		return accid;
	}
	public void setAccid(Integer accid) {
		this.accid = accid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getMoney() {
		return money;
	}
	public void setMoney(Integer money) {
		this.money = money;
	}
	@Override
	public String toString() {
		return "Account [accid=" + accid + ", name=" + name + ", money=" + money + "]";
	}
	
}
