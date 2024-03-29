package com.yakov.coupons.beans;

import javax.xml.bind.annotation.XmlRootElement;
/**
 * Bean class used for login
 * @author Yakov
 *
 */
@XmlRootElement
public class User {
	private String userName;
	private String userPassword;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
}
