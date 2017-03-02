package com.jfonzuer.dto.response;

/**
 * Created by pgm on 11/03/17.
 */
public class ExceptionDto {
    private String message;

    public ExceptionDto() {
    }

    public ExceptionDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
