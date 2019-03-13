package com.fk.entitry;

import xfl.fk.annotation.Cascade;
import xfl.fk.annotation.Lucky;

@Cascade(delete=true,update=true)
@Lucky(table="t_book",id="bid",key= {"stid","autid"},url= {"com.fk.entitry.Stort","com.fk.entitry.Author"})
public class Book {
	private Integer bid;
	private String bname;
	private Double price;
	private Integer stid;
	private Integer autid;
	public Integer getBid() {
		return bid;
	}
	public void setBid(Integer bid) {
		this.bid = bid;
	}
	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getStid() {
		return stid;
	}
	public void setStid(Integer stid) {
		this.stid = stid;
	}
	public Integer getAutid() {
		return autid;
	}
	public void setAutid(Integer autid) {
		this.autid = autid;
	}
	@Override
	public String toString() {
		return "Book [bid=" + bid + ", bname=" + bname + ", price=" + price + ", stid=" + stid + ", autid=" + autid
				+ "]";
	}

}
