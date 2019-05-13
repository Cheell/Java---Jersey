package com.yakov.coupons.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import com.yakov.coupons.beans.ErrorBean;

/**
 * Exception handler
 * @author Yakov
 *
 */
@Provider
public class ExceptionsHandler implements ExceptionMapper<Throwable> 
{
	@Override
	
	public Response toResponse(Throwable throwed) 
	{
		//if exception is "our" Application exception then we work with it.  
		if (throwed instanceof ApplicationException){
			ApplicationException e = (ApplicationException) throwed;

			int internalErrorCode = e.getErrorType().getErrorCode();
			String internalMessage = e.getMessage();
			String externalMessage = e.getErrorType().getErrorMessage();									
			ErrorBean errorBean = new ErrorBean(internalErrorCode, internalMessage, externalMessage);
			return Response.status(internalErrorCode).entity(errorBean).build();
		}

		//if not then we just send some generic error.
		String internalMessage = throwed.getMessage();
		throwed.printStackTrace();
		ErrorBean errorBean = new ErrorBean(601, internalMessage, "General error");
		return Response.status(601).entity(errorBean).build();
	}
}