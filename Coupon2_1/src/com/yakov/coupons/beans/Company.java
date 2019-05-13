package com.yakov.coupons.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Yakov
 * Bean class for company data object
 */
@XmlRootElement
public class Company {
	private long companyId;
	private String companyName;
	private String companyPassword;
	private String companyEmail;

	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyPassword() {
		return companyPassword;
	}
	public void setCompanyPassword(String companyPassword) {
		this.companyPassword = companyPassword;
	}
	public String getCompanyEmail() {
		return companyEmail;
	}
	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}
	@Override
	public String toString() {
		return "([Company ID: " + this.getCompanyId() + "] [Company name: " + this.getCompanyName() + "] [Company password: " + this.getCompanyPassword() 
		+ "] [Company email: " + this.getCompanyEmail() + "])";
	}
}
