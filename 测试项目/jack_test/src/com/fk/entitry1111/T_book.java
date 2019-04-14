package com.fk.entitry1111;

import java.util.Date;
import java.sql.*;
import java.util.*;
import xfl.fk.annotation.Lucky;

@SuppressWarnings("all")
@Lucky(id="bid")
public class T_book{
	private String bid;
	private String bname;
	private Double price;
	private Integer stid;
	private Integer autid;

	public T_book(){}

	public T_book(String bid, String bname, Double price, Integer stid, Integer autid){
		this.bid=bid;
		this.bname=bname;
		this.price=price;
		this.stid=stid;
		this.autid=autid;
	}

	public String getBid(){
		return this.bid;
	}
	public void setBid(String bid){
		this.bid=bid;
	}

	public String getBname(){
		return this.bname;
	}
	public void setBname(String bname){
		this.bname=bname;
	}

	public Double getPrice(){
		return this.price;
	}
	public void setPrice(Double price){
		this.price=price;
	}

	public Integer getStid(){
		return this.stid;
	}
	public void setStid(Integer stid){
		this.stid=stid;
	}

	public Integer getAutid(){
		return this.autid;
	}
	public void setAutid(Integer autid){
		this.autid=autid;
	}

	@Override
	public String toString() {
		return "T_book [bid=" + bid + ", bname=" + bname + ", price=" + price + ", stid=" + stid + ", autid=" + autid + "]";
	}
}