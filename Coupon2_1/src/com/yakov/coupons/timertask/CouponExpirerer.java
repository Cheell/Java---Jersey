package com.yakov.coupons.timertask;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.yakov.coupons.dao.CouponDao;
import com.yakov.coupons.exceptions.ApplicationException;
import com.yakov.coupons.utils.DateUtils;

/**
 * Expires coupons that are too old
 * @author Yakov
 *
 */
public class CouponExpirerer extends TimerTask{

	public void run(){
		try {
			CouponDao couponDao = new CouponDao();	
			//Getting all coupons that are expired.
			List<Long> couponIdList = couponDao.getCouponIdsWhichEndDateIsOlderThanDate(DateUtils.getCurrentDate());
			//removing those coupons
			for(long i : couponIdList) {
				couponDao.removeCouponsFromCustomerCouponsByCouponId(i);
				couponDao.removeCouponByCouponId(i);
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
			System.out.println("Failed to preform schedueled coupon removing!");
		}
	}
		
	public static void expire() throws ParseException {
		Timer timer = new Timer();
		DateFormat ds = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
		Date startDate = ds.parse("1990-01-01 00:01:00");
		//runs every day at 1 first minute of a new day 
		//and of course every server start up
		timer.scheduleAtFixedRate(new CouponExpirerer(), startDate, 24*60*60*1000);
	}
}