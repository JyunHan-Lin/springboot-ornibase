package com.example.demo.exception;

public class UserAlreadyExistException extends CertException{
	
	public UserAlreadyExistException(String message) {
		super(message);
	}
}
