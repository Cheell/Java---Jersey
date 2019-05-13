package com.yakov.coupons.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Yakov
 * Bean class used for customer data object.
 */
@XmlRootElement
public class Customer {
	private long customerId;
	private String customerName;
	private String customerPassword;

	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerPassword() {
		return customerPassword;
	}
	public void setCustomerPassword(String customerPassword) {
		this.customerPassword = customerPassword;
	}
	
	public String toString() {
		return "([Customer ID: " + this.getCustomerId() + "] [Customer name: " + this.getCustomerName() + "] [Customer password: " + this.getCustomerPassword() 
		+ "])";
	}

}
