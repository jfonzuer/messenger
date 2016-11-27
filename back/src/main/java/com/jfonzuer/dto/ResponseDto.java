package com.jfonzuer.dto;

/**
 * Created by pgm on 27/11/16.
 */
public class ResponseDto {
    private String message;

    public ResponseDto() {
    }

    public ResponseDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
