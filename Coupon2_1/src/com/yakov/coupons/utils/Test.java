package com.yakov.coupons.utils;


import java.sql.SQLException;

import com.yakov.coupons.beans.Company;
import com.yakov.coupons.beans.Coupon;
import com.yakov.coupons.beans.Customer;
import com.yakov.coupons.controller.CompanyController;
import com.yakov.coupons.controller.CouponController;
import com.yakov.coupons.controller.CustomerController;
import com.yakov.coupons.dao.CompanyDao;
import com.yakov.coupons.dao.CouponDao;
import com.yakov.coupons.dao.CustomerDao;
import com.yakov.coupons.exceptions.ApplicationException;
import com.yakov.coupons.utils.JdbcUtils;
import com.yakov.coupons.timertask.CouponExpirerer;
import com.yakov.coupons.enums.CouponTypes;
import com.yakov.coupons.utils.DateUtils;

@SuppressWarnings("unused")
public class Test {

	public static void main(String[] args) throws SQLException, InterruptedException, ApplicationException {
		try {
			JdbcUtils.getConnection();
			Thread.sleep(3000);
		
/*	 		Company company = new Company();
			company.setCompanyEmail("si@pei.com");
	 		company.setCompanyName("!");
			company.setCompanyPassword("1354");
			company.setCompanyId(24);
			CompanyDao companyDao = new CompanyDao();
			companyDao.createCompany(company);
						
			CompanyDao companyDao = new CompanyDao();
			System.out.println(companyDao.getCompanyByCompanyId(3));	
			System.out.println(companyDao.getAllCompanies());
			System.out.println(companyDao.removeCompanyById(5));
	
	
			company.setCompanyName("pepsiLLL");
			company.setCompanyEmail("pepsi@pepsiOO.com");
			company.setCompanyPassword("33299");
			company.setCompanyId(3);
			System.out.println(companyDao.updateCompany(company));
	
			CompanyDao companyDao = new CompanyDao();
			System.out.println(companyDao.companyLogin("pepsi", "332"));
			
			
			Customer customer = new Customer();
			customer.setCustomerId(1);
			customer.setCustomerName("splinter");
			customer.setCustomerPassword("88777");
			
			CustomerDao customerDao = new CustomerDao();
			System.out.println(customerDao.getCustomerByCustomerName("gggggg"));
			
			
/*			customerDao.createCustomer(customer);
			System.out.println(customerDao.getCustomerByCustomerId(2));	
			System.out.println(customerDao.getAllCustomers());
			System.out.println(customerDao.removeCustomerById(7));
	
			customer.setCustomerName("pepsiLLL");
			customer.setCustomerPassword("33299");
			customer.setCustomerId(3);
			System.out.println(customerDao.updateCustomer(customer));
			System.out.println(customerDao.customerLogin("Don", "222"));
*/
			Coupon coupon = new Coupon();
			coupon.setCouponId(12);
			coupon.setCouponAmmount(23);
			coupon.setCouponEndDate("2023-11-15");
			coupon.setCouponImage("No pic.img");
			coupon.setCouponMessage("Check it out now");
			coupon.setCouponPrice(10);
			coupon.setCouponStartDate("2024-11-16");
			coupon.setCouponTitle("pkl");
			coupon.setCouponType(CouponTypes.CASTLE);
			coupon.setCompanyId(23);

			
			CouponDao couponDao = new CouponDao();
/*			System.out.println(couponDao.getCouponByCouponId(8));
			System.out.println(couponDao.getAllcoupons());
			couponDao.removeCouponByCouponId(3);
			System.out.println(couponDao.getCouponsByType(CouponTypes.LEISURE));
			couponDao.createCoupon(coupon);
			coupon.setCouponId(4);
			coupon.setCouponAmmount(333);
			coupon.setCouponEndDate("01.02.2032");
			coupon.setCouponImage("No pic-pic");
			coupon.setCouponMessage("Boooooring!");
			coupon.setCouponPrice(1000.00);
			coupon.setCouponStartDate("01.01.2032");
			coupon.setCouponTitle("WHo am i?");
			coupon.setCouponType(CouponTypes.VACATION);
			couponDao.updateCoupon(coupon);



			System.out.println(couponDao.getCouponsByCompanyId(2));

			System.out.println(couponDao.getCouponsByCustomerId(1));
			CouponDao couponDao = new CouponDao();
			CustomerDao customer = new CustomerDao();			
			couponDao.purchaseCoupon(couponDao.getCouponByCouponId(2), customer.getCustomerByCustomerId(5));
	
			CompanyController cControl = new CompanyController();
			cControl.createCompany(company);
			cControl.updateCompany(company);
			cControl.companyLogin("!", "1354");

			System.out.println(Integer.parseInt("10.10".replace(".", "")));
			CustomerController cuController = new CustomerController();
			cuController.createCustomer(customer);
			cuController.updateCustomer(customer);
			System.out.println(cuController.getAllCustomers());
			cuController.removeCustomerByCustomerId(10);
			cuController.customerLogin("eo", "556");
*/		
			CouponController coContr = new CouponController();
//			coContr.createCoupon(coupon);
//			System.out.println(coContr.getCouponByCouponId(8));
//			System.out.println(coContr.getCouponByCouponTitle("kot"));
//			System.out.println(coContr.getCouponsByCompanyId(24));
//			System.out.println(coContr.getCouponsByCustomerId(1));
//			System.out.println(coContr.getCouponsByType(CouponTypes.FOOD));
//			coContr.purchaseCoupon(coupon, customer);
//			System.out.println(coContr.getCouponPurchasesByCustomerId(1));
//			System.out.println(coContr.getCouponsByPrice(0, 6));
//			coContr.purchaseCouponAmmount(12, 2, 2);
//			System.out.println(DateUtils.getCurrentDate());
//			System.out.println(couponDao.getCouponsIdByEndDate("2014-01-01"));
//			CouponExpirerer.expire();
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}
}
