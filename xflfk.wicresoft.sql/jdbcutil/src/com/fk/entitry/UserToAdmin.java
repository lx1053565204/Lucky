package com.fk.entitry;

public class UserToAdmin{
	private String username;
	private String password;  
	private String adminName;
	private String adminPassword;
	private String aabb;
	
	public String getAabb() {
		return aabb;
	}
	public void setAabb(String aabb) {
		this.aabb = aabb;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public String getAdminPassword() {
		return adminPassword;
	}
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}
	@Override
	public String toString() {
		return "UserToAdmin [username=" + username + ", password=" + password + ", adminName=" + adminName
				+ ", adminPassword=" + adminPassword + ", aabb=" + aabb + "]";
	}
}
