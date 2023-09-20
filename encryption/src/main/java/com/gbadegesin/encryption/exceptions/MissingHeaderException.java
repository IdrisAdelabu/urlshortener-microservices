package com.gbadegesin.encryption.exceptions;

import org.springframework.http.HttpStatus;


public class MissingHeaderException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2879397445601690436L;

	private HttpStatus httpStatus;
	private String code;
	private String message;

	public MissingHeaderException(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public MissingHeaderException(String code, String message, HttpStatus httpStatus) {
		super();
		this.code = code;
		this.message = message;
		this.httpStatus = httpStatus;
	}

//	public MissingHeaderException(String code, String message) {
//		this.code = code;
//		this.message = message;
//	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
}
