package com.yakov.coupons.dao;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.yakov.coupons.beans.Company;
import com.yakov.coupons.exceptions.ApplicationException;
import com.yakov.coupons.enums.ErrorType;
import com.yakov.coupons.utils.DateUtils;
import com.yakov.coupons.utils.JdbcUtils;

/**
 * 
 * @author Yakov
 *	Dao that works with Companies.
 */
public class CompanyDao{
	
	/**
	 * Adds new company to the company table.
	 * @param company to add to the table.
	 * @throws ApplicationException 
	 */
	public void createCompany(Company company) throws ApplicationException {

		java.sql.PreparedStatement preparedStatement = null;
		Connection connection = null;

		try {
			// Getting a connection to the DB
			connection = JdbcUtils.getConnection();			
			// Creating a string which will contain the query
			// PAY ATTENTION - BY USING THE ? (Question marks) WE PREVENT AN SQL INJECTION ATTACK
			String sql = "insert into company (comp_name, comp_password, comp_email) values (?,?,?)";

			preparedStatement= connection.prepareStatement(sql);

			preparedStatement.setString(1, company.getCompanyName());
			preparedStatement.setString(2, company.getCompanyPassword());
			preparedStatement.setString(3, company.getCompanyEmail());
			preparedStatement.executeUpdate();
		} 

		catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CompanyDao, createCompany(); Failed");
		} 

		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}

	
	/**
	 * Finds a company by company ID.
	 * @param companyId Id to look for the company.
	 * @return company.
	 * @throws ApplicationException 
	 */
	public Company getCompanyByCompanyId(long companyId) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Company company = null;

		try {
			connection = JdbcUtils.getConnection();
			String sql = "SELECT * FROM company WHERE COMP_ID = ? ";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, companyId);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.next()) {
				return company;
			}
			company = extractCompanyFromResultSet(resultSet);

		}

		catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() 
					+ "Error in CompanyDao, getCompanyByCompanyId(long companyId);  Failed");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		return company;
	}

	/**
	 * Finds a company by company Name.
	 * @param companyName to look for the company.
	 * @return company.
	 * @throws ApplicationException 
	 */
	public Company getCompanyByCompanyName(String companyName) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Company company = null;

		try {
			connection = JdbcUtils.getConnection();
			String sql = "SELECT * FROM company WHERE COMP_NAME = ? ";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, companyName);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.next()) {
				return company;
			}
			company = extractCompanyFromResultSet(resultSet);
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() 
					+ "Error in CompanyDao, getCompanyByCompanyName(String companyName);  Failed");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		return company;
	}
	
	/**
	 * Finds a company by company Email.
	 * @param email to look for the company.
	 * @return company.
	 * @throws ApplicationException 
	 */
	public Company getCompanyByCompanyEmail(String companyEmail) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Company company = null;

		try {
			connection = JdbcUtils.getConnection();
			String sql = "SELECT * FROM company WHERE COMP_EMAIL = ? ";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, companyEmail);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.next()) {
				return company;
			}
			company = extractCompanyFromResultSet(resultSet);
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() 
					+ "Error in CompanyDao, getCompanyByCompanyEmail(String companyEmail);  Failed");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		return company;
	}
	
	
	/**
	 * Creates company from Data Base data.
	 * @param resultSet data to extract.
	 * @return company object.
	 * @throws Exception
	 */
	private Company extractCompanyFromResultSet(ResultSet resultSet) throws Exception {
		Company company = new Company();
		company.setCompanyId(resultSet.getLong("COMP_ID"));
		company.setCompanyName(resultSet.getString("COMP_NAME"));
		company.setCompanyPassword(resultSet.getString("COMP_PASSWORD"));
		company.setCompanyEmail(resultSet.getString("COMP_EMAIL"));

		return company;
	}

	
	/**
	 * Extracts data of all companies from Data Base.
	 * @return List of all companies.
	 * @throws ApplicationException 
	 */
	public List<Company> getAllCompanies() throws ApplicationException{
		
		List<Company> companiesList = new ArrayList<>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = JdbcUtils.getConnection();
			String sql = "SELECT * FROM company";
			preparedStatement = connection.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery();
			if (!resultSet.first()) {
				return companiesList;
			} else {
				resultSet.beforeFirst();				
			}
			while (resultSet.next()) {
				companiesList.add(extractCompanyFromResultSet(resultSet));
			}
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() + "Error in CompanyDao, getAllCompanies(); Failed");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
		
		return companiesList;
	}
	
	/**
	 * Removes company with certain ID from Data Base. 
	 * @param companyId company id to remove.
	 * @throws ApplicationException 
	 */
	public void removeCompanyById(long companyId) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try {
			connection = JdbcUtils.getConnection();
			String sql = "DELETE FROM company WHERE COMP_ID = ? ";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, companyId);
			preparedStatement.executeUpdate();
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() 
					+ "Error in CompanyDao, removeCompanyById(long companyId); Failed");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	
	/**
	 * Updates data of company using company Id as a search parameter, that we receive from company.
	 * @param company that we update.
	 * @throws ApplicationException 
	 */
	public void updateCompany(Company company) throws ApplicationException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
				
		try {
			connection = JdbcUtils.getConnection();
			String sql = "UPDATE company SET COMP_NAME = ?, COMP_PASSWORD = ?, COMP_EMAIL = ? WHERE COMP_ID = ? ";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, company.getCompanyName());
			preparedStatement.setString(2, company.getCompanyPassword());
			preparedStatement.setString(3, company.getCompanyEmail());
			preparedStatement.setLong(4, company.getCompanyId());
			preparedStatement.executeUpdate();
		}

		catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() 
					+ "Error in CompanyDao, updateCompany(Company company); Failed");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement);
		}
	}
	
	/**
	 * Checks if there is such match of company Name and Password in Data Base.
	 * @param companyName name to match.
	 * @param companyPassword password to match.
	 * @return true if match was found otherwise false.
	 */
	public boolean companyLogin(String companyName, String companyPassword) throws ApplicationException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = JdbcUtils.getConnection();
			String sql = "SELECT * FROM company WHERE COMP_NAME = ? AND COMP_PASSWORD =  ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, companyName);
			preparedStatement.setString(2, companyPassword);			
			resultSet = preparedStatement.executeQuery();
			return resultSet.next();
		}

		catch (Exception e) {
			throw new ApplicationException(e, ErrorType.SYSTEM_ERROR, DateUtils.getCurrentDateAndTime() 
					+ "Error in CompanyDao, companyLogin(String companyName, String companyPassword); Failed");
		}

		finally {
			JdbcUtils.closeResources(connection, preparedStatement, resultSet);
		}
	}
	
}