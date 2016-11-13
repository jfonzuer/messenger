package com.jfonzuer.dto;

/**
 * Created by pgm on 12/11/16.
 */
public class OrderNumberDto {
    private Integer orderNumber;

    public OrderNumberDto() {
    }

    public OrderNumberDto(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }
}
