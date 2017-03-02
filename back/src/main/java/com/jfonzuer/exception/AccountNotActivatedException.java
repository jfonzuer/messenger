package com.jfonzuer.exception;

/**
 * Created by pgm on 05/03/17.
 */
public class AccountNotActivatedException extends RuntimeException{
    public AccountNotActivatedException() {
        super();
    }

    public AccountNotActivatedException(String message) {
        super(message);
    }
}
