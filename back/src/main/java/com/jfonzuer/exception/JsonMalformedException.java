package com.jfonzuer.exception;

/**
 * Created by pgm on 25/10/16.
 */
public class JsonMalformedException extends RuntimeException {

    private String message;

    public JsonMalformedException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
