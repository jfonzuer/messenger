package com.jfonzuer.dto;

/**
 * Created by pgm on 07/11/16.
 */
public class ImageDto {
    private Long id;
    private Integer orderNumber;
    private String url;

    public ImageDto() {
    }

    public ImageDto(Long id, Integer order, String url) {
        this.id = id;
        this.orderNumber = order;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static class ImageDtoBuilder {
        private Long id;
        private Integer order;
        private String url;

        public ImageDtoBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public ImageDtoBuilder setOrder(Integer order) {
            this.order = order;
            return this;
        }

        public ImageDtoBuilder setUrl(String url) {
            this.url = url;
            return this;
        }

        public ImageDto createImageDto() {
            return new ImageDto(id, order, url);
        }
    }
}
