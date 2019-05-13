package com.yakov.coupons.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.yakov.coupons.beans.Coupon;
import com.yakov.coupons.beans.CouponPurchase;
import com.yakov.coupons.controller.CouponController;
import com.yakov.coupons.exceptions.ApplicationException;
import com.yakov.coupons.enums.CouponTypes;

@Path("/Coupons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
/**
 * Coupon API
 * @author Yakov
 *
 */
public class CouponApi {

	private CouponController couponController = new CouponController();
	/**
	 * Creates new Coupon object
	 * @param coupon Object to create
	 * @throws ApplicationException
	 */
	//http://localhost:8080/Coupon2_1/rest/Coupons/
	/*
 	}
	"companyId": "1",
	"couponAmmount": "50",
	"couponEndDate": "2033-02-01",
	"couponImage": "No pic.img",
	"couponMessage": "Wooot???",
	"couponPrice": "11.2",
	"couponStartDate": "2010-01-01",
	"couponTitle": "Ya",
	"couponType": "ELECTRICITY"
	{
	*/
	@POST
	public void createCoupon(Coupon coupon) throws ApplicationException{
		couponController.createCoupon(coupon);
	}
	
	/**
	 * Search for Coupon object by specific id
	 * @param id
	 * @return Coupon object that was found
	 * @throws ApplicationException
	 */
	@GET
	@Path("/{id}/byId")
	//http://localhost:8080/Coupon2_1/rest/Coupons/1/byId
	public Coupon getCouponByCouponId(@PathParam("id")long id) throws ApplicationException {
		return couponController.getCouponByCouponId(id);
	}

	/**
	 * Search for Coupon Object with specific title
	 * @param name title String to search for 
	 * @return Coupon object that was found
	 * @throws ApplicationException if nothing was found
	 */
	@GET
	@Path("/{title}/byTitle")
	//http://localhost:8080/Coupon2_1/rest/Coupons/1/byTitle
	public Coupon getCouponByCouponTitle(@PathParam("title") String name) throws ApplicationException {
		return couponController.getCouponByCouponTitle(name);
	}

	/**
	 * Request for all Coupon objects in DB
	 * @return List of Coupon objects
	 * @throws ApplicationException
	 */
	@GET
	//http://localhost:8080/Coupon2_1/rest/Coupons/
	public List<Coupon> getAllCoupons() throws ApplicationException {
		return couponController.getAllCoupons();
	}

	/**
	 * Removes Coupon object by specific id
	 * @param id id to remove
	 * @throws ApplicationException
	 */
	@DELETE
	@Path("/{id}/byId")
	//http://localhost:8080/Coupon2_1/rest/Coupons/1/byId
	public void removeCouponByCouponId(@PathParam("id") long id) throws ApplicationException {
		couponController.removeCouponByCouponId(id);
	}

	/**
	 * Removes all Coupons objects with specific Company id
	 * @param id company id to remove
	 * @throws ApplicationException
	 */
	@DELETE
	@Path("/byCompanyId")
	//http://localhost:8080/Coupon2_1/rest/Coupons/byCompanyId?id=1
	public void removeCouponsByCompanyId(@QueryParam("id") long id) throws ApplicationException {
		couponController.removeCouponsByCompanyId(id);
	}
	
	/**
	 * Updates Coupon object (by id) to new version
	 * @param coupon Coupon object to update
	 * @throws ApplicationException
	 */
	@PUT
	//http://localhost:8080/Coupon2_1/rest/Coupons/
	public void updateCoupon(Coupon coupon) throws ApplicationException {
		couponController.updateCoupon(coupon);
	}
	
	/**
	 * Search for Coupon objects of specific type
	 * @param type to search for
	 * @return List of Coupon Objects
	 * @throws ApplicationException
	 */
	
	@GET
	@Path("/byType")
	//http://localhost:8080/Coupon2_1/rest/Coupons/byType?type=CASTLE
	public List<Coupon> getCouponsByType(@QueryParam("type") String type) throws ApplicationException {
		return couponController.getCouponsByType(CouponTypes.valueOf(type));
	}

	/**
	 * Search for Coupon objects with specific Company Id
	 * @param type to search for
	 * @return List of Coupon Objects
	 * @throws ApplicationException
	 */
	@GET
	@Path("/byCompId")
	//http://localhost:8080/Coupon2_1/rest/Coupons/byCompId?id=35
	public List<Coupon> getCouponsByCompanyId(@QueryParam("id")long id) throws ApplicationException {
		System.out.println(id);
		return couponController.getCouponsByCompanyId(id);
	}

	/**
	 * Search for Coupon objects with specific Company Id
	 * @param type to search for
	 * @return List of Coupon Objects
	 * @throws ApplicationException
	 */
	@GET
	@Path("/byCompName")
	//http://localhost:8080/Coupon2_1/rest/Coupons/byCompName?name=Cheap
	public List<Coupon> getCouponsByCompanyName(@QueryParam("name") String name) throws ApplicationException {
		return couponController.getCouponsByCompanyName(name);
	}

	/**
	 * Search for purchases of specific customer by id
	 * @param customerId customer id to search for
	 * @return List of CouponPurchase objects
	 * @throws ApplicationException
	 */
	@GET
	@Path("/purchasesByCustomerId")
	//http://localhost:8080/Coupon2_1/rest/Coupons/purchasesByCustomerId?id=1
	public List<CouponPurchase> getCouponPurchasesByCustomerId(@QueryParam("id")long customerId) throws ApplicationException {
		return couponController.getCouponPurchasesByCustomerId(customerId);
	}
	
	/**
	 * Search for Coupon objects with price between two "borders"
	 * @param couponPriceFrom lower border
	 * @param couponPriceTo upper border
	 * @return List of Coupon objects
	 * @throws ApplicationException
	 */
	@GET
	@Path("/byPrice")
	//http://localhost:8080/Coupon2_1/rest/Coupons/byPrice?from=1&to=100
	public List<Coupon> getCouponsByPrice(@QueryParam("from") double couponPriceFrom, @QueryParam("to") double couponPriceTo) throws ApplicationException {
		return couponController.getCouponsByPrice(couponPriceFrom, couponPriceTo);
	}

	
	
	
	/**
	 * Add CouponPurchase object to DB
	 * @param purchase CouponPurchase to add
	 * @throws ApplicationException
	 */
	@POST
	@Path("/purchase")
	//http://localhost:8080/Coupon2_1/rest/Coupons/purchase
	public void purchaseCouponAmmount(CouponPurchase purchase) throws ApplicationException {
		couponController.makePurchase(purchase);
	}
}