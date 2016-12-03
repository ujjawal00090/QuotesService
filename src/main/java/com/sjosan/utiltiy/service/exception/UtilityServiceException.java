package com.sjosan.utiltiy.service.exception;

public class UtilityServiceException extends Exception {

	/**
	 * 
	 */
	
	private String message;
	
	private static final long serialVersionUID = 1L;
	
	
	public UtilityServiceException(String message){
		this.message=message;
	}
	
	public String getCustomMessage() {
		return message;
	}

}
