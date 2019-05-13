package com.yakov.coupons.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.yakov.coupons.beans.Customer;
import com.yakov.coupons.exceptions.ApplicationException;
import com.yakov.coupons.enums.ErrorType;
import com.yakov.coupons.utils.DateUtils;
import com.yakov.coupons.utils.JdbcUtils;

/**
 * 
 * @author Yakov
 *	Dao that works with Customers.
 */
public class CustomerDao{

	/**
	 * Adds new customer to the customer table.
	 * @param customer to add to the table.
	 */
	public void createCustomer(Customer customer) throws ApplicationException {

		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = null;

		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();
			
			
			
			// Creating a string which will contain the query
			// PAY ATTENTION - BY USING THE ? (Question marks) WE PREVENT AN SQL INJECTION ATTACK
			String sql = "insert into customer (cust_name, cust_password) values (?,?)";

			preparedStatement= connection.prepareStatement(sql);

			preparedStatement.setString(1, customer.getCustomerName());
			preparedStatement.setString(2, customer.getCustomerPassword());
			preparedStatement.executeUpdate();
		} 

		catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() 
					+ "Error in CustomerDao, creatCustomer(); FAILED");
		} 

		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	/**
	 * Finds a customer by customer ID.
	 * @param customerId Id to look for the customer.
	 * @throws ApplicationException 
	 * @return customer
	 */
	public Customer getCustomerByCustomerId(long customerId) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Customer customer = null;

		try {
			connection = JdbcUtils.getConnection();
			String sql = "SELECT * FROM customer WHERE CUST_ID = ? ";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, customerId);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.next()) {
				return customer;
			}
			customer = extractCustomerFromResultSet(resultSet);

		}

		catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CustomerDao, getCustomerByCustomerId; FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		return customer;
	}

	/**
	 * Finds a customer by customer name.
	 * @param customerName - name to look for the customer.
	 * @throws ApplicationException 
	 * @return customer
	 */
	public Customer getCustomerByCustomerName(String customerName) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Customer customer = null;

		try {
			connection = JdbcUtils.getConnection();
			String sql = "SELECT * FROM customer WHERE CUST_NAME = ? ";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, customerName);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.next()) {
				return customer;
			}
			customer = extractCustomerFromResultSet(resultSet);

		}

		catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CustomerDao, getCustomerByCustomerName; FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		return customer;
	}
	
	/**
	 * Extracts data from received Data Base output to create customer from it.
	 * @param resultSet database output to extract from.
	 * @return customer object.
	 * @throws Exception
	 */
	private Customer extractCustomerFromResultSet(ResultSet resultSet) throws Exception {
		Customer customer = new Customer();
		customer.setCustomerId(resultSet.getLong("CUST_ID"));
		customer.setCustomerName(resultSet.getString("CUST_NAME"));
		customer.setCustomerPassword(resultSet.getString("CUST_PASSWORD"));

		return customer;
	}
	
	/**
	 * Extracts data of all customers from Data Base.
	 * @return List of all customers.
	 * @throws ApplicationException 
	 */
	public List<Customer> getAllCustomers() throws ApplicationException{
		
		List<Customer> customersList = new ArrayList<>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = JdbcUtils.getConnection();
			String sql = "SELECT * FROM customer";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.first()) {
				return customersList;
			} else {
				resultSet.beforeFirst();				
			}
			while (resultSet.next()) {
				customersList.add(extractCustomerFromResultSet(resultSet));
			}
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CustomerDao, getAllCustomers(); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		
		return customersList;
	}
	
	/**
	 * Removes customer with certain ID from customer table. 
	 * @param customerId id of the customer that is going to be removed.
	 * @throws ApplicationException 
	 */
	public void removeCustomerById(long customerId) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = JdbcUtils.getConnection();
			String sql = "DELETE FROM customer WHERE CUST_ID = ? ";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, customerId);
			preparedStatement.executeUpdate();
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CustomerDao, removeCustomerById(long customerId); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	
	/**
	 * Removes all coupons bought by customer with certain ID from Data Base. 
	 * (deletes data from customer_coupon table)
	 * @param customerId customer id to search for coupons.
	 * @throws ApplicationException 
	 */
	public void removeCustomerCouponsByCustomerId(long customerId) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = JdbcUtils.getConnection();
			String sql = "DELETE FROM customer_coupon WHERE CUST_ID = ? ";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, customerId);
			preparedStatement.executeUpdate();
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CustomerDao, removeCustomerCouponsByCustomerId(long customerId); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	
	
	
	/**
	 * Updates data of certain customer using customer id as a search parameter.
	 * @param customer thats we update.
	 * @throws ApplicationException 
	 */
	public void updateCustomer(Customer customer) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
				
		try {
			connection = JdbcUtils.getConnection();
			String sql = "UPDATE customer SET CUST_NAME = ?, CUST_PASSWORD = ? WHERE CUST_ID = ? ";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, customer.getCustomerName());
			preparedStatement.setString(2, customer.getCustomerPassword());
			preparedStatement.setLong(3, customer.getCustomerId());
			preparedStatement.executeUpdate();
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CustomerDao, updateCustomer(Customer customer); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}	
		
	/**
	 * Checks if there is such match of Name and Password in Data Base.
	 * @param customerName name to match.
	 * @param customerPassword password to match.
	 * @return true if match was found otherwise false.
	 */
	public boolean customerLogin(String customerName, String customerPassword) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = JdbcUtils.getConnection();
			String sql = "SELECT * FROM customer WHERE CUST_NAME = ? AND CUST_PASSWORD =  ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, customerName);
			preparedStatement.setString(2, customerPassword);			
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.next()) {
				return false;
			}
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + 
					"Error in CustomerDao, customerLogin(String customerName, String customerPassword); FAILED");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		return true;
	}
}