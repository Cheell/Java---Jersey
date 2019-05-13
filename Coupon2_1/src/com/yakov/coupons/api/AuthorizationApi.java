package com.yakov.coupons.api;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.yakov.coupons.beans.User;
import com.yakov.coupons.controller.CompanyController;
import com.yakov.coupons.controller.CustomerController;
import com.yakov.coupons.exceptions.ApplicationException;


@Consumes(MediaType.APPLICATION_JSON)

/**
 * Authorization API 
 * @author Yakov
 *
 */

@Path("/Authorize")
public class AuthorizationApi extends HttpServlet {
	private static final long serialVersionUID = 2L;

	/*
	  {
	  	"userName":"Izya",
	  	"userPassword":"justap"
	  }
	 */
	/**
	 * Authorization for Customer
	 * @param user UserLogin object with data required for login
	 * @param request provided HttpServletRequest object
	 * @param response provided HttpServletResponse object
	 * @return 
	 * @return Response status
	 * @throws ApplicationException
	 * @throws IOException 
	 */
	//http://localhost:8080/Coupon2_1/rest/Authorize/asCustomer
	@POST
	@Path("/asCustomer")
	public Response customerLogin(User user, @Context HttpServletRequest request, @Context HttpServletResponse response) throws ApplicationException, IOException {
		CustomerController customerController = new CustomerController();
		customerController.customerLogin(user.getUserName(), user.getUserPassword());
		System.out.println(Response.status(200).build());
		return Response.status(200).build();
	}
	
	/**
	 * Authorization for Company
	 * @param user UserLogin object with data required for login
	 * @param request provided HttpServletRequest object
	 * @param response provided HttpServletResponse object
	 * @return 
	 * @return Response status
	 * @throws ApplicationException
	 */
	//http://localhost:8080/Coupon2_1/rest/Authorize/asCompany
	@POST
	@Path("/asCompany")
	public Response companyLogin(User user, @Context HttpServletRequest request, @Context HttpServletResponse response) throws ApplicationException, IOException {
		CompanyController companyController = new CompanyController();
		companyController.companyLogin(user.getUserName(), user.getUserPassword());
		return Response.status(200).build();
	}

}
