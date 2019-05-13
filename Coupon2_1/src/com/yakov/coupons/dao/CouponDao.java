package com.yakov.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.yakov.coupons.beans.Coupon;
import com.yakov.coupons.beans.CouponPurchase;
import com.yakov.coupons.exceptions.ApplicationException;
import com.yakov.coupons.enums.ErrorType;
import com.yakov.coupons.enums.CouponTypes;
import com.yakov.coupons.utils.DateUtils;
import com.yakov.coupons.utils.JdbcUtils;

/**
 * 
 * @author Yakov
 *	Dao that works with Coupons.
 */
public class CouponDao{

	/**
	 * Adds new coupon to the coupon table.
	 * @param coupon to add to the table.
	 */
	public void createCoupon(Coupon coupon) throws ApplicationException {

		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = null;

		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();
			
			
			
			// Creating a string which will contain the query
			// PAY ATTENTION - BY USING THE ? (Question marks) WE PREVENT AN SQL INJECTION ATTACK
			String sql = "insert into coupon (COUP_TITLE, COUP_START_DATE, COUP_END_DATE, COUP_AMMOUNT, COUP_TYPE, COUP_MESSAGE, COUP_PRICE, COUP_IMAGE, COMP_ID) "
					+ "values (?,STR_TO_DATE(?, '%Y-%m-%d'),STR_TO_DATE(?, '%Y-%m-%d'),?,?,?,?,?,?)";

			preparedStatement= connection.prepareStatement(sql);

			preparedStatement.setString(1, coupon.getCouponTitle());
			preparedStatement.setString(2, coupon.getCouponStartDate());
			preparedStatement.setString(3, coupon.getCouponEndDate());
			preparedStatement.setInt(4, coupon.getCouponAmmount());
			preparedStatement.setString(5, coupon.getCouponType().name());
			preparedStatement.setString(6, coupon.getCouponMessage());
			preparedStatement.setDouble(7, coupon.getCouponPrice());
			preparedStatement.setString(8, coupon.getCouponImage());
			preparedStatement.setLong(9, coupon.getCompanyId());
			preparedStatement.executeUpdate();
			
		} 

		catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CouponDao, creatCoupon(); FAILED");
		} 

		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	/**
	 * Finds a coupon by coupon ID.
	 * @param couponId Id to look for the coupon.
	 * @return coupon.
	 * @throws ApplicationException 
	 */
	public Coupon getCouponByCouponId(long couponId) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Coupon coupon = null;

		try {
			connection = JdbcUtils.getConnection();
			String sql = "SELECT coupon.*, company.COMP_NAME FROM coupon LEFT JOIN company ON coupon.COMP_ID = company.COMP_ID WHERE COUP_ID = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, couponId);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.next()) {
				return coupon;
			}
			coupon = extractCouponFromResultSet(resultSet);
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CouponDao, getCouponByCouponId(long couponId); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		return coupon;
	}

	/**
	 * Finds a coupon by coupon Title.
	 * @param coupon Title to look for the coupon.
	 * @return coupon.
	 * @throws ApplicationException 
	 */
	public Coupon getCouponByCouponTitle(String couponTitle) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Coupon coupon = null;

		try {
			connection = JdbcUtils.getConnection();
			String sql = "SELECT coupon.*, company.COMP_NAME FROM coupon LEFT JOIN company ON coupon.COMP_ID = company.COMP_ID WHERE COUP_TITLE = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, couponTitle);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.next()) {
				return coupon;
			}
			coupon = extractCouponFromResultSet(resultSet);
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, "Error in CouponDao, getCouponByCouponTitle(String couponTitle); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		return coupon;
	}
		
	/**
	 * Creates coupon from Data Base data.
	 * @param resultSet - holds data to extract.
	 * @return coupon object.
	 * @throws Exception
	 */
	private Coupon extractCouponFromResultSet(ResultSet resultSet) throws Exception {
		Coupon coupon = new Coupon();
		coupon.setCouponId(resultSet.getLong("COUP_ID"));
		coupon.setCouponTitle(resultSet.getString("COUP_TITLE"));
		coupon.setCouponStartDate(resultSet.getString("COUP_START_DATE"));
		coupon.setCouponEndDate(resultSet.getString("COUP_END_DATE"));
		coupon.setCouponAmmount(resultSet.getInt("COUP_AMMOUNT"));
		coupon.setCouponType(CouponTypes.valueOf(resultSet.getString("COUP_TYPE")));
		coupon.setCouponMessage(resultSet.getString("COUP_MESSAGE"));
		coupon.setCouponPrice(resultSet.getDouble("COUP_PRICE"));
		coupon.setCouponImage(resultSet.getString("COUP_IMAGE"));
		coupon.setCompanyId(resultSet.getLong("COMP_ID"));
		coupon.setCompanyName(resultSet.getString("COMP_NAME"));
		return coupon;
	}

	/**
	 * Extracts data of all coupons from Data Base.
	 * @return List of all coupons.
	 * @throws ApplicationException 
	 */
	public List<Coupon> getAllcoupons() throws ApplicationException{
		
		List<Coupon> couponsList = new ArrayList<>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = JdbcUtils.getConnection();
			String sql = "SELECT coupon.*, company.COMP_NAME FROM coupon LEFT JOIN company ON coupon.COMP_ID = company.COMP_ID";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.first()) {
				return couponsList;
			} else {
				resultSet.beforeFirst();				
			}
			while (resultSet.next()) {
				couponsList.add(extractCouponFromResultSet(resultSet));
			}
		}

		catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CouponDao, getAllcoupons(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}		
		return couponsList;
	}
	
	/**
	 * Deletes all coupons with specific company id from coupon table.
	 * @param companyId id of the company to search and delete.
	 * @throws ApplicationException.
	 */
	public void removeCouponsByCompanyId(long companyId) throws ApplicationException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = JdbcUtils.getConnection();
			String sql = "DELETE FROM coupon WHERE COMP_ID = ? ";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, companyId);
			preparedStatement.executeUpdate();
		}

		catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CouponDao, removeCouponsByCompanyId(long companyId); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	
	/**
	 * Deletes coupons with specific id from customer_coupons table.
	 * @param id coupon id to search for and delete.
	 * @throws ApplicationException.
	 */
	public void removeCouponsFromCustomerCouponsByCouponId(long id) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = JdbcUtils.getConnection();
			String sql = "DELETE FROM customer_coupon WHERE COUP_ID = ? ";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, id);
			preparedStatement.executeUpdate();
		}

		catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CouponDao, removeCouponsFromCustomerCouponsByCouponId; FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}		
	}
	
	
	/**
	 * Removes coupon with certain ID from coupon table. 
	 * @param couponId coupon id to remove.
	 * @throws ApplicationException 
	 */
	public void removeCouponByCouponId(long couponId) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = JdbcUtils.getConnection();
			String sql = "DELETE FROM coupon WHERE COUP_ID = ? ";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, couponId);
			preparedStatement.executeUpdate();
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CouponDao, removeCouponByCouponId; FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	
	/**
	 * Updates data of certain coupon using coupon id as a search parameter.
	 * @param coupon - coupon thats we update, and get id from.
	 * @throws ApplicationException 
	 */
	public void updateCoupon(Coupon coupon) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
				
		try {
			connection = JdbcUtils.getConnection();
			String sql = 
					"UPDATE coupon SET COUP_TITLE = ?, COUP_START_DATE = ?, COUP_END_DATE = ?, COUP_AMMOUNT = ?,"
					+ "  COUP_TYPE = ?, COUP_MESSAGE = ?, COUP_PRICE = ?, COMP_ID = ?, COUP_IMAGE = ? WHERE COUP_ID = ?";

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, coupon.getCouponTitle());
			preparedStatement.setString(2, coupon.getCouponStartDate());
			preparedStatement.setString(3, coupon.getCouponEndDate());
			preparedStatement.setInt(4, coupon.getCouponAmmount());
			preparedStatement.setString(5, coupon.getCouponType().name());
			preparedStatement.setString(6, coupon.getCouponMessage());
			preparedStatement.setDouble(7, coupon.getCouponPrice());
			preparedStatement.setLong(8, coupon.getCompanyId());
			preparedStatement.setString(9, coupon.getCouponImage());
			preparedStatement.setLong(10, coupon.getCouponId());
			preparedStatement.executeUpdate();
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CouponDao, updateCoupon(Coupon coupon); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	
	/**
	 * Finds all coupons with specific type.
	 * @param couponType - type to search for.
	 * @return List of coupons with specific type.
	 * @throws ApplicationException 
	 */
	public List<Coupon> getCouponsByType(CouponTypes couponType) throws ApplicationException{
		List<Coupon> couponByTypeList = new ArrayList<>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = JdbcUtils.getConnection();
			String sql = "SELECT coupon.*, company.COMP_NAME FROM coupon LEFT JOIN company ON coupon.COMP_ID = company.COMP_ID WHERE COUP_TYPE = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, couponType.name());
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.first()) {
				return couponByTypeList;
			} else {
				resultSet.beforeFirst();				
			}
			while (resultSet.next()) {
				couponByTypeList.add(extractCouponFromResultSet(resultSet));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() +
					"Error in CouponDao, getCouponByType(CouponTypes couponType); FAILED");			
		}
		return couponByTypeList;
	}

	
	
	
	/**
	 * Searches for all coupons with specific company ID.
	 * @param companyId id to search for.
	 * @return List of coupons that were found.
	 * @throws ApplicationException. 
	 */
	public List<Coupon> getCouponsByCompanyId(long companyId) throws ApplicationException{		
		List<Coupon> couponList = new ArrayList<>(); 
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = JdbcUtils.getConnection();
			String sql = "SELECT DISTINCT coupon.*, company.COMP_NAME FROM coupon LEFT JOIN company ON coupon.COMP_ID = company.COMP_ID WHERE coupon.COMP_ID = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, companyId);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.next()) {
				return couponList;
			} else {
				resultSet.beforeFirst();				
			}
			while (resultSet.next()) {
				couponList.add(extractCouponFromResultSet(resultSet));
			}

		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CouponDao, getCouponByCompanyId(long companyId); FAILED");
		}
		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}		
		return couponList;
	}	

	/**
	 * Searches for all coupons with specific company ID.
	 * @param companyName id to search for.
	 * @return List of coupons that were found.
	 * @throws ApplicationException. 
	 */
	public List<Coupon> getCouponsByCompanyName(String companyName) throws ApplicationException{		
		List<Coupon> couponList = new ArrayList<>(); 
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = JdbcUtils.getConnection();
			String sql = "SELECT coupon.*, company.COMP_NAME FROM coupon LEFT JOIN company ON coupon.COMP_ID = company.COMP_ID where COMP_NAME = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, companyName);

			resultSet = preparedStatement.executeQuery();
			
			if (!resultSet.next()) {
				return couponList;
			} else {
				resultSet.beforeFirst();				
			}
			while (resultSet.next()) {
				couponList.add(extractCouponFromResultSet(resultSet));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CouponDao, getCouponByCompanyId(long companyId); FAILED");
		}
		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}		
		
		return couponList;
	}	
	
	/**
	 * Searches for all purchases by certain customer.
	 * @param customerId id to search for.
	 * @return List of coupon purchases that were found.
	 * @throws ApplicationException 
	 */
	public List<CouponPurchase> getCouponPurchasesByCustomerId(long customerId) throws ApplicationException{
		
		ArrayList<CouponPurchase> couponPurchases = new ArrayList<>(); 
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = JdbcUtils.getConnection();
			String sql = "SELECT * FROM customer_coupon LEFT JOIN coupon ON customer_coupon.COUP_ID = coupon.COUP_ID  left join company on coupon.COMP_ID = company.COMP_ID where CUST_ID = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, customerId);

			resultSet = preparedStatement.executeQuery();
			
			if (!resultSet.next()) {
				return couponPurchases;
			} else {
				resultSet.beforeFirst();				
			}
			
			while (resultSet.next()) {
				CouponPurchase couponPurchase = new CouponPurchase();
				couponPurchase.setCouponId(resultSet.getLong("COUP_ID"));
				couponPurchase.setCustomerId(resultSet.getLong("CUST_ID"));
				couponPurchase.setAmount(resultSet.getInt("COUP_AMOUNT"));
				couponPurchase.setCoupon(extractCouponFromResultSet(resultSet));
				couponPurchases.add(couponPurchase);
			}
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CouponDao, getCouponsByCustomerId(long customerId); FAILED");
		}
		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}		
		return couponPurchases;
	}
	
	
	
	/**
	 * Adds a new row to the customer_coupon table.
	 * @param couponId id if the coupon that was bought.
	 * @param customerId id of the buyer.
	 * @param amount amount of coupons that were bought.
	 * @throws ApplicationException
	 */
	public void addCustomerCoupon(long couponId, long customerId, int amount) throws ApplicationException{

		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = null;

		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();
			
			
			// Creating a string which will contain the query
			// PAY ATTENTION - BY USING THE ? (Question marks) WE PREVENT AN SQL INJECTION ATTACK
			String sql = "INSERT INTO customer_coupon (CUST_ID, COUP_ID, COUP_AMOUNT) values (?,?,?)";

			preparedStatement= connection.prepareStatement(sql);

			preparedStatement.setLong(1, customerId);
			preparedStatement.setLong(2, couponId);
			preparedStatement.setInt(3, amount);
			preparedStatement.executeUpdate();
		} 

		catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CouponDao, createNewRow(); FAILED");
		} 

		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	/**
	 * Updates amount of coupon with certain id.
	 * @param couponId id of coupon that was bought.
	 * @param ammountNew new amount of coupons. 
	 * @throws ApplicationException
	 */
	public void updateCouponAmmount(long couponId, int ammountNew) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
				
		try {
			connection = JdbcUtils.getConnection();
			String sql = "UPDATE coupon SET COUP_AMMOUNT = ? WHERE COUP_ID = ?";

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, ammountNew);			
			preparedStatement.setLong(2, couponId);			
			preparedStatement.executeUpdate();
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CouponDao, updateCouponAmmount; FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	/**
	 * Finds all coupons with price that is between 2 prices set by parameters.
	 * @param couponPriceFrom first price to start the search from - the lesser one of two.
	 * @param couponPriceTo second price to search up to that pricer - the bigger one.
	 * @return list of coupons which prices are in the range determined by parameters of the function.
	 * @throws ApplicationException.
	 */
	public List<Coupon> getCouponsByPrice(double couponPriceFrom, double couponPriceTo) throws ApplicationException {
		List<Coupon> couponsList = new ArrayList<>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = JdbcUtils.getConnection();
			String sql = "SELECT coupon.*, company.COMP_NAME FROM coupon LEFT JOIN company ON coupon.COMP_ID = company.COMP_ID WHERE COUP_PRICE BETWEEN ? AND ?";

			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setDouble(1, couponPriceFrom);			
			preparedStatement.setDouble(2, couponPriceTo);		
			preparedStatement.executeQuery();
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.first()) {
				return couponsList;
			} else {
				resultSet.beforeFirst();				
			}
			while (resultSet.next()) {
				couponsList.add(extractCouponFromResultSet(resultSet));
			}

		}

		catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CouponDao, getCouponsByPrice; FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
		
		return couponsList;
	}
	
	/**
	 * Finds Ids of all coupons with end date smaller than parameter (in other words end date that is older than the parameter).
	 * @param date to search for.
	 * @return list of ids.
	 * @throws ApplicationException.
	 */
	public List<Long> getCouponIdsWhichEndDateIsOlderThanDate(String date) throws ApplicationException{		
		List<Long> couponIds = new ArrayList<>(); 
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = JdbcUtils.getConnection();
			String sql = "SELECT COUP_ID FROM coupon where COUP_END_DATE < ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, date);

			resultSet = preparedStatement.executeQuery();
			
			if (!resultSet.next()) {
				return couponIds;
			} else {
				resultSet.beforeFirst();				
			}
			while (resultSet.next()) {				
				couponIds.add(resultSet.getLong("COUP_ID"));
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CouponDao, getCouponsIdByEndDate; FAILED");
		}
		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}		
		
		return couponIds;
	}	
}