package com.jfonzuer.exception;

/**
 * Created by pgm on 21/09/16.
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(String e) {
        super(e);
    }
}
