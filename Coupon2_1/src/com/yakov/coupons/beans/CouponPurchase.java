package com.yakov.coupons.beans;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Yakov
 *	Wrapper for coupon and amount.
 *  Holds coupon Id and amount of that coupon bought.
 *  and customerId that made that purchase.
 */
@XmlRootElement
public class CouponPurchase {
	private long customerId;
	private long couponId;
	private int amount;
	private Coupon coupon;
	
	public long getCouponId() {
		return couponId;
	}
	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}
	public int getAmount() {
		return amount;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}
	
	public Coupon getCoupon() {
		return this.coupon;
	}

	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "CouponPurchase [customerId=" + customerId + ", couponId=" + couponId + ", amount=" + amount + "]";
	}
	public long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
}
