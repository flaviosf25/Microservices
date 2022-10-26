package com.sboot.cloud.netflix.crud.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFundException extends RuntimeException{

	private static final long serialVersionUID = 3350157240820367540L;

	public ResourceNotFundException(String exception) {
		super(exception);
	}
}
