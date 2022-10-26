package com.sboot.cloud.netflx.pagamento.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFundException extends RuntimeException{
	
	private static final long serialVersionUID = 7949368453228295171L;

	public ResourceNotFundException(String exception) {
		super(exception);
	}

}
