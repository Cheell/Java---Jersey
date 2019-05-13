package com.yakov.coupons.beans;

import javax.xml.bind.annotation.XmlRootElement;

import com.yakov.coupons.enums.CouponTypes;
/**
 * 
 * @author Yakov
 * bean class for coupon data object
 */

@XmlRootElement
public class Coupon {
	private long couponId;
	private String couponTitle;
	private String couponStartDate;
	private String couponEndDate;
	private int couponAmmount;
	private String couponMessage;
	private double couponPrice;
	private String couponImage;	
	private CouponTypes couponType;
	private long companyId;
	private String companyName;
	
	public long getCouponId() {
		return couponId;
	}
	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}
	public String getCouponTitle() {
		return couponTitle;
	}
	public void setCouponTitle(String couponTitle) {
		this.couponTitle = couponTitle;
	}
	public String getCouponStartDate() {
		return couponStartDate;
	}
	public void setCouponStartDate(String couponStartDate) {
		this.couponStartDate = couponStartDate;
	}
	public String getCouponEndDate() {
		return couponEndDate;
	}
	public void setCouponEndDate(String couponEndDate) {
		this.couponEndDate = couponEndDate;
	}
	public int getCouponAmmount() {
		return couponAmmount;
	}
	public void setCouponAmmount(int couponAmmount) {
		this.couponAmmount = couponAmmount;
	}
	public String getCouponMessage() {
		return couponMessage;
	}
	public void setCouponMessage(String couponMessage) {
		this.couponMessage = couponMessage;
	}
	public double getCouponPrice() {
		return couponPrice;
	}
	public void setCouponPrice(double couponPrice) {
		this.couponPrice = couponPrice;
	}
	public String getCouponImage() {
		return couponImage;
	}
	public void setCouponImage(String couponImage) {
		this.couponImage = couponImage;
	}
	public CouponTypes getCouponType() {
		return this.couponType;
	}
	public void setCouponType(CouponTypes couponType) {
		this.couponType = couponType;
	}
/*	public void setCouponType(String couponType) {
		this.couponType = CouponTypes.valueOf(couponType);
	}
*/	
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
	

	
	@Override
	public String toString() {
		return "Coupon [couponId=" + couponId + ", couponTitle=" + couponTitle + ", couponStartDate=" + couponStartDate
				+ ", couponEndDate=" + couponEndDate + ", couponAmmount=" + couponAmmount + ", couponMessage="
				+ couponMessage + ", couponPrice=" + couponPrice + ", couponImage=" + couponImage + ", couponType="
				+ couponType + ", companyId=" + companyId + ", companyName=" + companyName + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (companyId ^ (companyId >>> 32));
		result = prime * result + couponAmmount;
		result = prime * result + ((couponEndDate == null) ? 0 : couponEndDate.hashCode());
		result = prime * result + (int) (couponId ^ (couponId >>> 32));
		result = prime * result + ((couponImage == null) ? 0 : couponImage.hashCode());
		result = prime * result + ((couponMessage == null) ? 0 : couponMessage.hashCode());
		long temp;
		temp = Double.doubleToLongBits(couponPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((couponStartDate == null) ? 0 : couponStartDate.hashCode());
		result = prime * result + ((couponTitle == null) ? 0 : couponTitle.hashCode());
		result = prime * result + ((couponType == null) ? 0 : couponType.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Coupon other = (Coupon) obj;
		if (companyId != other.companyId)
			return false;
		if (couponAmmount != other.couponAmmount)
			return false;
		if (couponEndDate == null) {
			if (other.couponEndDate != null)
				return false;
		} else if (!couponEndDate.equals(other.couponEndDate))
			return false;
		if (couponId != other.couponId)
			return false;
		if (couponImage == null) {
			if (other.couponImage != null)
				return false;
		} else if (!couponImage.equals(other.couponImage))
			return false;
		if (couponMessage == null) {
			if (other.couponMessage != null)
				return false;
		} else if (!couponMessage.equals(other.couponMessage))
			return false;
		if (Double.doubleToLongBits(couponPrice) != Double.doubleToLongBits(other.couponPrice))
			return false;
		if (couponStartDate == null) {
			if (other.couponStartDate != null)
				return false;
		} else if (!couponStartDate.equals(other.couponStartDate))
			return false;
		if (couponTitle == null) {
			if (other.couponTitle != null)
				return false;
		} else if (!couponTitle.equals(other.couponTitle))
			return false;
		if (couponType != other.couponType)
			return false;
		return true;
	}
	


}