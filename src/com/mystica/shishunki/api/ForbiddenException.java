package com.mystica.shishunki.api;

public class ForbiddenException extends RuntimeException {

	public ForbiddenException(String string, Exception e) {
		super(string, e);
	}

	public ForbiddenException() {
		super();
	}

}
