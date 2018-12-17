package com.fk.entitry;

public class Order {
	private Integer id;
	private String name;
	private User user;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "Order [id=" + id + ", name=" + name + ", user=" + user + "]";
	}
	

}
