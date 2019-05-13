package com.yakov.coupons.api;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.yakov.coupons.beans.User;
import com.yakov.coupons.controller.CompanyController;
import com.yakov.coupons.controller.CustomerController;
import com.yakov.coupons.cookies.CookieUtils;
import com.yakov.coupons.exceptions.ApplicationException;

@Consumes(MediaType.APPLICATION_JSON)


/**
 * Login Api 
 * @author Yakov
 *
 */

@Path("/Login")
public class LoginApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	/*
	  {
	  	"userName":"Izya",
	  	"userPassword":"justap"
	  }
	 */
	
	/**
	 * Login for Customer
	 * @param user UserLogin object with data required for login
	 * @param request provided HttpServletRequest object
	 * @param response provided HttpServletResponse object
	 * @return Response status
	 * @throws ApplicationException
	 */
	//http://localhost:8080/Coupon2_1/rest/Login/asCustomer
	@POST
	@Path("/asCustomer")
	public Response customerLogin(User user, @Context HttpServletRequest request, @Context HttpServletResponse response) throws ApplicationException {
		CustomerController customerController = new CustomerController();
		customerController.customerLogin(user.getUserName(), user.getUserPassword()); 
		CookieUtils cl = new CookieUtils(customerController.getCustomerByCustomerName(user.getUserName()));
		request.getSession(true);
		response = cl.addCookies(response);
		return Response.status(200).build();
	}
	
	/**
	 * Logout for any kind of user
	 * @param user UserLogin object with data required for login
	 * @param request provided HttpServletRequest object
	 * @param response provided HttpServletResponse object
	 * @return Response status
	 * @throws ApplicationException
	 */
	//http://localhost:8080/Coupon2_1/rest/Login/asCompany
	@POST
	@Path("/asCompany")
	public Response companyLogin(User user, @Context HttpServletRequest request, @Context HttpServletResponse response) throws ApplicationException {
		CompanyController companyController = new CompanyController();
		companyController.companyLogin(user.getUserName(), user.getUserPassword());
		CookieUtils cl = new CookieUtils(companyController.getCompanyByCompanyName(user.getUserName()));
		request.getSession(true);
		response = cl.addCookies(response);
		return Response.status(200).build();
	}

	
	/**
	* Logout for any kind of user
	* deletes users session
	* @return Response status
	* @throws ApplicationException
	*/
	//I don't want to create another class to put Logout there.
	// Maybe putting Logout in Login class is not the best thing to do, but next time ill just stick to auth class.
	
	@GET
	@Path("/Logout")
	public Response logout(@Context HttpServletRequest request, @Context HttpServletResponse response) throws ApplicationException {
		request.getSession(false).invalidate();
		return Response.status(200).build();
	}
}
