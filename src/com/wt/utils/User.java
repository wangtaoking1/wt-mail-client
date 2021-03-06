package com.wt.utils;

/**
 * User is the user class
 * @author wangtao
 * @time 2014/11/15
 */
public class User {
	private String username;
	private String password;
	
	public User() {}
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return this.username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword () {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
