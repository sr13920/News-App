package com.AuthService.exception;

public class UserNotFoundException extends RuntimeException {
public UserNotFoundException(String message) {
	super(message);
}

}
