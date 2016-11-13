package com.jfonzuer.dto;

/**
 * Created by pgm on 12/11/16.
 */
public class CustomErrorType {
    private Integer status;
    private String message;

    public CustomErrorType(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
