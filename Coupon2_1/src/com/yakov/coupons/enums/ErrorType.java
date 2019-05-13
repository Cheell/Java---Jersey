package com.yakov.coupons.enums;

/**
 * Types of possible exceptions
 * @author Yakov
 *
 */
public enum ErrorType {
	SYSTEM_ERROR(202), 					//Errors that can happen while exchanging data with DB.
	NAME_ALREADY_EXISTS(303),			//Appears when trying to use name which already exists in DB.
	EMAIL_ALREADY_EXISTS(304),			//Appears when trying to use email which already exists in DB.
	MISSING_STRING(313),				//Required String came empty.
	INCORRECT_NAME_SPELLING(323),		//Forbidden name spelling 
	INCORRECT_EMAIL_SPELLING(333),		//
	INCORRECT_DATE_SPELLING(343),		//
	INCORRECT_PASSWORD_SPELLING(343),	//
	INCORRECT_VALUE(344),				//Value doesn't match requirements.
	NOT_FOUND(373),				//Trying to use object that doesn't exist in Data Base.
	NO_MATCH_FOUND(375);			//
	
	
	private final int errorCode;
	
	private ErrorType(int errorCode) {
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return this.errorCode;
	}
	
	public String getErrorMessage() {
		return this.name();
	}
	
	public static ErrorType fromString(final String s) {
		return ErrorType.valueOf(s);
	}
	
	
}
