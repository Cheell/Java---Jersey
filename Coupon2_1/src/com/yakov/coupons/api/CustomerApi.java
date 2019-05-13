package com.yakov.coupons.api;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.yakov.coupons.beans.Customer;
import com.yakov.coupons.controller.CustomerController;
import com.yakov.coupons.cookies.CookieUtils;
import com.yakov.coupons.exceptions.ApplicationException;

@Path("/Customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

/**
 * 
 * @author Yakov
 * Customer API
 */
public class CustomerApi {

	private CustomerController customerController = new CustomerController();
	
	/**
	 * Create new customer by sending data to Controller
	 * @param customer - customer to be created
	 * @throws ApplicationException 
	 */
	@POST
	//http://localhost:8080/Coupon2_1/rest/Customers/
	public void createCustomer(Customer customer) throws ApplicationException {
		customerController.createCustomer(customer);
	}

	/**
	 * Search DB for customer with specific id
	 * @param id to search for
	 * @return customer object that was found
	 * @throws ApplicationException if nothing was found
	 */
	@GET
	@Path("/{id}/byId")
	//http://localhost:8080/Coupon2_1/rest/Customers/byId
	public Customer getCustomerByCustomerId(@PathParam("id") long id)throws ApplicationException {
		return customerController.getCustomerByCustomerId(id);
	}

	/**
	 * For security reason we can't use getCustomerById,
	 * cause this way any customer may get information about any other customer,
	 * and we don't want that to happen!
	 * So this is a secure version of getCustomer. 
	 * @param id to search for
	 * @return customer object that was found
	 * @throws ApplicationException if nothing was found
	 */
	@GET
	@Path("/{name}/{password}/byNameAndPassword")
	//http://localhost:8080/Coupon2_1/rest/Customers/{name}/{password}/byNameAndPassword
	public Customer getCustomerByCustomerNameAndPassword(@PathParam("name") String name, @PathParam("password") String password)throws ApplicationException {
		customerController.customerLogin(name, password);
		return customerController.getCustomerByCustomerName(name);
	}

	/**
	 * Search DB for customer with specific name
	 * @param name to search for
	 * @return customer object that was found
	 * @throws ApplicationException if nothing was found
	 */
	@GET
	@Path("/{name}/byName")
	//http://localhost:8080/Coupon2_1/rest/Customers/byName
	public Customer getCustomerByCustomerName(@PathParam("name") String name)throws ApplicationException {
		return customerController.getCustomerByCustomerName(name);
	}
	
	
	/**
	 * Requests data of all customers from DB
	 * @return all customers in DB
	 * @throws ApplicationException
	 */
	@GET
	//http://localhost:8080/Coupon2_1/rest/Customers/
	public List<Customer> getAllCustomers() throws ApplicationException {
		return customerController.getAllCustomers();
	}
	
	/**
	 * Removes specific id customer from DB
	 * @param id to remove
	 * @throws ApplicationException
	 */
	@DELETE
	@Path("/{id}")
	//http://localhost:8080/Coupon2_1/rest/Customers/
	public void removeCustomerByCustomerId(@PathParam("id") long id)throws ApplicationException {
		customerController.removeCustomerByCustomerId(id);
	}

	/**
	 * Updates specific customer using customer id to search
	 * @param customer to update
	 * @throws ApplicationException
	 */
	@PUT
	//http://localhost:8080/Coupon2_1/rest/Customers/
	public Response updateCustomer(Customer customer, @Context HttpServletRequest request, @Context HttpServletResponse response) throws ApplicationException {
		customerController.updateCustomer(customer);
		HttpSession session = request.getSession(false);
		Cookie[] cookies = request.getCookies();
		if (session!=null && cookies != null) {
			for(Cookie cookie: cookies) {
				if(cookie.getName().equals(CookieUtils.type) && cookie.getValue().equals(CookieUtils.customerType)) {
					CookieUtils cl = new CookieUtils(customer);
					request.getSession();
					response = cl.addCookies(response);
				}
			}
		}		
		return Response.status(200).build();
	}

}
