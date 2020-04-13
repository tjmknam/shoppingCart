package net.class101.exception;

public class SoldOutException extends Exception {
	
	public SoldOutException(String errorMessage) {
		super(errorMessage);
	}

}
