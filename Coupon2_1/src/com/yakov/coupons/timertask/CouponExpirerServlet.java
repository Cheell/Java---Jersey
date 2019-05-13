package com.yakov.coupons.timertask;

import java.text.ParseException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * Coupon Expirer API 
 * @author Yakov
 *
 */
@WebServlet("/Expire")
public class CouponExpirerServlet extends HttpServlet{

	private static final long serialVersionUID = -5928384099721665990L;

	/**
	 * Simply a gateway to launch coupon-expirer thread on server-side, with server launch.
	 * @throws ParseException
	 */
	public CouponExpirerServlet() throws ParseException {
		CouponExpirerer.expire();
	}
}
