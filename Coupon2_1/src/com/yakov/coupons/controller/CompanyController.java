package com.yakov.coupons.controller;

import java.util.List;
import com.yakov.coupons.beans.Company;
import com.yakov.coupons.beans.Coupon;
import com.yakov.coupons.exceptions.ApplicationException;
import com.yakov.coupons.enums.ErrorType;
import com.yakov.coupons.dao.CompanyDao;
import com.yakov.coupons.dao.CouponDao;
import com.yakov.coupons.utils.DateUtils; 
import com.yakov.coupons.utils.validationUtils;

/**
 * Logics for Company object.
 * @author Yakov.
 *
 */
public class CompanyController {
	
	CompanyDao companyDao = new CompanyDao(); 
	
	/**
	 * Validates company and either creates it in DB or throws exception if validation fails.
	 * @param company - company to validate
	 * @throws ApplicationException
	 */
	public void createCompany(Company company) throws ApplicationException{
		validateCompanyEmail(company.getCompanyEmail());
		validateCompanyPassword(company.getCompanyPassword());
		validateCompanyName(company.getCompanyName());
		this.companyDao.createCompany(company);
	
	}

	/**
	 * Validates name of the company.
	 * @param name name to validate
	 * @throws ApplicationException
	 */
	private void validateCompanyName(String name) throws ApplicationException{
		validationUtils.validateAbsence(name);
		validationUtils.validateNameSpelling(name);
		validateNameDoNotExistYet(name);
	}

	/**
	 * Validates uniqueness of company Name.
	 * @param companyName name to validate
	 * @throws ApplicationException
	 */
	private void validateNameDoNotExistYet(String companyName) throws ApplicationException {
		if(companyDao.getCompanyByCompanyName(companyName) != null) {
			throw new ApplicationException(ErrorType.NAME_ALREADY_EXISTS, DateUtils.getCurrentDateAndTime() + 
					"\nAttempt to set company name using a name that already exists.");
		}
	}
	
	/**
	 * Validates company password.
	 * @param companyPassword password to validate.
	 * @throws ApplicationException
	 */
	private void validateCompanyPassword(String companyPassword) throws ApplicationException{
		validationUtils.validateAbsence(companyPassword);
		validationUtils.validatePasswordSpelling(companyPassword);
	}
	
	/**
	 * Validates company Email.
	 * @param companyEmail email to validate
	 * @throws ApplicationException
	 */
	private void validateCompanyEmail(String companyEmail) throws ApplicationException {
		validationUtils.validateAbsence(companyEmail);
		validationUtils.validateEmailSpelling(companyEmail);
		validateEmailDoNotExistYet(companyEmail);
	}

	/**
	 * Validates uniqueness of email.
	 * @param companyEmail email to validate
	 * @throws ApplicationException
	 */
	private void validateEmailDoNotExistYet(String companyEmail) throws ApplicationException {
		if(companyDao.getCompanyByCompanyEmail(companyEmail) != null) {
			throw new ApplicationException(ErrorType.EMAIL_ALREADY_EXISTS, DateUtils.getCurrentDateAndTime() + 
					"\nAttempt to set company email using a email that belongs to another company.");
		}
	}
	
	/**
	 * Searches for company by id.
	 * @param id id to search for
	 * @return company that was found.
	 */
	public Company getCompanyByCompanyId(long id) throws ApplicationException {
		Company company = companyDao.getCompanyByCompanyId(id);
/*		if(company == null) {
			throw new ApplicationException(ErrorType.NOT_FOUND_IN_DB, DateUtils.getCurrentDateAndTime() + 
					" Get company by id has failed."
					+ "\nSearch for Company Id  that doesn't exist.");
		}
*/		return company;
	}
	
	/**
	 * Searches for company by name.
	 * @param name name to search for
	 * @return company that was found
	 * @throws ApplicationException.
	 */
	public Company getCompanyByCompanyName(String name) throws ApplicationException {
		Company company = companyDao.getCompanyByCompanyName(name);
/*		if(company == null) {
			throw new ApplicationException(ErrorType.NOT_FOUND_IN_DB, DateUtils.getCurrentDateAndTime() + 
					" Get company by name has failed."
					+ "\nSearch for Company name  that doesn't exist.");
		}
*/		return company;
	}

	/**
	 * Searches for company by email.
	 * @param email email to search for
	 * @return company that was found
	 * @throws ApplicationException
	 */
	public Company getCompanyByCompanyEmail(String email) throws ApplicationException {
		Company company = companyDao.getCompanyByCompanyEmail(email);
/*		if(company == null) {
			throw new ApplicationException(ErrorType.NOT_FOUND_IN_DB, DateUtils.getCurrentDateAndTime() + 
					" Get company by email has failed."
					+ "\nSearch for Company email that doesn't exist.");
		}
*/		return company;
	}
	
	/**
	 * Gets List of all companies that exist in DB.
	 * @return list of companies that were found.
	 * @throws ApplicationException
	 */
	public List<Company> getAllCompanies() throws ApplicationException {
		return companyDao.getAllCompanies();
	}
	
	/**
	 * Deletes company and all of its coupons from DB or throws exception if no company found by provided id.
	 * @param id id of the company to delete.
	 * @throws ApplicationException
	 */
	public void removeCompanyByCompanyId(long id) throws ApplicationException {
		CouponDao couponDao = new CouponDao();
		if(companyDao.getCompanyByCompanyId(id) == null) {
			throw new ApplicationException(ErrorType.NOT_FOUND, DateUtils.getCurrentDateAndTime() + 
					" Remove company by id has failed."
					+ "\nAttmpt to remove company that doesn't exist.");
		}
		for(Coupon c : couponDao.getCouponsByCompanyId(id)) {
			couponDao.removeCouponsFromCustomerCouponsByCouponId(c.getCompanyId());
		}
		couponDao.removeCouponsByCompanyId(id);
		companyDao.removeCompanyById(id);
	}
	
	/**
	 * Validates updated company and updates if validation succeeds, throws exception if validation fails.
	 * @param company updated company version to change current version.
	 * @throws ApplicationException
	 */
	public void updateCompany(Company company) throws ApplicationException {
		Company companyOld = companyDao.getCompanyByCompanyId(company.getCompanyId());
		
		if(companyDao.getCompanyByCompanyId(company.getCompanyId()) == null) {
			throw new ApplicationException(ErrorType.NOT_FOUND, DateUtils.getCurrentDateAndTime() + 
					" Update company by id has failed."
					+ "\nAttempt to update company that doesn't exist.");
		}
		
		if(!company.getCompanyName().equals(companyOld.getCompanyName())) {
			validateCompanyName(company.getCompanyName());
		}
		if(!company.getCompanyEmail().equals(companyOld.getCompanyEmail())) {
			validateCompanyEmail(company.getCompanyEmail());
		}
		if(!company.getCompanyPassword().equals(companyOld.getCompanyPassword())) {
			validateCompanyPassword(company.getCompanyPassword());
		}
		companyDao.updateCompany(company);
	}
	
	/**
	 * Check if login and password match, throws exception if not.
	 * @param companyName name to check.
	 * @param companyPassword password to check. 
	 * @return 
	 * @return 
	 * @throws ApplicationException.
	 */
	public boolean companyLogin(String companyName, String companyPassword) throws ApplicationException {
			validationUtils.validateAbsence(companyName);
			validationUtils.validateNameSpelling(companyName);
			validateCompanyPassword(companyPassword);
			if(!companyDao.companyLogin(companyName, companyPassword)) {
/*				throw new ApplicationException(ErrorType.NO_MATCH_FOUND_IN_DB, DateUtils.getCurrentDateAndTime() +
						" Company login failed "
						+ "\nAttemp to login with wrong password or company name");
*/				return false;
			}
			return true;
		}
}