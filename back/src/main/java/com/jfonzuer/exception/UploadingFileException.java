package com.jfonzuer.exception;

/**
 * Created by pgm on 25/10/16.
 */
public class UploadingFileException extends RuntimeException {

    private String message;

    public UploadingFileException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
