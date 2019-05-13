package com.yakov.coupons.api;
/**
 * API for Company object
 */
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

import com.yakov.coupons.beans.Company;
import com.yakov.coupons.controller.CompanyController;
import com.yakov.coupons.cookies.CookieUtils;
import com.yakov.coupons.exceptions.ApplicationException;

@Path("/Companies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
/**
 * 
 * @author Yakov
 * Company API
 */
public class CompanyApi {
	private CompanyController companyController = new CompanyController();

	/**
	 * Creates new Company object
	 * @param company company to create
	 * @throws ApplicationException
	 */
	/*{
	 *	
		companyName = Moppar;
		companyPassword = asdkafjhg;
		companyEmail = addw@dajds.hgj;
	 * 
	 * 
	 *}
	 */
	@POST
	//http://localhost:8080/Coupon2_1/rest/Companies/
	public void createCompany(Company company) throws ApplicationException{
		companyController.createCompany(company);
	}
	
	/**
	 * Search for Company with specific id provided
	 * @param id id to search for
	 * @return Company object that was found
	 * @throws ApplicationException if nothing was found
	 */
	@GET
	@Path("/{id}/byId")
	//http://localhost:8080/Coupon2_1/rest/Companies/1/byId
	public Company getCompanyByCompanyId(@PathParam("id") long id) throws ApplicationException {
		return companyController.getCompanyByCompanyId(id);
	}
	
	/**
	 * Search for Company with specific name
	 * @param name name to search for
	 * @return Company object that was found
	 * @throws ApplicationException if nothing was found
	 */
	@GET
	@Path("/{name}/byName")
	//http://localhost:8080/Coupon2_1/rest/Companies/!/byName
	public Company getCompanyByCompanyName(@PathParam("name") String name) throws ApplicationException {
		return companyController.getCompanyByCompanyName(name);
	}

	/**
	 * Search Company with specific email
	 * @param email email string to search for Company object
	 * @return Company object that was found
	 * @throws ApplicationException if nothing was found
	 */
	@GET
	@Path("/{email}/byEmail")
	//http://localhost:8080/Coupon2_1/rest/Companies/si@pei.com/byEmail
	public Company getCompanyByCompanyEmail(@PathParam("email") String email) throws ApplicationException {
		return companyController.getCompanyByCompanyEmail(email);
	}

	/**
	 * For security reason we can't use getCompanyById,
	 * cause this way any customer may get information about any other companies,
	 * and we don't want that to happen!
	 * So this is a secure version of getCompany. 
	 * @param id to search for
	 * @return customer object that was found
	 * @throws ApplicationException if nothing was found
	 */
	@GET
	@Path("/{name}/{password}/byNameAndPassword")
	//http://localhost:8080/Coupon2_1/rest/Companies/{name}/{password}/byNameAndPassword
	public Company getCompanyByCompanyNameAndPassword(@PathParam("name") String name, @PathParam("password") String password)throws ApplicationException {
		companyController.companyLogin(name, password);
		return companyController.getCompanyByCompanyName(name);
	}
	
	/**
	 * For security reason we can't use getCompanyById,
	 * cause this way any customer may get information about any other companies,
	 * and we don't want that to happen!
	 * So this is a secure version of getCompany. 
	 * @param id to search for
	 * @return customer object that was found
	 * @throws ApplicationException if nothing was found
	 */
	@GET
	@Path("/{id}/nameById")
	//http://localhost:8080/Coupon2_1/rest/Companies/31/nameById
	public Company getCompanyNameByCompanyId(@PathParam("id") long id)throws ApplicationException {
		Company company = companyController.getCompanyByCompanyId(id);
		company.setCompanyEmail(null);
		company.setCompanyPassword(null);
		return company;
	}

	
	/**
	 * Requests all Company objects from DB
	 * @return List of Company objects
	 * @throws ApplicationException
	 */
	@GET
	//http://localhost:8080/Coupon2_1/rest/Companies/
	public List<Company> getAllCompanies() throws ApplicationException {
		return companyController.getAllCompanies();
	}
	
	/**
	 * Removes Company object with specific id from DB.
	 * @param id to remove Company
	 * @throws ApplicationException 
	 */
	@DELETE
	@Path("/{id}")
	//http://localhost:8080/Coupon2_1/rest/Companies/
	public void removeCompanyByCompanyId(@PathParam("id") long id) throws ApplicationException {
		companyController.removeCompanyByCompanyId(id);
	}
	
	/**
	 * Updates Company (by id) to new version, that was provided
	 * Checks if our current cookie show that user is logged in as company
	 * Updates cookies
	 * @param company to update
	 * @throws ApplicationException
	 */
	@PUT
	//http://localhost:8080/Coupon2_1/rest/Companies/
	public Response updateCompany(Company company, @Context HttpServletRequest request, @Context HttpServletResponse response) throws ApplicationException {
		companyController.updateCompany(company);
		HttpSession session = request.getSession(false);
		Cookie[] cookies = request.getCookies();
		if (session!=null && cookies != null) {
			for(Cookie cookie: cookies) {
				if(cookie.getName().equals(CookieUtils.type) && cookie.getValue().equals(CookieUtils.companyType)) {
					CookieUtils cl = new CookieUtils(company);
					request.getSession();
					response = cl.addCookies(response);
				}
			}
		} 
		return Response.status(200).build();
	}

}
